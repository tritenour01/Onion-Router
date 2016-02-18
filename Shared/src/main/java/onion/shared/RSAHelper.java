package onion.shared;

import java.security.Key;
import javax.crypto.Cipher;

public class RSAHelper {
    
    public static byte[] encrypt(byte data[], Key key){
        byte result[] = null;
        
        try{
            result = process(data, key, Cipher.ENCRYPT_MODE);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return result;
    }
    
    public static byte[] decrypt(byte cipherText[], Key key){
        byte result[] = null;
        
        try{
            result = process(cipherText, key, Cipher.DECRYPT_MODE);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return result;
    }
    
    private static byte[] process(byte data[], Key key, int mode)
            throws Exception {
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(mode, key);
        
        byte cipherText[]; 
        byte result[] = new byte[0];
        
        int length = (mode == Cipher.ENCRYPT_MODE) ? 245 : 256;
        length = Math.min(length, data.length);
        
	byte[] buffer = new byte[length];

	for (int i = 0; i < data.length; i++){
            
		if ((i > 0) && (i % length == 0)){
			cipherText = cipher.doFinal(buffer);
			result = append(result, cipherText);
			
			int newlength = length;
			if (i + length > data.length) {
				 newlength = data.length - i;
			}
			
			buffer = new byte[newlength];
		}
		
		buffer[i % length] = data[i];
	}
        
	cipherText = cipher.doFinal(buffer);
        result = append(result, cipherText);
        
        return result;
    }
    
    private static byte[] append(byte a[], byte b[]){
        byte result[] = new byte[a.length + b.length];
        
        for(int i = 0; i < a.length; i++)
            result[i] = a[i];
        
        for(int i = 0; i < b.length; i++)
            result[a.length + i] = b[i];
        
        return result;
    }
    
}
