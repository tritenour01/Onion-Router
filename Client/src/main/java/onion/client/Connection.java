package onion.client;

import onion.shared.TCPHandler;

public class Connection{
    private String host;
    private int port;
    
    private RoutingProtocol proto;
    
    private TCPHandler handler;
    
    public Connection(String host, int port){
        this.host = host;
        this.port = port;
        
        proto = new RoutingProtocol();
        
        handler = new TCPHandler(host, port, proto);
    }
    
    public void start(){
        new Thread(handler).start();
    }
   
    public String getHost(){
        return host;
    }
    
    public int getPort(){
        return port;
    }
    
    public RoutingProtocol getProtocol(){
        return proto;
    }
    
    public TCPHandler getHandler(){
        return handler;
    }
}
