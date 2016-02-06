package onion.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RoutingProtocol {
    
    private Connection conn;

    public RoutingProtocol(Connection conn){
        this.conn = conn;
    }
    
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
    
}
