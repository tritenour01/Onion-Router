package onion.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection implements Runnable{
    private String host;
    private int port;
    
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;
    
    private RoutingProtocol proto;
    
    public Connection(String host, int port){
        this.host = host;
        this.port = port;
        
        proto = new RoutingProtocol(this);
    }
    
    public void run(){
        try{
            String data;
            while((data = read()) != null){
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
            sock = new Socket(host, port);
            input = new BufferedReader(
                    new InputStreamReader(sock.getInputStream())
            );
            output = new PrintWriter(sock.getOutputStream(), true);
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
    
    public void disconnect(){
        try{
            sock.close();
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
    
    private String read() throws IOException{
        return input.readLine();
    }
    
    public void write(String data){
        output.println(data);
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
