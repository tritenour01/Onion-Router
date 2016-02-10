package onion.router;

import java.util.HashMap;
import java.util.Random;
import onion.shared.TCPHandler;

public class SessionManager {
    private static HashMap sessions = new HashMap<Integer, TCPHandler>();
    private static HashMap associations = new HashMap<Integer, Integer>();
    
    public static int createSession(TCPHandler handler){
        Random rand = new Random();
        int sessionId;
        do{
            sessionId = rand.nextInt(100000);
        }
        while(sessions.containsKey(sessionId));
        
        sessions.put(sessionId, handler);
        
        return sessionId;
    }
    
    public static boolean createSession(int id, TCPHandler handler){
        
        if(sessions.containsKey(id))
            return false;
        
        sessions.put(id, handler);
        
        return true;
        
    }
    
    public static void associate(int sessA, int sessB){
        associations.put(sessA, sessB);
        associations.put(sessB, sessA);
    }
    
    public static int getAssociatted(int sessionId){
        Object val = associations.get(sessionId);
        if(val == null)
            return -1;
        
        return (int)val;
    }
    
    public static TCPHandler getHandler(int sessionId){
        Object val = sessions.get(sessionId);
        if(val == null)
            return null;
        
        return (TCPHandler)val;
    }
}
