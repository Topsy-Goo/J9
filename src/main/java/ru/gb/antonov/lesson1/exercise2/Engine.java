package ru.gb.antonov.lesson1.exercise2;

/*  Замечание преподавателя (проигнорировано) : почему stoppable? это интерфейс для движения машины. двигатель сам никуда не ездит и останавливаться от движения не должен.  */
public class Engine implements Stopable {
    @Override public void stop () {    System.out.println ("Engine is stopped.");    }
}
