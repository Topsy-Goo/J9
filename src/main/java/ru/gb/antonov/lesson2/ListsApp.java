package ru.gb.antonov.lesson2;

import static ru.gb.antonov.Library.*;

import ru.gb.antonov.lesson2.exercise1.MyLinkedList;
import ru.gb.antonov.lesson2.exercise2.MyArrayList;

public class ListsApp {

    public static void main (String[] args) {
        MyArrayList<Integer> al = new MyArrayList<>(1);
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

        MyLinkedList<Integer> ll = new MyLinkedList<>();
        lnprint (ll.toString());
        for (int i=0; i<8; i++)
            ll.add(i);
        ll.add(null);
        lnprint (ll.toString());
        ll.add(0,5);
        ll.add(ll.size(),6);
        ll.add(ll.size()/2,7);
        lnprint (ll.toString());
        ll.set(0, 6);
        ll.set(ll.size()/2, 5);
        ll.set(ll.size()-1, 8);
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
        lnprint (ll.toString());
        ll.removeAt (ll.size()/2);
        lnprint (ll.toString());
        ll.removeAt (0);
        lnprint (ll.toString());
        lnprint ("remove (0) = "+ ll.remove (0));
        lnprint ("remove (15)= "+ ll.remove (15));
        lnprint ("remove (5) = "+ ll.remove (5));
        lnprint ("remove (7) = "+ ll.remove (7));
        lnprint ("remove (null) = "+ ll.remove (null));
        lnprint (ll.toString());
        ll.clear();
        lnprint ("clear() -> "+ ll.toString());
    }
}
