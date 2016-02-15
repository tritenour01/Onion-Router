package onion.router;

import onion.shared.HTTPRequestHelper;
import onion.shared.TCPHandler;

public class HTTPRequest implements Runnable {
    
    TCPHandler handler;
    String url;
    int sessionId;
    
    public HTTPRequest(int sessionId, String url, TCPHandler handler){
        this.sessionId = sessionId;
        this.url = url;
        this.handler = handler;
    }
    
    public void run(){
        try{
            String response = HTTPRequestHelper.request(url);
            System.out.println(response);
        }
        catch(Exception e){
            System.out.println("Request failed");
            System.out.println(e);
        }
    }
}
