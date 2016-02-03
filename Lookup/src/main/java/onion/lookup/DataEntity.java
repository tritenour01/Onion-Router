package onion.lookup;

import java.util.Date;

public class DataEntity {
    RouterInfo data;
    Date lastPing;
    
    public DataEntity(RouterInfo info){
        data = info;
        lastPing = new Date();
    }
    
    public RouterInfo getData(){
        return data;
    }
    
    public Date getLastPing(){
        return lastPing;
    }
    
    public void setData(RouterInfo info){
        data = info;
    }
    
    public void updateLastPing(){
        lastPing = new Date();
    }
    
    public String toJSON(){
        return data.toJSON();
    }
}
