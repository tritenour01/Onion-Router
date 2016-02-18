package onion.router;

import onion.shared.HTTPRequestHelper;
import onion.shared.PacketHelper;
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
        PacketHelper builder = new PacketHelper();
        
        try{
            String response = HTTPRequestHelper.request(url);
            
            String packet = builder.response(sessionId, true, response);
            handler.write(packet);
            System.out.println(response);
        }
        catch(Exception e){
            String packet = builder.response(sessionId, false, e.toString());
            handler.write(packet);
            System.out.println("Request failed");
            System.out.println(e);
        }
    }
}
