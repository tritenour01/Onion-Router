package onion.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import onion.shared.RouterInfo;

public class GenerateReport {
    
    static final int num = 200;
    
    public static void main(String args[]){
        PathGenerator builder = new PathGenerator();
        List<RouterInfo[]> paths = new ArrayList();
        for(int i = 0; i < num; i++)
            paths.add(builder.generate());
        
        String url = "http://www.google.com";
        for(int i = 0; i < num; i++)
            RequestManager.create(url, paths.get(i));
        
        Checker check = new Checker();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(check, 0, 15, TimeUnit.SECONDS);
    }
    
    private static class Checker implements Runnable{

        public void run() {
            System.out.println("CHECKING");
            
            List<Long> data = new ArrayList();
            
            int numSuccess = 0;
            Iterator<Request> iter = RequestManager.iterator();
            while(iter.hasNext()){
                Request req = iter.next();
                if(req.isDone()){
                    data.add(req.getTime());
                    if(req.getState().equals("Success"))
                        numSuccess++;
                }
            }
            
            if(data.size() == 0){
                return;
            }
            
            System.out.println(createReport(num, data, numSuccess));
            
        }
        
        private String createReport(int num, List<Long> data, int success){
            String result = "";
            
            result += "Number of requests: " + num + "\n";
            result += "Number of successful requests: " + success + "\n";
            
            Collections.sort(data);
            result += "Minimum latency: " + data.get(0) + "ms\n";
            result += "Maximum latency: " + data.get(data.size() - 1) + "ms\n";
            
            long median = 0;
            if(num % 2 == 0){
                long sum = data.get((data.size() / 2) - 1) + data.get(data.size() / 2);
                median = sum / 2;
            }
            else
                median = data.get(data.size() / 2);
            
            result += "Median latency: " + median + "ms\n";
            
            long average = 0;
            for(long val : data){
                average += val;
            }
            average /= data.size();
            
            result += "Average latency: " + average + "ms\n";
            
            return result;
        }
        
    }
    
}
