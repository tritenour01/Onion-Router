package onion.shared;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigHelper {
    private static ConfigHelper instance = null;
    
    private Properties config = null;
    
    private ConfigHelper(){
        String filename = "config.properties";
        config = new Properties();
        
        try{
            FileInputStream file = new FileInputStream(filename);
            config.load(file);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static ConfigHelper getInstance(){
        if(instance == null){
            instance = new ConfigHelper();
        }
        return instance;
    }
    
    public String getValue(String key){
        return config.getProperty(key);
    }
}