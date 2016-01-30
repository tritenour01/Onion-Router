package onion.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import onion.shared.ConfigHelper;

public class RegistryPing implements Runnable {
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;
    
    
    public void run(){
        connect();
        output.println("test");
        disconnect();
    }
    
    private void connect(){
        ConfigHelper config = ConfigHelper.getInstance();
        String host = config.getValue("lookupHost");
        int port = Integer.parseInt(config.getValue("lookupPort"));
        
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
