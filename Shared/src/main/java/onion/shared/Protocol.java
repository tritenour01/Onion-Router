package onion.shared;

public interface Protocol {
    public void handleInput(String data);
    public void setHandler(TCPHandler handler);
}
