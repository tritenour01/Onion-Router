package onion.client;

import onion.shared.Protocol;
import org.json.simple.JSONObject;

public class RoutingProtocol extends Protocol {
    
    protected void process(JSONObject data) {
        String command = data.get("command").toString();
        
        switch(command){
        
        }
    }
    
}
