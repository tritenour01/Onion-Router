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
    
    public void failed(String data){
        state = State.FAIL;
        endTime = new Date();
        response = data;
    }
    
    public String getUrl(){
        return url;
    }
    
    public RouterInfo[] getPath(){
        return path;
    }
    
    public String getResponse(){
        return response;
    }
    
    public String getStart(){
        return startTime.toString();
    }
    
    public String getEnd(){
        return endTime.toString();
    }
    
    public String getState(){
        switch(state){
            case PENDING:
                return "Pending";
            case FAIL:
                return "Fail";
            case SUCCESS:
                return "Success";
            default:
                return "";
        }
    }
    
    public String summary(){
        int maxCharacters = 2000;
        String responseStr = response.substring(0, Math.min(response.length(), maxCharacters));
        if(response.length() > maxCharacters)
            responseStr += "... string trimmed";
        
        return "State: " + getState() + "\n" +
               "URL: " + url + "\n" +
               "Start: " + getStart() + "\n" +
               "End: " + getEnd() + "\n" +
               "Response: " + responseStr + "\n";
               
    }
}
