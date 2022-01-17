package ru.gb.antonov.lesson2;

    public interface MyList<E> {
    int size ();
    boolean isEmpty ();
    boolean add (E e);  /* < Замечание преподавателя (проигнорировано) : для листа boolean Тут не актуален, как говорили на уроке в оригинальном интерфейсе это легаси. для своего интерфейса повторять нет смысла */
    void add (int index, E e);
    E set (int index, E e);
    E get (int index);
    int indexOf (E object);
    boolean contains (E object);
    E removeAt (int index);
    boolean remove (E object);
    void clear();
    E[] toArray ();

/*  Замечание преподавателя (проигнорировано) : везде где Object стоит использовать дженерик, нет смысла делать проверку на объекты иного типа  */
}
