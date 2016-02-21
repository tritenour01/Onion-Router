package onion.lookup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import onion.shared.ConfigHelper;
import onion.shared.Factory;
import onion.shared.TCPEndpoint;

public class Main {
    public static void main(String[] args){
        
        ConfigHelper config = ConfigHelper.getInstance();
        int port = Integer.parseInt(config.getValue("tcpPort"));
        
        Factory factory = new ProtocolFactory();
        
        //Start the TCP server
        TCPEndpoint tcpServer = new TCPEndpoint(port, factory);
        new Thread(tcpServer).start();
        
        //Start the HTTP server
        HTTPEndpoint httpServer = new HTTPEndpoint();
        new Thread(httpServer).start();
        
        //periodically cleanup data store
        DataCleaner clean = new DataCleaner();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(clean, 5, 5, TimeUnit.MINUTES);
        
    }
}
