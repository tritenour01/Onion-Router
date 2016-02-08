package onion.client;

import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingFuture<T> implements Future<T> {

    private Semaphore lock;
    private T result;
    
    public BlockingFuture(){
        lock = new Semaphore(1);
        
        try{
            lock.acquire();
        }
        catch(InterruptedException e){
            System.out.println("initial semaphore aquire failed");
        }
    }
    
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return !(result == null);
    }

    @Override
    public T get() throws InterruptedException {
        lock.acquire();
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        if(lock.tryAcquire(timeout, unit)){
            return result;
        }
        else{
            throw new TimeoutException();
        }
    }
    
    public void put(T val){
        result = val;
        lock.release();
    }
    
}
