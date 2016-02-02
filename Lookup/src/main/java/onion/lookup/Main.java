package onion.lookup;

public class Main {
    public static void main(String[] args){
        
        //Start the TCP server
        TCPEndpoint tcpServer = new TCPEndpoint();
        new Thread(tcpServer).start();
        
        //Start the HTTP server
        HTTPEndpoint httpServer = new HTTPEndpoint();
        new Thread(httpServer).start();
        
    }
}
