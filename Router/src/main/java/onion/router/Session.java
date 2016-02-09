package onion.router;

import onion.shared.TCPHandler;

public class Session {
    private int id;
    private TCPHandler handler;
    
    public int getId(){
        return id;
    }
    
    public TCPHandler getHandler(){
        return handler;
    }
    
    public void setId(int val){
        id = val;
    }
    
    public void setHandler(TCPHandler val){
        handler = val;
    }
}
