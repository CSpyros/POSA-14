package edu.vuum.mocca;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @param <E>
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore<E> {

	private Object[] items;
     
	/*
	 * NOTE: seems that i'm missing something here. i can't get rid of the IllegalMonitorStateException that is thrown by acquireUninterruptibly. 
	 * I try everything but without success :-(
	 */
	
    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
    	// TODO - you fill in here
   	
    	this.items = new Object[permits];
    	lock = new ReentrantLock(fair);
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
    	// TODO - you fill in here

    	lock.lockInterruptibly();
        try {
            try {
                while (availablePermits() <= 0)
                	notEmpty.await();
                ++count;
            } catch (InterruptedException ie) {
                notFull.signal(); // propagate to non-interrupted thread
                throw ie;
            }
        } finally {
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
        try {
        	while (availablePermits() <= 0)
				notEmpty.awaitUninterruptibly();
			++count;        
		} finally {
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
			--count;
			//notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Return the number of permits available.
     */
    public int availablePermits(){
    	// TODO - you fill in here
    	lock.lock();
        try {
            return items.length - count;
        } finally {
            lock.unlock();
        }    	
    }
    
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    //// You go ahead and put a reentrant lock here
    //// which if you watch the videos on condition 
    //// objects and watch the videos that we have on 
    //// some other examples
    //// to see how they use the reentrant lock
    
    Lock lock = new ReentrantLock();     

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
	//TODO - you fill in here
	//// You need to define the condition. and the 
	//// condition is basically an interface that is 
	//// intitialized by a factory method
	//// on reentrant lock called new condition
    Condition notFull  = lock.newCondition(); 
    Condition notEmpty = lock.newCondition();

    /**
     * Define a count of the number of available permits.
     */
	//TODO - you fill in here
	//// and then you go ahead and put in a count of the 
	//// number of available permits. and my 
	//// recommendation would be to make this
	//// volatile. you don't have to do that but that 
	//// would be one way to do that. you can also put 
	//// locks in the aquire permits method.
	int count;

}


