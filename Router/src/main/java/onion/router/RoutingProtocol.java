package onion.router;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import onion.shared.Protocol;
import onion.shared.TCPHandler;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    protected void process(JSONObject data){
        String command = data.get("command").toString();
        JSONObject response = new JSONObject();
        
        switch(command){
            case "create":
            {
                long sessionId = (long)data.get("sessId");
                if(SessionManager.createSession((int)sessionId, handler)){
                    response.put("command", "create-success");
                    response.put("sessId", sessionId);
                }
                else{
                    response.put("command", "create-failure");
                    response.put("sessId", sessionId);
                }
                break;
            }
            case "create-success":
            {
                long sessionId = (long)data.get("sessId");
                int returnId = SessionManager.getAssociatted((int)sessionId);
                TCPHandler returnHandler = SessionManager.getHandler(returnId);
                
                response.put("command", "extended");
                response.put("sessId", returnId);
                
                returnHandler.write(response.toString());
                
                response = null;
                break;
            }
            case "extend":
            {
                Map m = (HashMap)data.get("data");
                String host = m.get("host").toString();
                int port = Integer.parseInt(m.get("port").toString());
                
                long sessionId = (long)data.get("sessId");
                System.out.println(sessionId);
                
                Connection conn = ConnectionManager.get(host, port);
                TCPHandler handler = conn.getHandler();
                
                int newSession = SessionManager.createSession(handler);
                SessionManager.associate((int)sessionId, newSession);
                
                response.put("command", "create");
                response.put("sessId", newSession);
                handler.write(response.toString());
                
                response = null;
                break;
            }
            default:
                System.out.println("Unrecognized command: " + command);
                response = null;
        }
        
        if(response != null)
            handler.write(response.toString());
    }
    
}
