package ru.gb.antonov.lesson3.exercise2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CocurrentCounter {

    Lock lock = new ReentrantLock (/*true*/);
    private       int value;
    private final int minValue = 0;

    public CocurrentCounter () {
        reset0();
    }

    private void reset0 () { value = 0; }

    public void reset () {
        lock.lock();
        try {
            reset0();
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            lock.unlock();
        }
    }

    public void increase () {
        lock.lock();
        value ++;
        lock.unlock();
    }

    public boolean decrease () {
        lock.lock();
        boolean ok = value > minValue;
        if (ok) value --;
        lock.unlock();
        return ok;
    }

    public int get () {
        lock.lock();
        int result = value;
        lock.unlock();
        return result;
    }
}
