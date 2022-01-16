package ru.gb.antonov.lesson2;

import static ru.gb.antonov.Library.*;

import ru.gb.antonov.lesson2.exercise1.MyLinkedList;
import ru.gb.antonov.lesson2.exercise2.MyArrayList;

import java.util.LinkedList;
import java.util.Random;

public class ListsApp {

    static final boolean ADD_TO_MIDDLE_TEST = true;

    public static void main (String[] args) {
        MyLinkedList<Integer> ll = new MyLinkedList<>();
        //generalMyArrayListTest (new MyArrayList<>(1));
        //generalMyLinkedListTest (ll);
        testIntAddition (100_000, ll, new LinkedList<>(), ADD_TO_MIDDLE_TEST);
    }

    static void generalMyArrayListTest (MyArrayList<Integer> al) {
        lnprint (al.toString());
        al.add(0);
        lnprint (al.toString());
        al.add(1);
        al.add(2);
        al.add(3);
        al.add(4);
        lnprint (al.toString());
        al.add(5, 80);
        lnprint (al.toString());
        al.removeAt (1);
        lnprint (al.toString());
        al.removeAt (al.size()-1);
        lnprint (al.toString());
        al.remove(2);
        lnprint (al.toString());
        lnprint ("contains(80) = "+al.contains(80));
        lnprint ("contains(15) = "+al.contains(15));
        lnprint ("indexOf(3) = "+al.indexOf(3));
        lnprint ("indexOf(15) = "+al.indexOf(15));
        lnprint ("get(1) = "+al.get(1));
        al.set(2,15);
        lnprint (al.toString());
        al.clear();
        lnprint ("clear() -> "+ al.toString());
        lnprint("--------------------------------");
    }

    static void generalMyLinkedListTest (MyLinkedList<Integer> ll) {
        lnprint (ll.toString());
        for (int i=0; i<8; i++)
            ll.add(i);
        ll.add(null);
        lnprint (ll.toString());
        ll.add(0,5);
        ll.add(ll.size(),16);
        ll.add(ll.size()/2,17);
        lnprint (ll.toString());
        ll.set(0, 6);
        ll.set(ll.size()/2, 45);
        ll.set(ll.size()-1, 48);
        lnprint (ll.toString());
        lnprint (""+ll.get(0));
        lnprint (""+ll.get(ll.size()/2));
        lnprint (""+ll.get(ll.size()-1));
        lnprint ("indexOf 3 = "+ll.indexOf(3));
        lnprint ("indexOf 15 = "+ll.indexOf(15));
        lnprint ("contains 6 = "+ll.contains(6));
        lnprint ("contains 8 = "+ll.contains(8));
        lnprint ("contains 15 = "+ll.contains(15));
        ll.removeAt (ll.size()-1);
        lnprint ("ll.removeAt (ll.size()-1) - "+ll.toString());
        ll.removeAt (ll.size()/2);
        lnprint ("ll.removeAt (ll.size()/2) - "+ll.toString());
        ll.removeAt (0);
        lnprint ("ll.removeAt (0) - "+ll.toString());
        lnprint ("remove (0) = "+ ll.remove (0));
        lnprint ("remove (15)= "+ ll.remove (15));
        lnprint ("remove (5) = "+ ll.remove (5));
        lnprint ("remove (7) = "+ ll.remove (7));
        lnprint ("remove (null) = "+ ll.remove (null));
        lnprint (ll.toString());
        ll.clear();
        lnprint ("clear() -> "+ ll.toString());
    }

    static void testIntAddition (int n, MyLinkedList<Integer> ll, LinkedList<Integer> jll, boolean testType) {
        Random r = new Random(47);
        long start, finish;
        boolean b = testType == ADD_TO_MIDDLE_TEST;
        String className;
        int logstep = 10_000;

        if (ll != null) {
            className = ll.getClass().getSimpleName();
            start = System.currentTimeMillis();
            for (int i=0, j;  i<n;  i++) {
                if (b) {
                    ll.add (j=i>>1, r.nextInt ());
                    if (j % logstep == 0 && (i&1) == 0)
                        System.out.print(j+" ");
                } else
                    ll.add (r.nextInt ());
            }
            finish = System.currentTimeMillis();
            if (b) lnprint (className +" - добавление "+n+" элементов в середину списка: "+ (finish - start) + " мс\n");
            else {
                lnprint (className +" - добавление "+n+" элементов: "+ (finish - start) + " мс");
                start = System.currentTimeMillis();
                ll.get(n>>1);
                finish = System.currentTimeMillis();
                lnprint (className +" - переход к элементу "+n/2+" выполнен за: "+ (finish - start) + " мс");
            }
        }

        if (jll != null) {
            className = jll.getClass().getSimpleName();
            start = System.currentTimeMillis();
            for (int i=0, j;  i<n;  i++) {
                if (b) {
                    jll.add (j=i>>1, r.nextInt ());
                    if (j % logstep == 0 && (i&1) == 0)
                        System.out.print(j+" ");
                } else
                    jll.add (r.nextInt ());
            }
            finish = System.currentTimeMillis();
            if (b) lnprint (className +" - добавление "+n+" элементов в середину списка: "+ (finish - start) + " мс");
            else {
                lnprint (className +" - добавление "+n+" элементов: "+ (finish - start) + " мс");
                start = System.currentTimeMillis();
                jll.get(n>>1);
                finish = System.currentTimeMillis();
                lnprint (className +" - переход к элементу "+n/2+" выполнен за: "+ (finish - start) + " мс");
            }
        }
    }
}
