package onion.shared;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Protocol {
    
    protected TCPHandler handler;
    
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
    
    abstract protected void process(JSONObject data);
    
    public void setHandler(TCPHandler handler){
        this.handler = handler;
    }
    
}
