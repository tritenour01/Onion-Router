package onion.router;

import onion.shared.ConfigHelper;
import onion.shared.Factory;
import onion.shared.TCPEndpoint;

public class Main {
    public static void main(String[] args){
        
        RegistryPing ping = new RegistryPing();
        new Thread(ping).start();
        
        ConfigHelper config = ConfigHelper.getInstance();
        int port = Integer.parseInt(config.getValue("port"));
        
        Factory factory = new ProtocolFactory();
        
        TCPEndpoint tcp = new TCPEndpoint(port, factory);
        new Thread(tcp).start();
    }
}
