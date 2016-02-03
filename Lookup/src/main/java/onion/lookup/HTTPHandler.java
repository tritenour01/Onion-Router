package onion.lookup;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HTTPHandler implements HttpHandler{
    
    public void handle(HttpExchange t){
        JSONArray jsonArray = new JSONArray();
        JSONParser parser = new JSONParser();
        
        ArrayList data = DataStore.dump();
        Iterator<DataEntity> iter = data.iterator();
        
        try{
            while(iter.hasNext()){
                DataEntity val = iter.next();
                String jsonStr  = val.toJSON();
                JSONObject json = (JSONObject)parser.parse(jsonStr);
                jsonArray.add(json);
            }
            
            String response = jsonArray.toString();
            t.getResponseHeaders().add("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.length());
            t.getResponseBody().write(response.getBytes());
        }
        catch(IOException e){
            System.out.println(e);
        }
        catch(ParseException e){
            System.out.println(e);
        }
    }
    
}
