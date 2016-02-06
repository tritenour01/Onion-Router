package onion.router;

import java.io.IOException;
import java.net.Socket;
import onion.shared.SocketWrapper;

public class TCPHandler implements Runnable{
    private SocketWrapper socket;
    
    private RoutingProtocol proto = new RoutingProtocol(this);
    
    TCPHandler(Socket s){
        try{
            socket = new SocketWrapper(s);
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    public void run(){
        System.out.println("TCP handler started");
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
        System.out.println("TCP handler exiting");
    }
    
    public void write(String data){
        socket.write(data);
    }
}
