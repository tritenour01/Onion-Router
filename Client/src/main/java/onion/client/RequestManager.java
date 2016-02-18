package onion.client;

import java.util.ArrayList;
import java.util.HashMap;
import onion.shared.RouterInfo;

public class RequestManager {
    private static ArrayList<Request> requests = new ArrayList<>();
    
    private static HashMap<Integer, RequestRunner> runners = new HashMap<>();
    
    public static void create(String url, RouterInfo path[]){
        Request newReq = new Request(url, path);
        requests.add(newReq);
        
        RequestRunner run = new RequestRunner(newReq);
        new Thread(run).start();
    }
    
    public static void associate(int sessionId, RequestRunner runner){
        runners.put(sessionId, runner);
    }
    
    public static RequestRunner lookupRunner(int sessionId){
        return runners.get(sessionId);
    }
}
