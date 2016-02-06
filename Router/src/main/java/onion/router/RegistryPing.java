package onion.router;

import java.io.IOException;
import java.net.UnknownHostException;

import onion.shared.ConfigHelper;
import onion.shared.SocketWrapper;

public class RegistryPing implements Runnable {
    private SocketWrapper socket;
    
    public void run(){
        connect();
        
        RegisterProtocol proto = new RegisterProtocol(this);
        proto.init();
        
        try{
            String data;
            while(proto.isDone() == false && (data = socket.read()) != null){
                System.out.println(data);
                proto.handleInput(data);
            }
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
        
        disconnect();
    }
    
    private void connect(){
        ConfigHelper config = ConfigHelper.getInstance();
        String host = config.getValue("lookupHost");
        int port = Integer.parseInt(config.getValue("lookupPort"));
        
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
    
    private void disconnect(){
        try{
            socket.disconnect();
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    public void write(String data){
        socket.write(data);
    }
}
