package ru.gb.antonov.lesson2.exercise1;

import ru.gb.antonov.lesson2.MyList;

import static ru.gb.antonov.Library.LIST_DELIMITER;

public class MyLinkedList<E> implements MyList<E> {

    private int size;
    private Node head, tail;
    private final E[] empty = (E[]) new Object[]{};
//-----------------------------------------------------------------
    public MyLinkedList () {}
//-----------------------------------------------------------------
    @Override public int size () { return size; }

    @Override public boolean isEmpty () { return size <= 0; }

    @Override public boolean add (E e) {
        if (tail == null) {
            tail = new Node (null, e, null);
            head = tail;
        }
        else {
            Node tmp = tail;
            tail = new Node (tmp, e, null);
            tmp.next = tail;
        }
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
                head = new Node (null, e, head);
            else {
                Node right = goTo (index),
                     left = right.prev;
                right.prev = left.next = new Node (left, e, right);
            }
            size ++;
        }
    }

    @Override public E set (int index, E e) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        Node n = goTo(index);
        E previous = n.element;
        n.element = e;
        return previous;
    }

    @Override public E get (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        return goTo(index).element;
    }

    @Override public int indexOf (Object object) {
        return firstIndexOf (object);
    }

    @Override public boolean contains (Object object) {
        return firstIndexOf (object) >= 0;
    }

    @Override public E removeAt (int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException();
        return excludeNode (goTo (index));
    }

    @Override public boolean remove (Object object) {
        Node node = find (object);
        boolean ok = node != null;
        if (ok)  excludeNode (node);
        return ok;
    }

    @Override public void clear() {
        Node node = head, tmp;
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

    @Override public E[] toArray () {
        E[] result = empty;
        if (size > 0) {
            result = (E[]) new Object [size];
            Node e = head;
            for (int i=0;  i<size;  i++) {
                result[i] = e.element;
                e = e.next;
            }
        }
        return result;
    }
//-----------------------------------------------------------------
    class Node {
        Node prev, next;
        E element;

        Node (Node p, E e, Node n) {
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

    private Node goTo (int index) {

        boolean b = index < size/2;
        Node result = b ? head : tail;
        if (b) {
            while (index-- > 0)
                result = result.next;
        }
        else {
            for (int i=size-1;  i>index;  i--)
                result = result.prev;
        }
        return result;
    }

    private int firstIndexOf (Object object) {
        int result = -1;
        int hashCode = (object == null) ? 0 : object.hashCode();
        Node nd = head;

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

    private Node find (Object object) {
        Node result = head;
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

    private E excludeNode (Node exclude) {
        E result = null;
        if (exclude != null) {
            result = exclude.element;
            Node prv = exclude.prev,
                 nxt = exclude.next;
            if (prv != null)   prv.next = nxt;    else head = nxt;
            if (nxt != null)   nxt.prev = prv;    else tail = prv;
            size --;
        }
        return result;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(".[");
        for (Node n=head;  n!=null;  n=n.next) {
            if (n != head)
                sb.append (LIST_DELIMITER);
            sb.append (n.element);
        }
        return sb.append("]sz=").append(size).toString();
    }
}
