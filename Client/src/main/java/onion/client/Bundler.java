package onion.client;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Bundler {
    public static Onion bundle(RouterInfo path[]){
        Onion onions[] = new Onion[path.length];
        
        for(int i = 0; i < path.length; i++){
            Onion newOnion = new Onion();
            newOnion.setDestination(path[i]);
            onions[i] = newOnion;
        }
        
        for(int i = onions.length - 1; i > 0; i--){
            byte data[] = serialize(onions[i]);
            onions[i - 1].setData(data);
        }
        
        return onions[0];
    }
    
    private static byte[] serialize(Onion o){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        
        try{
            out = new ObjectOutputStream(bos);   
            out.writeObject(o);
            byte[] data = bos.toByteArray();
            return data;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
