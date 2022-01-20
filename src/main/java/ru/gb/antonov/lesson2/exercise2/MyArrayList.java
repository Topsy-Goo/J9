package ru.gb.antonov.lesson2.exercise2;

import ru.gb.antonov.lesson2.MyList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.RandomAccess;

import static ru.gb.antonov.Library.LIST_DELIMITER;

public class MyArrayList<E> implements MyList<E>, RandomAccess {

    private int capacity = 10;  /*  Замечание преподавателя (проигнорировано) : можно вообще не использовать эту переменну. она всегда равна длине массива  */
    private int size;
    private Object[] data;
    private float growFactor = 0.5f;
    static final BigInteger bigmax = BigInteger.valueOf (Integer.MAX_VALUE);
/*  Замечание преподавателя (проигнорировано) : зачем эта статика, да еще и не final, если всегда есть константа по Integer.MAX_VALUE  */

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
        boolean ok = true;
        try {
            if (size >= capacity)
                grow (growFactor);
            data [size++] = e;
        }
        catch (RuntimeException exception) {
            ok = false;
        }
        return ok;
    }

    @Override public void add (int index, E e) {
        if (index > size || index < 0)
            throw new IllegalArgumentException();
        if (index == size)
            add (e);
        else {
            if (size >= capacity)
                grow (growFactor);
            System.arraycopy (data, index, data, index +1, size - index);
            size++;
            data [index] = e;
        }
    }

    @SuppressWarnings("unchecked")
    @Override public E set (int index, E e) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        E previous = (E) data [index];
        data [index] = e;
        return previous;
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override public E[] toArray () {
        return Arrays.copyOf ((E[])data, capacity);
    }
//-----------------------------------------------------------------
/** Позволяет изменить значение drowFactor.
@param gf новое значение для drowFactor.
@return TRUE если {@code gf >= 0.1f;} */
    public boolean setGrowFactor (float gf) {
        boolean ok = gf > 0.1f;
        if (ok)
            growFactor = gf;
        return ok;
    }

    private void grow (float factor) {
        if (factor > 0.0f) {
            if (BigInteger.valueOf (capacity).compareTo (bigmax) >= 0)
                throw new OutOfMemoryError();

/*  Замечание преподавателя (проигнорировано) : перемножение значения близкого к Integer.MAX_VALUE с 1.5f все равно выйдет за границу типа. сделали сложные вычисления, но смысл их полностью потерян. с этим же успехом можно всегда перемножать обычные int и float с округлением и не заморачиваться сложными расчетами */
            BigInteger cap = BigDecimal.valueOf (factor)
                                       .multiply (BigDecimal.valueOf (capacity))
                                       .add (BigDecimal.valueOf (capacity))
                                       .toBigInteger();

            capacity = (cap.compareTo (bigmax) < 0) ? cap.intValue() : bigmax.intValue();
            data = Arrays.copyOf (data, capacity);
        }
        else throw new IllegalArgumentException();
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
