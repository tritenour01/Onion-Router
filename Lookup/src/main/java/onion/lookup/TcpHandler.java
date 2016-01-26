package onion.lookup;

import java.net.Socket;

public class TcpHandler implements Runnable {
    Socket sock;
    
    TcpHandler(Socket s){
        this.sock = s;
    }
    
    public void run(){
        System.out.println("HEEEEEEEEEEEEEEEEEEEY");
    }
}
