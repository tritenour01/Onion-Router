package onion.router;

import java.util.HashMap;

public class ConnectionManager {
    private static HashMap<String, Connection> connections = new HashMap();
    
    public static Connection get(String host, int port){
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
