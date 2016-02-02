package onion.lookup;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import onion.shared.ConfigHelper;

public class HTTPEndpoint implements Runnable{
    
    public void run(){
        ConfigHelper config = ConfigHelper.getInstance();
        int port = Integer.parseInt(config.getValue("httpPort"));
        
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new HTTPHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("HTTP server started on port " + port);
        }
        catch(IOException e){
            System.out.println("HTTP server startup failed");
            System.out.println(e);
        }
    }
    
}
