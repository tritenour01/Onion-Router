package onion.router;

import java.util.Collections;
import java.util.HashMap;
import onion.shared.TCPHandler;

public class SessionManager {
    private static HashMap sessions = new HashMap<Integer, Session>();
    
    public static int createSession(TCPHandler handler){
        int sessionId = 1;
        try{
            sessionId = (int)Collections.max(sessions.keySet()) + 1;
        }
        catch(Exception e){}
        
        System.out.println(sessionId);
        
        Session newSession = new Session();
        newSession.setId(sessionId);
        newSession.setHandler(handler);
        
        sessions.put(sessionId, newSession);
        
        return sessionId;
    }
}
