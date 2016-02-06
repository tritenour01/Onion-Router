package onion.lookup;

import onion.shared.Factory;
import onion.shared.Protocol;

public class ProtocolFactory implements Factory {
    
    public Protocol createProtocol(){
        return new RegisterProtocol();
    }
    
}
