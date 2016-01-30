package onion.lookup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import onion.shared.ConfigHelper;

public class Main {
    public static void main(String[] args){
        
        try{
            ConfigHelper config = ConfigHelper.getInstance();
            int port = Integer.parseInt(config.getValue("port"));
            
            ServerSocket server = new ServerSocket(port);
            System.out.println("Listening on port " + port + ", waiting for connection");
            
            while(true){
                Socket sock = server.accept();
                
                TcpHandler handler = new TcpHandler(sock);
                new Thread(handler).start();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        System.out.println("Exiting");
        
    }
}
