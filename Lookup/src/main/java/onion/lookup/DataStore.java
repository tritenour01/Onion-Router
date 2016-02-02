package onion.lookup;

import java.util.ArrayList;
import java.util.Iterator;

public class DataStore {
    private static final ArrayList<DataEntity> data = new ArrayList<DataEntity>();
    
    private DataStore(){}
    
    public static RouterInfo lookupByKey(String key){
        DataEntity entity = keyLookup(key);
        if(entity == null)
            return null;
        else
            return entity.getData();
    }
    
    private static DataEntity keyLookup(String key){
        Iterator<DataEntity> iter = data.iterator();
        while(iter.hasNext()){
            DataEntity entity = iter.next();
            RouterInfo info = entity.getData();
            if(info.getIdKey().equals(key))
                return entity;
        }
        return null;
    }
    
    public static void insert(RouterInfo instance){
        DataEntity entity = keyLookup(instance.getIdKey());
        if(entity == null){
            DataEntity newData = new DataEntity(instance);
            data.add(newData);
        }
        else{
            entity.setData(instance);
            entity.updateLastPing();
        }
    }
}
