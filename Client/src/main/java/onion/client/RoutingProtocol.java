package onion.client;

import java.util.HashMap;
import onion.shared.Protocol;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    HashMap requests = new HashMap<String, BlockingFuture>();
    
    protected void process(JSONObject data) {
        String command = data.get("command").toString();
        
        switch(command){
            case "create-success":
            {
                long sessionId = (long)data.get("sessId");
                
                String key = "create:" + sessionId;
                BlockingFuture future = (BlockingFuture)requests.get(key);
                
                future.put((int)sessionId);
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
        JSONObject data = parseJson(payload);
        String command = data.get("command").toString();
        
        switch(command){
            case "extended":
            {
                String key = "extend:" + sessionId;
                BlockingFuture future = (BlockingFuture)requests.get(key);
                
                future.put(true);
                
                break;
            }
            case "response":
            {
                String key = "request:" + sessionId;
                BlockingFuture future = (BlockingFuture)requests.get(key);
                
                String response = data.get("data").toString();
                future.put(response);
                
                break;
            }
            default:
                System.out.println("Unknown forward command " + command);
        }
    }
    
    public void register(String key, BlockingFuture future){
        requests.put(key, future);
    }
    
}
