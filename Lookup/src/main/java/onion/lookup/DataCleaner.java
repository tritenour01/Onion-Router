package onion.lookup;

public class DataCleaner implements Runnable{

    public void run() {
        DataStore.cleanup();
    }
    
}
