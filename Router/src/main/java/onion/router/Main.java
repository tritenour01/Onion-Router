package onion.router;

public class Main {
    public static void main(String[] args){
        
        RegistryPing ping = new RegistryPing();
        new Thread(ping).start();
        
        TCPEndpoint tcp = new TCPEndpoint();
        new Thread(tcp).start();
    }
}
