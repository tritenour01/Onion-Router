package onion.shared;

import java.security.Key;
import javax.crypto.Cipher;

public class RSAHelper {
    
    public static String encrypt(String data, Key key){
        String result = null;
        
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte cipherText[] = cipher.doFinal(data.getBytes());
            
            result = Base64Helper.encode(cipherText);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return result;
    }
    
    public static String decrypt(String EncodedCipherText, Key key){
        String result = null;
        
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte cipherText[] = Base64Helper.decode(EncodedCipherText);
            byte text[] = cipher.doFinal(cipherText);
            
            result = new String(text);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return result;
    }
    
}
