package onion.client;

import java.util.Date;

public class Request {
    
    private enum State {
        PENDING, FAIL, SUCCESS
    }
    
    private State state;
    private String url;
    private Date startTime;
    private Date endTime;
    
    private RouterInfo path[];
    
    public Request(String url, RouterInfo path[]){
        state = State.PENDING;
        this.url = url;
        startTime = new Date();
        this.path = path;
    }
    
    public void complete(){
        state = State.SUCCESS;
        endTime = new Date();
    } 
    
    public void failed(){
        state = State.FAIL;
        endTime = new Date();
    }
    
    public RouterInfo[] getPath(){
        return path;
    }
}
