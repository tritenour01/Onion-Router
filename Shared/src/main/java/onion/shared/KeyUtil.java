package onion.shared;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtil {
    public static enum KEYS {IDENTITY, ONION};
    private static enum TYPE {PUBLIC, PRIVATE};
    
    public static PublicKey loadPublic(KEYS key){
        try{
            File f = openFile(key, TYPE.PUBLIC);
            byte content[] = readKey(f);
            
            return createPublicKey(content);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    public static PublicKey createPublicKey(byte key[]){
        try{
            X509EncodedKeySpec s = new X509EncodedKeySpec(key);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(s);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    public static PrivateKey loadPrivate(KEYS key){
        try{
            File f = openFile(key, TYPE.PRIVATE);
            byte content[] = readKey(f);
            
            return createPrivateKey(content);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    public static PrivateKey createPrivateKey(byte key[]){
        try{
            PKCS8EncodedKeySpec s = new PKCS8EncodedKeySpec(key);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            
            return factory.generatePrivate(s);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    private static byte[] readKey(File f)
            throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(f);
        DataInputStream dStream = new DataInputStream(stream);
        byte content[] = new byte[(int)f.length()];
        dStream.readFully(content);
        dStream.close();
        
        return content;
    }
    
    private static File openFile(KEYS key, TYPE type){
        String filename = "";
        switch(key){
            case IDENTITY:
                filename = "id";
                break;
            case ONION:
                filename = "onion";
                break;
        }
        
        filename += "_";
        
        switch(type){
            case PUBLIC:
                filename += "public";
                break;
            case PRIVATE:
                filename += "private";
                break;
        }
        
        filename += ".der";
        
        String path = "keys/" + filename;
        return new File(path);
    }
}
