package onion.router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import onion.shared.ConfigHelper;
import onion.shared.TCPHandler;

public class TCPEndpoint implements Runnable{
    
    public void run(){
        try{
            ConfigHelper config = ConfigHelper.getInstance();
            int port = Integer.parseInt(config.getValue("port"));
            
            ServerSocket server = new ServerSocket(port);
            System.out.println("Listening on port " + port + ", waiting for connection");
            
            while(true){
                Socket sock = server.accept();
                
                RoutingProtocol proto = new RoutingProtocol();
                TCPHandler handler = new TCPHandler(sock, proto);
                new Thread(handler).start();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
}
