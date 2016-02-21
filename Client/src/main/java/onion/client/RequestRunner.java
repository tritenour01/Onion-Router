package onion.client;

import java.util.Random;
import java.util.concurrent.Semaphore;
import onion.shared.PacketHelper;
import onion.shared.RouterInfo;
import onion.shared.TCPHandler;

public class RequestRunner implements Runnable{
    
    private Semaphore lock;
    
    private Request req;
    private PacketHelper builder;
    private RoutingProtocol proto;
    private TCPHandler handler;
    
    public RequestRunner(Request req){
        this.req = req;
        builder = new PacketHelper(req.getPath());
        lock = new Semaphore(1);
    }
    
    public void run(){
        RouterInfo path[] = req.getPath();
        Connection conn = ConnectionManager.get(path[0]);
        proto = conn.getProtocol();
        handler = conn.getHandler();
        
        try{
            int sessionId = createSession();
            System.out.println("Session Created");
            
            for(int i = 0; i < path.length - 1; i++){
                extend(sessionId, i);
                System.out.println("Circuit Extended ");
            }
            
            System.out.println("Circuit Built");
            System.out.println("Sending Request");
            
            request(sessionId);
            System.out.println("Request Complete");
        }
        catch(Exception e){
            req.failed(e.getMessage());
            System.out.println("Request execution failed");
            System.out.println(e);
        }
    }
    
    private void acquire(){
        try{
            lock.acquire();
        }
        catch(InterruptedException e){
            System.out.println("semaphore aquire failed");
        }
    }
    
    private void lockWait(){
        acquire();
        lock.release();
    }
    
    private int createSession(){
        acquire();
        
        Random rand = new Random();
        int sessionId = rand.nextInt(100000);
        
        RequestManager.associate(sessionId, this);
        
        String packet = builder.create(sessionId);
        handler.write(packet);
        
        lockWait();
        
        return sessionId;
    }
    
    public void sessionCreated(){
        lock.release();
    }
    
    private void extend(int sessionId, int destId){
        acquire();
        
        String packet = builder.extend(sessionId, destId);
        handler.write(packet);
        
        lockWait();
    }
    
    public void extended(){
        lock.release();
    }
    
    private void request(int sessionId){
        acquire();
        
        String packet = builder.request(sessionId, req.getUrl());
        handler.write(packet);
        
        lockWait();
    }
    
    public void response(boolean success, String data){
        lock.release();
        if(success)
            req.complete(data);
        else
            req.failed(data);
    }
    
    public PacketHelper getPacketHelper(){
        return builder;
    }
}
