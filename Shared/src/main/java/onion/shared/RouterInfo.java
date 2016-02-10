package onion.shared;

import java.io.Serializable;

public class RouterInfo implements Serializable{
    private String host;
    private int port;
    private String onionKey;
    
    public String getHost(){
        return host;
    }
    
    public int getPort(){
        return port;
    }
    
    public String getOnionKey(){
        return onionKey;
    }
    
    public void setHost(String val){
        host = val;
    }
    
    public void setPort(int val){
        port = val;
    }
    
    public void setOnionKey(String val){
        onionKey = val;
    }
}
