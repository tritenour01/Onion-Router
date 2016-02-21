package onion.client;

import onion.shared.RouterInfo;

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
                    PathGenerator builder = new PathGenerator();
                    RouterInfo path[] = builder.generate();
                    
                    System.out.println("Sending Request...");
                    RequestManager.create(url, path);
                    
                    break;
                }
                case 2:
                {
                    int val = cli.getInputList();
                    if(val > 0){
                        Request r = RequestManager.get(val - 1);
                        System.out.println(r.summary());
                    }
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
