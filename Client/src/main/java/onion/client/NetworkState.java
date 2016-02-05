package onion.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NetworkState {
    
    private static ArrayList<RouterInfo> data = null;
    
    private NetworkState(){}
    
    public static ArrayList<RouterInfo> dump(){
        
        try{
            if(data == null){
                String jsonStr = requestData();
                data = processJson(jsonStr);
            }

            return (ArrayList)data.clone();
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    private static String requestData()
            throws MalformedURLException, IOException{
        URL url = new URL("http://127.0.0.1:8081");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream())
        );
        
        String line = "";
        StringBuilder buf = new StringBuilder();
        while((line = in.readLine()) != null){
            buf.append(line);
        }
        in.close();
        
        String result = buf.toString();
        
        return result;
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
