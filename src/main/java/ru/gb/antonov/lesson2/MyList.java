package ru.gb.antonov.lesson2;

    public interface MyList<E> {
    int size ();
    boolean isEmpty ();
    boolean add (E e);
    void add (int index, E e);
    E set (int index, E e);
    E get (int index);
    int indexOf (Object object);
    boolean contains (Object object);
    E removeAt (int index);
    boolean remove (Object object);
    void clear();
    E[] toArray ();
}
