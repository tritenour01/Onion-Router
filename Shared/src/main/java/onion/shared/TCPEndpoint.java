package onion.shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEndpoint implements Runnable {
    
    private int port;
    private Factory factory;
    
    public TCPEndpoint(int port, Factory factory){
        this.port = port;
        this.factory = factory;
    }
    
    public void run(){
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("Listening on port " + port);
            
            while(true){
                Socket sock = server.accept();
                
                Protocol proto = factory.createProtocol();
                TCPHandler handler = new TCPHandler(sock, proto);
                new Thread(handler).start();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
}
