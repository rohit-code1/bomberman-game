package de.tha.prog2.praktikum.game.container;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import de.tha.prog2.praktikum.game.exceptions.DuplicateObjectException;
import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;
import de.tha.prog2.praktikum.game.exceptions.ObjectNotFoundException;

public class Vector<T> extends AbstractContainer<T> {
    private int changes = 0;
    private Class<T> clazz;
    private T[] oa;
    private int i = 0;

    public Vector(Class<T> clazz) {
        this.clazz = clazz;
        this.oa = (T[]) Array.newInstance(clazz, 10);
    }

    @Override
    public boolean add(T o) {
        if (contains(o)) {
            throw new DuplicateObjectException(
                "Duplicate Objects are not allowed in Vector Container: Object " + o + " is already in this Container");
        }
        if (i < oa.length) {
            oa[i++] = o;
        } else {
            oa = resizeArrayWithAdd(o);
            i++;
        }
        changes++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalGetException(
                "Index " + index + " is out of Boundry of Vector Container: Accepted boundry = [0 - " + size() + "]");
        }
        return oa[index];
    }

    private T[] resizeArrayWithAdd(T o) {
        T[] newArray = (T[]) Array.newInstance(clazz, oa.length + 1);
        System.arraycopy(oa, 0, newArray, 0, oa.length);
        newArray[newArray.length - 1] = o;
        return newArray;
    }

    private T[] resizeArrayAfterRemove() {
        T[] newArray = (T[]) Array.newInstance(clazz, size());
        System.arraycopy(oa, 0, newArray, 0, newArray.length);
        return newArray;
    }

    @Override
    public int size() {
        int count = 0;
        for (T obj : oa) {
            if (obj != null) count++;
        }
        return count;
    }

    @Override
    public boolean remove(T o) {
        if (!contains(o)) {
            throw new ObjectNotFoundException(
                "Object is not available in Vector Container: Object " + o + " is not in this Container");
        }
        int removedIndex = -1;
        for (int j = 0; j < oa.length; j++) {
            if (o.equals(oa[j])) {
                removedIndex = j;
                break;
            }
        }
        for (int j = removedIndex; j < oa.length - 1; j++) {
            oa[j] = oa[j + 1];
        }
        oa[oa.length - 1] = null;
        i--;
        oa = resizeArrayAfterRemove();
        changes++;
        return true;
    }

    @Override
    public Iterator<T> iteratorRandom() {
        return new VectorIteratorRandom();
    }

    private class VectorIteratorRandom implements Iterator<T> {
        private int cursor = 0;
        private final int expectedChanges = changes;
        private final int[] order = createRandomOrder();
        private final Random rand = new Random();

        private int[] createRandomOrder() {
            int n = size();
            int[] ord = new int[n];
            for (int k = 0; k < n; k++) {
                int r;
                do {
                    r = rand.nextInt(n);
                } while (contains(r, ord, k));
                ord[k] = r;
            }
            return ord;
        }

        private boolean contains(int v, int[] arr, int upTo) {
            for (int j = 0; j < upTo; j++) {
                if (arr[j] == v) return true;
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            checkMod();
            return cursor < order.length;
        }

        @Override
        public T next() {
            checkMod();
            if (!hasNext()) throw new NoSuchElementException();
            return get(order[cursor++]);
        }

        private void checkMod() {
            if (changes != expectedChanges) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new VectorIterator();
    }

    private class VectorIterator implements Iterator<T> {
        private int cursor = 0;
        private final int expectedChanges = changes;

        @Override
        public boolean hasNext() {
            checkMod();
            return cursor < size();
        }

        @Override
        public T next() {
            checkMod();
            if (!hasNext()) throw new NoSuchElementException(
                "Index " + cursor + " is out of Boundry of Vector Container: Accepted boundry = [0 - " + size() + "]");
            return get(cursor++);
        }

        private void checkMod() {
            if (changes != expectedChanges) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public static void main(String[] args) {
        Vector<String> v = new Vector<>(String.class);
        v.add("first String");
        v.add("second String");
        v.add("third String");
        v.clear();
        System.out.println("size of array: " + v.size());
        v.add("first String");
        v.add("second String");
        v.add("third String");
        System.out.println("size of array: " + v.size());
        v.remove("second String");
        System.out.println("size of array: " + v.size());
        System.out.println(v.get(0));
        System.out.println(v.get(1));
    }
}
