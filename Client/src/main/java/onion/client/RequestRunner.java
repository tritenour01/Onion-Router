package onion.client;

import onion.shared.PacketBuilder;
import onion.shared.RouterInfo;

public class RequestRunner implements Runnable{
    
    private Request req;
    private PacketBuilder builder;
    
    public RequestRunner(Request req){
        this.req = req;
        builder = new PacketBuilder(req.getPath());
    }
    
    public void run(){
        RouterInfo path[] = req.getPath();
        Connection conn = ConnectionManager.get(path[0]);
        RoutingProtocol proto = conn.getProtocol();
        
        try{
            BlockingFuture<Integer> future = proto.createSession(builder);
            int sessionId = future.get();
            System.out.println("Session Created");
            
            for(int i = 0; i < path.length - 1; i++){
                BlockingFuture<Boolean> futureExt = proto.extend(builder, sessionId, i);
                futureExt.get();
                System.out.println("Circuit Extended ");
            }
            
            System.out.println("Circuit Built");
            System.out.println("Sending Request");
            
            BlockingFuture<String> futureReq = proto.request(builder, sessionId, req.getUrl());
            String response = futureReq.get();
            
            req.complete(response);
            System.out.println("Request Complete");
        }
        catch(Exception e){
            System.out.println("Request execution failed");
            System.out.println(e);
        }
    }
    
}
