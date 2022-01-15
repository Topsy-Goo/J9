package ru.gb.antonov.lesson1.exercise2;

abstract public class Car {
    private Engine engine;
    private String color;
    private String name;

    protected void start() {    System.out.println ("Car starting");    }

    abstract void open();

    public void setEngine (Engine engine) {    this.engine = engine;    }
    public void setColor (String color) {    this.color = color;    }
    public void setName (String name) {    this.name = name;    }

    public Engine getEngine() {    return engine;    }
    public String getColor() {    return color;    }
    public String getName() {    return name;    }
}
