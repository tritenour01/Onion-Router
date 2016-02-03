package onion.lookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpHandler implements Runnable {
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;
    
    private RegisterProtocol proto = new RegisterProtocol(this);
    
    TcpHandler(Socket s){
        this.sock = s;
        
        try{
            this.input = new BufferedReader(
                new InputStreamReader(sock.getInputStream())
            );
            this.output = new PrintWriter(sock.getOutputStream(), true);
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
            while((data = input.readLine()) != null){
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
        output.println(data);
    }
}
