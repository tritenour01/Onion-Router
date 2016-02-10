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
            
            BlockingFuture<Boolean> futureExt = proto.extend(builder, sessionId, 0);
            futureExt.get();
            System.out.println("Circuit Extended");
        }
        catch(Exception e){
            System.out.println("Request execution failed");
            System.out.println(e);
        }
    }
    
}
