package onion.client;

import java.io.IOException;
import java.net.UnknownHostException;
import onion.shared.SocketWrapper;
import onion.shared.TCPHandler;

public class Connection{
    private String host;
    private int port;
    
    private RoutingProtocol proto;
    
    public Connection(String host, int port){
        this.host = host;
        this.port = port;
        
        proto = new RoutingProtocol();
    }
    
    public void start(){
        TCPHandler handler = new TCPHandler(host, port, proto);
        new Thread(handler).run();
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
}
