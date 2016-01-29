package onion.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegistryPing implements Runnable {
    Socket sock;
    BufferedReader input;
    PrintWriter output;
    
    
    public void run(){
        connect();
        output.println("test");
        disconnect();
    }
    
    private void connect(){
        try{
            sock = new Socket("127.0.0.1", 8083);
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
    
    private void disconnect(){
        try{
            sock.close();
        }
        catch(IOException e){
            System.out.println("Caught IOException");
            System.out.println(e);
        }
    }
}
