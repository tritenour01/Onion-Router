package onion.client;

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
        return "";
    }
    
    public int getInputList(){
        System.out.println("No requests");
        return 0;
    }
    
    public int readInt(int maxVal){
        int result;
        while(true){
            System.out.print(": ");
            try{
                int val = Integer.parseInt(scanner.nextLine());
                if(val < 0 || val > maxVal){
                    System.out.println("Expected a number between 0 and " + maxVal);
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
