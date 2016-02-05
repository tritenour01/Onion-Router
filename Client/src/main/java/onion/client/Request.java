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
    
    public Request(String url){
        state = State.PENDING;
        this.url = url;
        startTime = new Date();
    }
    
    public void complete(){
        state = State.SUCCESS;
        endTime = new Date();
    } 
    
    public void failed(){
        state = State.FAIL;
        endTime = new Date();
    }
}
