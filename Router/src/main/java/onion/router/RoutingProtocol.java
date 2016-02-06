package onion.router;

import onion.shared.Protocol;
import onion.shared.TCPHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RoutingProtocol implements Protocol {
    
    private TCPHandler handler;
    
    public RoutingProtocol(){
        this.handler = handler;
    }
    
    public boolean isDone(){
        return false;
    }
    
    public void init(){}
    
    public void handleInput(String data){
        JSONObject json;
        
        try{
            JSONParser parser = new JSONParser();
            json = (JSONObject)parser.parse(data);
        }
        catch(ParseException e){
            System.out.println("Parsing json failed");
            System.out.println(data);
            System.out.println(e);
            return;
        }
        
        process(json);
    }
    
    private void process(JSONObject data){
        String command = data.get("command").toString();
        
        switch(command){
        
        }
    }
    
    public void setHandler(TCPHandler handle){
        this.handler = handler;
    }
    
}
