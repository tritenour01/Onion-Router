package onion.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequestHelper {
    
    public static String request(String destUrl)
            throws MalformedURLException, IOException {
        
        URL url = new URL(destUrl);
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
    
}
