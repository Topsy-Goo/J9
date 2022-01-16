package ru.gb.antonov.lesson2.exercise2;

import ru.gb.antonov.lesson2.MyList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static ru.gb.antonov.Library.LIST_DELIMITER;

public class MyArrayList<E> implements MyList<E> {

    private int capacity = 10;
    private int size;
    private Object[] data;
    private float growFactor = 0.5f;
    static BigInteger bigmax = BigInteger.valueOf (Integer.MAX_VALUE);

//-----------------------------------------------------------------
    public MyArrayList () {
        data = new Object[capacity];
    }

    public MyArrayList (int initialCapacity) {
        if (initialCapacity > capacity)
            capacity = initialCapacity;
        data = new Object [capacity];
    }
//-----------------------------------------------------------------
    @Override public int size () {  return size;  }

    @Override public boolean isEmpty () {  return size <= 0;  }

    @Override public boolean add (E e) {
        if (size >= capacity)
            grow();
        data [size++] = e;
        return true;
    }

    @Override public void add (int index, E e) {
        if (index > size || index < 0)
            throw new IllegalArgumentException();
        if (index == size)
            add (e);
        else {
            if (size >= capacity)
                grow();
            System.arraycopy (data, index, data, index +1, size - index);
            size++;
            data [index] = e;
        }
    }

    @Override public E set (int index, E e) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        E previous = (E) data [index];
        data [index] = e;
        return previous;
    }

    @Override public E get (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        return (E) data [index];
    }

    @Override public int indexOf (Object object) {
        return find (object);
    }

    @Override public boolean contains (Object object) {
        return find (object) >= 0;
    }

    @Override public E removeAt (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        E removed = (E) data [index];
        System.arraycopy (data, index +1, data, index, size - index -1);
        size--;
        return removed;
    }

    @Override public boolean remove (Object object) {
        int index = find(object);
        boolean ok = index >= 0;
        if (ok) removeAt (index);
        return ok;
    }

    @Override public void clear() {
        for (int i=0;  i<size;  i++)
            data [i] = null;
        size = 0;
    }

    @Override public E[] toArray () {
        return Arrays.copyOf ((E[])data, capacity);
    }
//-----------------------------------------------------------------
    private void grow () {
        if (BigInteger.valueOf (capacity).compareTo (bigmax) >= 0)
            throw new OutOfMemoryError();

        BigInteger cap = BigDecimal.valueOf (growFactor)
                                   .multiply (BigDecimal.valueOf (capacity))
                                   .add (BigDecimal.valueOf (capacity))
                                   .toBigInteger();

        capacity = (cap.compareTo (bigmax) < 0) ? cap.intValue() : bigmax.intValue();
        data = Arrays.copyOf (data, capacity);
        //lnprint ("new capacity = "+ capacity);
    }

    private int find (Object object) {
        int result = -1;
        int hashCode = (object == null) ? 0 : object.hashCode();

        for (int i=0;  i<size;  i++) {
            Object o = data [i];
            if (o == null) {
                if (object == null) {
                    result = i;
                    break;
                }
            } else if (o.hashCode() == hashCode && o.equals (object)) {
                    result = i;
                    break;
            }
        }
        return result;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(".[");
        for (int i=0;  i<size;  i++) {
            if (i > 0)
                sb.append (LIST_DELIMITER);
            sb.append (data[i]);
        }
        return sb.append("]sz=").append(size).toString();
    }
}
