package onion.client;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args){
        
        System.out.println("Welcome!");
        
        CLI cli = new CLI();
        
        boolean done = false;
        while(!done){
            int option = cli.getInputMain();
            switch(option){
                case 1:
                {
                    String url = cli.getInputCreate();
                    
                    System.out.println("Building path...");
                    PathBuilder builder = new PathBuilder();
                    RouterInfo path[] = builder.build();
                    
                    System.out.println("Processing...");
                    Onion onion = Bundler.bundle(path);
                    
                    System.out.println("Sending Request...");
                    RequestManager.create(url, onion, path);
                    
                    break;
                }
                case 2:
                {
                    int val = cli.getInputList();
                    break;
                }
                case 3:
                    done = true;
                    break;
            }
        }
        
        System.out.println("Bye!");
        
    }
}
