package onion.lookup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args){
        
        try{
            ServerSocket server = new ServerSocket(8083);
            System.out.println("Listening on port 8083, waiting for connection");
            
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
