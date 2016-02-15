package onion.shared;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

public class PacketBuilder {
    
    private enum Mode {CLIENT, ROUTER};
    
    private RouterInfo path[];
    private Mode mode;
    
    public PacketBuilder(){
        mode = Mode.ROUTER;
    }
    
    public PacketBuilder(RouterInfo path[]){
        this.path = path;
        mode = Mode.CLIENT;
    }
    
    public String create(int sessionId){
        JSONObject obj = new JSONObject();
        obj.put("command", "create");
        obj.put("sessId", sessionId);
        
        return obj.toString();
    }
    
    public String extend(int sessionId, int destId){
        JSONObject obj = new JSONObject();
        obj.put("command", "forward");
        obj.put("sessId", sessionId);
        
        JSONObject payload = new JSONObject();
        payload.put("command", "extend");
        
        RouterInfo dest = path[destId + 1];
        Map m = new HashMap();
        m.put("host", dest.getHost());
        m.put("port", dest.getPort());
        payload.put("data", m);
        
        obj.put("payload", payload.toString());
        return obj.toString();
    }
    
    public String extended(int sessionId){
        JSONObject obj = new JSONObject();
        obj.put("command", "forward");
        obj.put("sessId", sessionId);
        
        JSONObject payload = new JSONObject();
        payload.put("command", "extended");
        
        obj.put("payload", payload.toString());
        return obj.toString();
    }
    
    public String request(int sessionId, String url){
        JSONObject obj = new JSONObject();
        obj.put("command", "forward");
        obj.put("sessId", sessionId);
        
        JSONObject payload = new JSONObject();
        payload.put("command", "request");
        payload.put("data", url);
        
        obj.put("payload", payload.toString());
        return obj.toString();
    }
}