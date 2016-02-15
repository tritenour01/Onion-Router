package onion.shared;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {
    
    public static byte[] decode(String data){
        byte decoded[] = Base64.decodeBase64(data);
        return decoded;
    }
    
    public static String encode(byte[] data){
        byte encoded[] = Base64.encodeBase64(data);
        return new String(encoded);
    }
    
}
