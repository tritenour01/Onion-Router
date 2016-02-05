package onion.client;

import java.util.ArrayList;

public class RequestManager {
    private static ArrayList<Request> requests = new ArrayList<>();
    
    public static void create(String url, Onion data, RouterInfo path[]){
        Request newReq = new Request(url, path);
        requests.add(newReq);
        
        RequestRunner run = new RequestRunner(newReq);
        new Thread(run).start();
    }
}
