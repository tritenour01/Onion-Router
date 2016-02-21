package onion.router;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import onion.shared.ConfigHelper;
import onion.shared.Factory;
import onion.shared.TCPEndpoint;

public class Main {
    public static void main(String[] args){
        
        RegistryPing ping = new RegistryPing();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(ping, 0, 5, TimeUnit.MINUTES);
        
        ConfigHelper config = ConfigHelper.getInstance();
        int port = Integer.parseInt(config.getValue("port"));
        
        Factory factory = new ProtocolFactory();
        
        TCPEndpoint tcp = new TCPEndpoint(port, factory);
        new Thread(tcp).start();
    }
}
