package onion.client;

import onion.shared.Protocol;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    protected void process(JSONObject data) {
        String command = data.get("command").toString();
        
        switch(command){
            case "create-success":
            {
                long sessionId = (long)data.get("sessId");
                RequestRunner runner = RequestManager.lookupRunner((int)sessionId);
                
                runner.sessionCreated();
                
                break;
            }
            case "forward":
            {
                long sessionId = (long)data.get("sessId");
                String payload = data.get("payload").toString();
                handleForward((int)sessionId, payload);
                
                break;
            }
            default:
                System.out.println("Unrecognized command " + command);
        }
    }
    
    private void handleForward(int sessionId, String payload){
        RequestRunner runner = RequestManager.lookupRunner(sessionId);
        payload = runner.getPacketHelper().decryptPayload(payload);
        
        JSONObject data = parseJson(payload);
        String command = data.get("command").toString();
        
        switch(command){
            case "extended":
            {
                runner.extended();
                
                break;
            }
            case "response":
            {   
                String response = data.get("data").toString();
                boolean success = (boolean)data.get("success");
                runner.response(success, response);
                
                break;
            }
            default:
                System.out.println("Unknown forward command " + command);
        }
    }
    
}
