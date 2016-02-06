package onion.router;

import onion.shared.ConfigHelper;
import onion.shared.TCPHandler;

public class RegistryPing implements Runnable {
    String host;
    int port;
    
    public RegistryPing(){
        ConfigHelper config = ConfigHelper.getInstance();
        host = config.getValue("lookupHost");
        port = Integer.parseInt(config.getValue("lookupPort"));
    }
    
    public void run(){
        RegisterProtocol proto = new RegisterProtocol();
        
        TCPHandler handler = new TCPHandler(host, port, proto);
        new Thread(handler).run();
    }
    
}
