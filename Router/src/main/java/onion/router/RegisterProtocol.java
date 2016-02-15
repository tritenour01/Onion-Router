package onion.router;

import java.security.Key;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import onion.shared.Base64Helper;
import org.json.simple.JSONObject;

import onion.shared.ConfigHelper;
import onion.shared.Protocol;
import onion.shared.RSAHelper;
import onion.shared.KeyUtil;

public class RegisterProtocol extends Protocol{

    private boolean done = false;
    
    public boolean isDone(){
        return done;
    }
    
    public void init(){
        Key key = KeyUtil.loadPublic(KeyUtil.KEYS.IDENTITY);
        String keyStr = Base64Helper.encode(key.getEncoded());
        
        JSONObject json = new JSONObject();
        json.put("command", "init");
        json.put("data", keyStr);
        handler.write(json.toString());
    }
    
    protected void process(JSONObject data){
        String command = data.get("command").toString();
        JSONObject response = new JSONObject();
        
        switch(command){
            case "challenge":
                long val = (long)data.get("data");
                
                PrivateKey key = KeyUtil.loadPrivate(KeyUtil.KEYS.IDENTITY);
                String cipherText = RSAHelper.encrypt(Integer.toString((int)val), key);
                
                response.put("command", "challenge-response");
                response.put("data", cipherText);
                
                break;
            case "challenge-failure":
                System.out.println("Challenge failed");
                response = null;
                done = true;
                break;
            case "challenge-success":
                response.put("command", "data");
                
                String requirement = data.get("data").toString();
                if(requirement.equals("required")){
                    ConfigHelper config = ConfigHelper.getInstance();
                    String host = config.getValue("host");
                    String port = config.getValue("port");

                    Map m = new HashMap();
                    m.put("host", host);
                    m.put("port", port);

                    response.put("data", m);
                }
                
                break;
            case "done":
                response = null;
                done = true;
                break;
            default:
                response = null;
        }
        
        if(response != null){
            handler.write(response.toString());
        }
    }
}
