package onion.client;

import java.util.ArrayList;
import java.util.Random;

public class PathBuilder {
    private final int pathLength = 3;
    
    public PathBuilder(){
        
    }
    
    public RouterInfo[] build(){
        ArrayList<RouterInfo> routers = NetworkState.dump();
        
        int numNodes = routers.size();
        if(numNodes < pathLength){
            System.out.println("Not enough nodes in the network for a path of length " + pathLength);
            return null;
        }
        
        int perm[] = generatePermutation(numNodes);
        
        RouterInfo result[] = new RouterInfo[pathLength];
        for(int i = 0; i < pathLength; i++)
            result[i] = routers.get(i);
        
        return result;
    }
    
    private int[] generatePermutation(int num){
        int a[] = new int[num];
        for(int i = 0; i < num; i++)
            a[i] = i;
        
        Random rand = new Random();
        for(int i = num - 1; i > 0; i--){
            int r = rand.nextInt(i + 1);
            
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
        
        int result[] = new int[pathLength];
        for(int i = 0; i < result.length; i++)
            result[i] = a[i];
        
        return result;
    }
}
