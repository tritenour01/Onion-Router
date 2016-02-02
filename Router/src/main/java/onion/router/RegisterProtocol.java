package onion.router;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import onion.shared.ConfigHelper;

public class RegisterProtocol {
    private RegistryPing ping;
    
    private boolean done = false;
    
    public RegisterProtocol(RegistryPing ping){
        this.ping = ping;
    }
    
    public boolean isDone(){
        return done;
    }
    
    public void init(){
        JSONObject json = new JSONObject();
        json.put("command", "init");
        json.put("data", "test");
        ping.write(json.toString());
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
            case "challenge":
                long val = (long)data.get("data");
                
                response.put("command", "challenge-response");
                response.put("data", val);
                
                break;
            case "challenge-failure":
                System.out.println("Challenge failed");
                response = null;
                done = true;
                break;
            case "challenge-success":
                ConfigHelper config = ConfigHelper.getInstance();
                String host = config.getValue("host");
                String port = config.getValue("port");
                
                Map m = new HashMap();
                m.put("host", host);
                m.put("port", port);
                
                response.put("command", "data");
                response.put("data", m);
                
                break;
            case "done":
                response = null;
                done = true;
                break;
            default:
                response = null;
        }
        
        if(response != null){
            ping.write(response.toString());
        }
    }
}
