package ru.gb.antonov.lesson2.exercise1;

import ru.gb.antonov.lesson2.MyList;

import static ru.gb.antonov.Library.LIST_DELIMITER;

public class MyLinkedList<E> implements MyList<E> {

    private int size;
    private Node<E> head, tail;
    private final E[] empty;
//-----------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public MyLinkedList () {
        empty = (E[]) new Object[]{};
    }
//-----------------------------------------------------------------
    @Override public int size () { return size; }

/*  Замечание преподавателя (проигнорировано) : он может быть меньше 0?  */
    @Override public boolean isEmpty () { return size <= 0; }

    @Override public boolean add (E e) {
        Node<E> n = new Node<> (tail, e, null);
        if (tail == null)
            head = tail = n;
        else
            tail = tail.next = n;
        size ++;
        return true;
    }

    @Override public void add (int index, E e) {
        if (index > size || index < 0)
            throw new IllegalArgumentException();
        if (index == size)
            add (e);
        else {
            if (index == 0)
                head = new Node<> (null, e, head);
            else {
                Node<E> right = goTo (index),
                        left = right.prev,
                        n = new Node<> (left, e, right);
                right.prev = n;
                if (left != null)  left.next = n;
            }
            size ++;
        }
    }

    @Override public E set (int index, E e) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        Node<E> n = goTo(index);
        E previous = n.element;
        n.element = e;
        return previous;
    }

    @Override public E get (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        return goTo(index).element;
    }

    @Override public int indexOf (E object) {
        return firstIndexOf (object);
    }

    @Override public boolean contains (E object) {
        return firstIndexOf (object) >= 0;
    }

    @Override public E removeAt (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        return excludeNode (goTo (index));
    }

    @Override public boolean remove (E object) {
        Node<E> node = find (object);
        boolean ok = node != null;
        if (ok)  excludeNode (node);
        return ok;
    }

    @Override public void clear() {
        Node<E> node = head, tmp;
        while (node != null) {
            node.element = null;
            tmp = node;
            node = node.next;
            tmp.prev = null;
            tmp.next = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override public E[] toArray () {
        E[] result = empty;
        if (size > 0) {
            result = (E[]) new Object [size];
            Node<E> e = head;
            for (int i=0;  i<size;  i++) {
                result[i] = e.element;
                e = e.next;
            }
        }
        return result;
    }
//-----------------------------------------------------------------
/* отсутствие модификатора static не позоляет выполнить добавление 100 000 000 элементов, —
примерно через 60 сек. появляется ошибка OutOfMemoryError. С модификатором тест выполняется
за 20 сек. */

/*  Замечание преподавателя (проигнорировано) : нода недоступна снаружи, нужно делать приватной. а вот статической нет смысла  */
    private static class Node<E> {
        Node<E> prev, next;
        E element;

        Node (Node<E> p, E e, Node<E> n) {
            prev = p;
            next = n;
            element = e;
        }
        public String toString () {
            String p = prev == null ? null : prev.element.toString(),
                   n = next == null ? null : next.element.toString();
            return String.format ("%s<%s>%s", p, element, n);
        }
    }

    private Node<E> goTo (int index) {

        boolean b = index < (size >> 1);
        Node<E> result = b ? head : tail;
        if (b) {
            while (index-- > 0)
                result = result.next;
        }
        else {
            for (int i=size-1;  i>index;  i--)
                result = result.prev;
        }
        //System.out.print(", goTo >> " + index);
        return result;
    }

    private int firstIndexOf (E object) {
        int result = -1;
        int hashCode = (object == null) ? 0 : object.hashCode();
        Node<E> nd = head;

        for (int i=0;  nd != null;  i++) {
            E e = nd.element;
            if (e == null) {
                if (object == null) {
                    result = i;
                    break;
                }
            }
            else if (e.hashCode() == hashCode && e.equals(object)) {
                    result = i;
                    break;
            }
            nd = nd.next;
        }
        return result;
    }

    private Node<E> find (E object) {
        Node<E> result = head;
        int hashCode = (object == null) ? 0 : object.hashCode();

        while (result != null) {
            E e = result.element;
            if (e == null) {
                if (object == null)
                    break;
            }
            else if (e.hashCode() == hashCode && e.equals(object))
                    break;
            result = result.next;
        }
        return result;
    }

    private E excludeNode (Node<E> exclude) {
        E result = null;
        if (exclude != null) {
            result = exclude.element;
            Node<E> prv = exclude.prev,
                 nxt = exclude.next;
            if (prv != null)   prv.next = nxt;    else head = nxt;
            if (nxt != null)   nxt.prev = prv;    else tail = prv;
            size --;
        }
        return result;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(".[");
        for (Node<E> n=head;  n!=null;  n=n.next) {
            if (n != head)
                sb.append (LIST_DELIMITER);
            sb.append (n.element);
        }
        return sb.append("]sz=").append(size).toString();
    }
}
