package onion.client;

import java.util.Random;
import onion.shared.PacketHelper;
import onion.shared.RouterInfo;
import onion.shared.TCPHandler;

public class RequestRunner implements Runnable{
    
    private Request req;
    private PacketHelper builder;
    private RoutingProtocol proto;
    private TCPHandler handler;
    
    public RequestRunner(Request req){
        this.req = req;
        builder = new PacketHelper(req.getPath());
    }
    
    public void run(){
        RouterInfo path[] = req.getPath();
        Connection conn = ConnectionManager.get(path[0]);
        proto = conn.getProtocol();
        handler = conn.getHandler();
        
        try{
            BlockingFuture<Integer> future = createSession();
            int sessionId = future.get();
            System.out.println("Session Created");
            
            for(int i = 0; i < path.length - 1; i++){
                BlockingFuture<Boolean> futureExt = extend(sessionId, i);
                futureExt.get();
                System.out.println("Circuit Extended ");
            }
            
            System.out.println("Circuit Built");
            System.out.println("Sending Request");
            
            BlockingFuture<String> futureReq = request(sessionId);
            String response = futureReq.get();
            
            req.complete(response);
            System.out.println("Request Complete");
        }
        catch(Exception e){
            System.out.println("Request execution failed");
            System.out.println(e);
        }
    }
    
    private BlockingFuture<Integer> createSession(){
        Random rand = new Random();
        int sessionId = rand.nextInt(100000);
        
        String packet = builder.create(sessionId);
        
        BlockingFuture<Integer> future = new BlockingFuture<>();
        String key = "create:" + sessionId;
        proto.register(key, future);
        
        handler.write(packet);
        
        return future;
    }
    
    private BlockingFuture<Boolean> extend(int sessionId, int destId){
        String packet = builder.extend(sessionId, destId);
        
        BlockingFuture<Boolean> future = new BlockingFuture<>();
        String key = "extend:" + sessionId;
        proto.register(key, future);
        
        handler.write(packet);
        
        return future;
    }
    
    private BlockingFuture<String> request(int sessionId){
        String packet = builder.request(sessionId, req.getUrl());
        
        BlockingFuture<String> future = new BlockingFuture<>();
        String key = "request:" + sessionId;
        proto.register(key, future);
        
        handler.write(packet);
        
        return future;
    }
}
