package ru.gb.antonov.lesson3.exercise1;

import static ru.gb.antonov.Library.lnprint;
import static ru.gb.antonov.Library.print;

public class PingPong {
    static       int     counter;
    static final int     maxCounter = 10;
    static final Object  MON = new Object();

    public static void main (String[] args) throws InterruptedException {

        Thread ping = new Thread (()->play ("\nping,"), "Payer1"),
               pong = new Thread (()->play (" pong."), "Payer2");
        ping.start();
        synchronized (MON) {
            MON.wait();
            pong.start();
        }
        ping.join();
        pong.join();
        lnprint ("counter = "+ counter);
    }

    static void play (String text) {
        try {
            while (counter < maxCounter)
                synchronized (MON) {
                    print (text);
                    counter++;
                    MON.notify();
                    MON.wait (/*2000*/);
                }
            synchronized (MON) {
                MON.notify();
            }
        }
        catch (InterruptedException e) { e.printStackTrace(); }
        finally {
            lnprint ("Завершился поток "+ Thread.currentThread().getName());
        }
    }
}
