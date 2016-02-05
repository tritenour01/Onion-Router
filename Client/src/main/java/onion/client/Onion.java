package onion.client;

import java.io.Serializable;

public class Onion implements Serializable{
    private RouterInfo destination;
    private byte data[];
    
    public RouterInfo getDestination(){
        return destination;
    }
    
    public byte[] getData(){
        return data;
    }
    
    public void setDestination(RouterInfo val){
        destination = val;
    }
    
    public void setData(byte val[]){
        data = val;
    }
}
