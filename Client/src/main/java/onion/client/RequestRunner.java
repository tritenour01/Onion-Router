package onion.client;

public class RequestRunner implements Runnable{
    
    private Request req;
    
    public RequestRunner(Request req){
        this.req = req;
    }
    
    public void run(){
        RouterInfo path[] = req.getPath();
        Connection conn = ConnectionManager.get(path[0]);
        RoutingProtocol proto = conn.getProtocol();
        
        try{
            BlockingFuture<Integer> future = proto.createSession();
            int sessionId = future.get();
            System.out.println("Session Created");
        }
        catch(Exception e){
            System.out.println("Request execution failed");
            System.out.println(e);
        }
    }
    
}
