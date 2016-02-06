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
    }
    
}
