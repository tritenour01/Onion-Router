package onion.lookup;

import java.util.Random;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegisterProtocol {
    private enum State{
        INIT, CHALLENGE, CHALLENGE_FAIL,
        DATA, DONE
    }
    
    TcpHandler handler;
    
    State state = State.INIT;
    int challengeVal;
    
    public RegisterProtocol(TcpHandler handler){
        this.handler = handler;
    }
    
    public void handleInput(String data){
        JSONObject json;
        
        try{
            JSONParser parser = new JSONParser();
            json = (JSONObject)parser.parse(data);
        }
        catch(ParseException e){
            System.out.println("Parsing json failed");
            System.out.println(data);
            System.out.println(e);
            return;
        }
        
        process(json);
    }
    
    private void process(JSONObject data){
        String command = data.get("command").toString();
        JSONObject response = new JSONObject();
        
        switch(command){
            case "init":
                if(state != State.INIT)
                    return;
                
                Random rand = new Random();
                challengeVal = rand.nextInt(1000000);
                
                response.put("command", "challenge");
                response.put("data", challengeVal);
                
                state = State.CHALLENGE;
                
                break;
            case "challenge-response":
                if(state != State.CHALLENGE)
                    return;
                
                long val = (long)data.get("data");
                if(val == challengeVal){
                    response.put("command", "challenge-success");
                    response.put("data", "required");
                    
                    state = State.DATA;
                }
                else{
                    response.put("command", "challenge-failure");
                    
                    state = State.CHALLENGE_FAIL;
                }
                
                break;
            case "data":
                if(state != State.DATA)
                    return;
                
                response.put("command", "done");
                
                break;
            default:
                System.out.println("Unrecognized command: " + command);
                response = null;
        }
        
        if(response != null)
            handler.write(response.toString());
    }
}
