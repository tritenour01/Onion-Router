package onion.lookup;

public class RouterInfo {
    private String idKey;
    private String onionKey;
    private String host;
    private int port;
    
    public String getIdKey(){
        return idKey;
    }
    
    public String getOnionKey(){
        return onionKey;
    }
    
    public String getHost(){
        return host;
    }
    
    public int getPort(){
        return port;
    }
    
    public void setIdKey(String val){
        idKey = val;
    }
    
    public void setOnionKey(String val){
        onionKey = val;
    }
    
    public void setHost(String val){
        host = val;
    }
    
    public void setPort(int val){
        port = val;
    }
}
