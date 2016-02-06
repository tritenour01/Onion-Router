package onion.router;

import onion.shared.Factory;
import onion.shared.Protocol;

public class ProtocolFactory implements Factory{
    
    public Protocol createProtocol(){
        return new RoutingProtocol();
    }
    
}
