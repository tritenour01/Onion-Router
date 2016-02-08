package onion.client;

import java.util.concurrent.LinkedBlockingQueue;
import onion.shared.Protocol;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    LinkedBlockingQueue createReq = new LinkedBlockingQueue<BlockingFuture>();
    
    protected void process(JSONObject data) {
        String command = data.get("command").toString();
        
        switch(command){
            case "create-success":
                long sessionId = (long)data.get("data");
                
                BlockingFuture future = (BlockingFuture)createReq.poll();
                
                future.put((int)sessionId);
                break;
        }
    }
    
    public BlockingFuture<Integer> createSession()
            throws InterruptedException {
        JSONObject obj = new JSONObject();
        obj.put("command", "create");
        
        BlockingFuture<Integer> future = new BlockingFuture<>();
        
        try{
            createReq.put(future);

            handler.write(obj.toString());

            return future;
        }
        catch(InterruptedException e){
            throw new InterruptedException();
        }
    }
    
}
