package onion.client;

import java.io.IOException;
import java.net.UnknownHostException;
import onion.shared.SocketWrapper;

public class Connection implements Runnable{
    private String host;
    private int port;
    
    private SocketWrapper socket;
    
    private RoutingProtocol proto;
    
    public Connection(String host, int port){
        this.host = host;
        this.port = port;
        
        proto = new RoutingProtocol(this);
    }
    
    public void run(){
        try{
            String data;
            while((data = socket.read()) != null){
                System.out.println(data);
                proto.handleInput(data);
            }
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    public void connect(){
        try{
            socket = new SocketWrapper(host, port);
        }
        catch(UnknownHostException e){
            System.out.println("Caught unknown host error");
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    public void write(String data){
        socket.write(data);
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
