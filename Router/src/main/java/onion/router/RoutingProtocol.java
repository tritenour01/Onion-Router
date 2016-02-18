package onion.router;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import onion.shared.Base64Helper;
import onion.shared.KeyUtil;
import onion.shared.PacketHelper;
import onion.shared.Protocol;
import onion.shared.RSAHelper;
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
                
                PacketHelper builder = new PacketHelper();
                String packet = builder.extended(returnId);
                
                returnHandler.write(packet);
                
                response = null;
                break;
            }
            case "forward":
            {
                long sessionId = (long)data.get("sessId");
                int nextId = SessionManager.getAssociatted((int)sessionId);
                TCPHandler.Mode mode = handler.getMode();
                
                String payload = data.get("payload").toString();
                
                if(mode == TCPHandler.Mode.INBOUND){
                    PrivateKey key = KeyUtil.loadPrivate(KeyUtil.KEYS.ONION);
                    byte decoded[] = Base64Helper.decode(payload);
                    byte result[] = RSAHelper.decrypt(decoded, key);
                    payload = Base64Helper.encode(result);
                }
                else if(mode == TCPHandler.Mode.OUTBOUND){
                    PrivateKey key = KeyUtil.loadPrivate(KeyUtil.KEYS.ONION);
                    byte decoded[] = Base64Helper.decode(payload);
                    byte result [] = RSAHelper.encrypt(decoded, key);
                    payload = Base64Helper.encode(result);
                }
                
                if(nextId == -1){
                    handleForward((int)sessionId, payload);
                }
                else{
                    TCPHandler nextHandler = SessionManager.getHandler(nextId);
                    data.put("sessId", nextId);
                    data.put("payload", payload);
                    nextHandler.write(data.toString());
                }
                
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
    
    private void handleForward(int sessionId, String encodedPayload){
        String payload = new String(Base64Helper.decode(encodedPayload));
        JSONObject data = parseJson(payload);
        String command = data.get("command").toString();
        
        JSONObject response = new JSONObject();

        switch(command){
            case "extend":
            {
                Map m = (HashMap)data.get("data");
                String host = m.get("host").toString();
                int port = Integer.parseInt(m.get("port").toString());

                Connection conn = ConnectionManager.get(host, port);
                TCPHandler newHandler = conn.getHandler();

                int newSession = SessionManager.createSession(newHandler);
                SessionManager.associate((int)sessionId, newSession);
                
                response.put("command", "create");
                response.put("sessId", newSession);
                newHandler.write(response.toString());
                break;
            }
            case "request":
            {
                String url = data.get("data").toString();
                HTTPRequest request = new HTTPRequest(sessionId, url, handler);
                new Thread(request).start();
                
                break;
            }
            default:
                System.out.println("Unrecognized forward command " + command);
        }
    }
    
}
