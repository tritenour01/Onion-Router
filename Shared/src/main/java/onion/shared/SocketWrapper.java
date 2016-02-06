package onion.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketWrapper {
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;
    
    public SocketWrapper(Socket sock)
            throws IOException {
        this.sock = sock;
        setupStreams();
    }
    
    public SocketWrapper(String host, int port) 
            throws UnknownHostException, IOException {
        this.sock = new Socket(host, port);
        setupStreams();
    }
    
    private void setupStreams()
            throws IOException {
        this.input = new BufferedReader(
            new InputStreamReader(sock.getInputStream())
        );
        this.output = new PrintWriter(sock.getOutputStream(), true);
    }
    
    public String read()
            throws IOException{
        return input.readLine();
    }
    
    public void write(String data){
        output.println(data);
    }
    
    public void disconnect()
            throws IOException {
        sock.close();
    }
}
