package onion.client;

import java.util.Iterator;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in); 
    
    public int getInputMain(){
        System.out.println("Please Select an Action:");
        System.out.println("1) Create a request");
        System.out.println("2) View request results");
        System.out.println("3) Exit");
        
        return readInt(3);
    }
    
    public String getInputCreate(){
        System.out.println("Enter request url");
        System.out.print(": ");
        return scanner.nextLine();
    }
    
    public int getInputList(){
        if(RequestManager.num() == 0){
            System.out.println("No requests");
            return 0;
        }
        else{
            System.out.println("Please Select Request to View Details:");
            Iterator<Request> iter = RequestManager.iterator();
            int count = 1;
            while(iter.hasNext()){
                Request r = iter.next();
                System.out.println(count + ") " + r.getStart() + ", " + r.getUrl());
                count++;
            }
            return readInt(RequestManager.num());
        }
    }
    
    public int readInt(int maxVal){
        int result;
        while(true){
            System.out.print(": ");
            try{
                int val = Integer.parseInt(scanner.nextLine());
                if(val < 1 || val > maxVal){
                    System.out.println("Expected a number between 1 and " + maxVal);
                    continue;
                }
                result = val;
                break;
            }
            catch(Exception e){
                System.out.println("Expected a number");
            }
        }
        
        return result;
    }
    
}
