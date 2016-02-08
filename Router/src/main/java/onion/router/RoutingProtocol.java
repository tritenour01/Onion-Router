package onion.router;

import java.util.Random;
import onion.shared.Protocol;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    protected void process(JSONObject data){
        String command = data.get("command").toString();
        JSONObject response = new JSONObject();
        
        switch(command){
            case "create":
                response.put("command", "create-success");
                
                Random rand = new Random();
                int sessionId = rand.nextInt();
                
                response.put("data", sessionId);
                break;
            default:
                System.out.println("Unrecognized command: " + command);
                response = null;
        }
        
        if(response != null)
            handler.write(response.toString());
    }
    
}
