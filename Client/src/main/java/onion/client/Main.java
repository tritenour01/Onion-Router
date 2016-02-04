package onion.client;

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
                    String val = cli.getInputCreate();
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
