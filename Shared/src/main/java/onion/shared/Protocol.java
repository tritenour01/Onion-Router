package onion.shared;

public interface Protocol {
    
    public boolean isDone();
    
    public void init();
    
    public void handleInput(String data);
    
    public void setHandler(TCPHandler handler);
    
}
