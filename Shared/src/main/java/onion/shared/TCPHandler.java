package onion.shared;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPHandler implements Runnable{
    
    private SocketWrapper socket;
    
    private Protocol proto;
    
    public enum Mode {INBOUND, OUTBOUND};
    private Mode mode;
    
    public TCPHandler(Socket s, Protocol p){
        proto = p;
        proto.setHandler(this);
        
        mode = Mode.INBOUND;
        
        try{
            socket = new SocketWrapper(s);
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    public TCPHandler(String host, int port, Protocol p){
        proto = p;
        proto.setHandler(this);
        
        mode = Mode.OUTBOUND;
        
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
    
    public void run(){
        System.out.println("TCP handler started");
        
        proto.init();
        
        try{
            String data;
            while(!proto.isDone() && (data = socket.read()) != null){
                System.out.println(data);
                proto.handleInput(data);
            }
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
        
        try{
            socket.disconnect();
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
    
    public Mode getMode(){
        return mode;
    }
    
}
