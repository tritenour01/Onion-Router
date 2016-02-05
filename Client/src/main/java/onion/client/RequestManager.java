package onion.client;

import java.util.ArrayList;

public class RequestManager {
    private static ArrayList<Request> requests = new ArrayList<>();
    
    public static void create(String url, byte data[]){
        Request newReq = new Request(url);
        requests.add(newReq);
        //start process of sending data to router
    }
}
