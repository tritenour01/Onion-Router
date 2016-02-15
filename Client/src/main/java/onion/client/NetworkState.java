package onion.client;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import onion.shared.ConfigHelper;
import onion.shared.HTTPRequestHelper;
import onion.shared.RouterInfo;

public class NetworkState {
    
    private static ArrayList<RouterInfo> data = null;
    
    private NetworkState(){}
    
    public static ArrayList<RouterInfo> dump(){
        
        try{
            if(data == null){
                String serverUrl = ConfigHelper.getInstance().getValue("serverUrl");
                String jsonStr = HTTPRequestHelper.request(serverUrl);
                data = processJson(jsonStr);
            }

            return (ArrayList)data.clone();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
            
    private static ArrayList<RouterInfo> processJson(String jsonStr)
            throws ParseException{
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray)parser.parse(jsonStr);
        
        ArrayList<RouterInfo> result = new ArrayList<>();
        
        Iterator<JSONObject> iter = jsonArray.iterator();
        while(iter.hasNext()){
            JSONObject cur = iter.next();
            
            RouterInfo newInfo = new RouterInfo();
            newInfo.setHost(cur.get("host").toString());
            newInfo.setPort(Integer.parseInt(cur.get("port").toString()));
            newInfo.setOnionKey(cur.get("key").toString());
            
            result.add(newInfo);
        }
        
        return result;
    } 
}
