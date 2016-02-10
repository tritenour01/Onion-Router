package onion.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
            case "extended":
            {
                long sessionId = (long)data.get("sessId");
                
                String key = "extend:" + sessionId;
                BlockingFuture future = (BlockingFuture)requests.get(key);
                
                future.put(true);
                break;
            }
        }
    }
    
    public BlockingFuture<Integer> createSession()
            throws InterruptedException {
        JSONObject obj = new JSONObject();
        obj.put("command", "create");
        
        Random rand = new Random();
        int sessionId = rand.nextInt(100000);
        obj.put("sessId", sessionId);
        
        BlockingFuture<Integer> future = new BlockingFuture<>();
        
        String key = "create:" + sessionId;
        requests.put(key, future);

        handler.write(obj.toString());

        return future;
    }
    
    public BlockingFuture<Boolean> extend(int sessionId, RouterInfo dest){
        JSONObject obj = new JSONObject();
        obj.put("command", "extend");
        obj.put("sessId", sessionId);
        
        Map m = new HashMap();
        m.put("host", dest.getHost());
        m.put("port", dest.getPort());
        
        obj.put("data", m);
        
        BlockingFuture<Boolean> future = new BlockingFuture<>();
        
        String key = "extend:" + sessionId;
        requests.put(key, future);
        
        handler.write(obj.toString());
        
        return future;
    }
    
}
