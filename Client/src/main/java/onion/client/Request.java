package onion.client;

import java.util.Date;
import onion.shared.RouterInfo;

public class Request {
    
    private enum State {
        PENDING, FAIL, SUCCESS
    }
    
    private State state;
    private String url;
    private Date startTime;
    private Date endTime;
    
    private String response;
    
    private RouterInfo path[];
    
    public Request(String url, RouterInfo path[]){
        state = State.PENDING;
        this.url = url;
        startTime = new Date();
        this.path = path;
    }
    
    public void complete(String data){
        state = State.SUCCESS;
        endTime = new Date();
        response = data;
    } 
    
    public void failed(){
        state = State.FAIL;
        endTime = new Date();
    }
    
    public String getUrl(){
        return url;
    }
    
    public RouterInfo[] getPath(){
        return path;
    }
}
