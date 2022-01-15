package ru.gb.antonov.lesson1.exercise2;

public class Lorry extends Car implements Moveable, Stopable {

    @Override void open()        {    System.out.println ("Car is open");    }
    @Override public void move() {
        start();
        System.out.println ("Car is moving");
    }
    @Override public void stop() {    System.out.println ("Car is stop");    }
}
