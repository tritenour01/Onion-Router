package onion.lookup;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import onion.shared.Base64Helper;
import onion.shared.KeyUtil;
import onion.shared.Protocol;
import onion.shared.RSAHelper;
import org.json.simple.JSONObject;

public class RegisterProtocol extends Protocol{
    private enum State{
        INIT, CHALLENGE, CHALLENGE_FAIL,
        DATA, DONE
    }
    
    State state = State.INIT;
    int challengeVal;
    String pubkey;
    
    protected void process(JSONObject data){
        String command = data.get("command").toString();
        JSONObject response = new JSONObject();
        
        switch(command){
            case "init":
                if(state != State.INIT)
                    return;
                
                pubkey = data.get("data").toString();
                
                Random rand = new Random();
                challengeVal = rand.nextInt(1000000);
                
                response.put("command", "challenge");
                response.put("data", challengeVal);
                
                state = State.CHALLENGE;
                
                break;
            case "challenge-response":
                if(state != State.CHALLENGE)
                    return;
                
                String cipherText = data.get("data").toString();
                
                byte decodedKey[] = Base64Helper.decode(pubkey);
                PublicKey key = KeyUtil.createPublicKey(decodedKey);
                
                String text = new String(RSAHelper.decrypt(cipherText, key));
                System.out.println(text);
                
                if(text.equals(Integer.toString(challengeVal))){
                    response.put("command", "challenge-success");
                    
                    if(DataStore.lookupByKey(pubkey) == null)
                        response.put("data", "required");
                    else
                        response.put("data", "optional");
                    
                    state = State.DATA;
                }
                else{
                    response.put("command", "challenge-failure");
                    
                    state = State.CHALLENGE_FAIL;
                }
                
                break;
            case "data":
                if(state != State.DATA)
                    return;
                
                Object obj = data.get("data");
                if(obj != null){
                    Map m = (HashMap)obj;
                    RouterInfo info = new RouterInfo();
                    info.setIdKey(pubkey);
                    info.setHost(m.get("host").toString());
                    info.setPort(Integer.parseInt(m.get("port").toString()));
                    info.setOnionKey(m.get("key").toString());

                    DataStore.insert(info);
                }
                
                response.put("command", "done");
                
                state = State.DONE;
                
                break;
            default:
                System.out.println("Unrecognized command: " + command);
                response = null;
        }
        
        if(response != null)
            handler.write(response.toString());
    }
}
