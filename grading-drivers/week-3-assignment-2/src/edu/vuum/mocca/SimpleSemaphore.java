package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore {
    /**
     * Constructor initialize the data members.  
     */
	
	/**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private  Lock lock = null;

    
    
    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    private Condition numberOfPermitsIsZero = null;;
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here
    int numberOfPerimits=0;
	
	
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
        // TODO - you fill in here
    	numberOfPerimits=permits;
    	lock=new ReentrantLock(fair);
    	numberOfPermitsIsZero = lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
    	lock.lockInterruptibly();
    	try
    	{
	    	while(numberOfPerimits==0)
	    	{
	    		numberOfPermitsIsZero.await();
	    	}
	    	numberOfPerimits--;
    	}finally
    	{
    		lock.unlock();	
    	}
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
    	lock.lock();
    	
    	try{
	    	while(numberOfPerimits==0)
	    	{
	    		try {
					numberOfPermitsIsZero.await();
				} catch (InterruptedException e) {
				}
	    	}
	    	numberOfPerimits--;
    	}finally
    	{
    		lock.unlock();	
    	}
    	
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
    	lock.lock();
    	try {
			numberOfPerimits++;
			numberOfPermitsIsZero.signal();
		} finally{
			lock.unlock();
		}
    	
    }

    
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return numberOfPerimits;
    }
    
    
}

