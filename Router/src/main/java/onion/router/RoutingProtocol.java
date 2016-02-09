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
                long sessionId = (long)data.get("sessId");
                if(SessionManager.createSession((int)sessionId, handler)){
                    response.put("command", "create-success");
                    response.put("sessId", sessionId);
                }
                else{
                    response.put("command", "create-failure");
                    response.put("sessId", sessionId);
                }
                break;
            case "extend":
                response = null;
                break;
            default:
                System.out.println("Unrecognized command: " + command);
                response = null;
        }
        
        if(response != null)
            handler.write(response.toString());
    }
    
}
