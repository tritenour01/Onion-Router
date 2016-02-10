package onion.client;

import java.util.HashMap;
import onion.shared.RouterInfo;

public class ConnectionManager {
    private static HashMap<String, Connection> connections = new HashMap();
    
    public static Connection get(RouterInfo info){
        String host = info.getHost();
        int port = info.getPort();
        
        System.out.println(port);
        
        String key = host + ":" + Integer.toString(port);
        
        if(connections.get(key) == null){
            System.out.println("CREATING CONNECTION");
            Connection conn = new Connection(host, port);
            conn.start();
            connections.put(key, conn);
        }
        
        return connections.get(key);
    }
}
