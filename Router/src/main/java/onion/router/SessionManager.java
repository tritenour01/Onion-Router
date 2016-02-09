package onion.router;

import java.util.HashMap;
import onion.shared.TCPHandler;

public class SessionManager {
    private static HashMap sessions = new HashMap<Integer, TCPHandler>();
    
    public static boolean createSession(int id, TCPHandler handler){
        
        if(sessions.containsKey(id))
            return false;
        
        sessions.put(id, handler);
        
        return true;
        
    }
}
