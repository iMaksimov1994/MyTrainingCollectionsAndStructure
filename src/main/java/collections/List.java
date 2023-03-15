package collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public class List<T> implements Iterable<T> {
    public class Entry {
        private Entry prev;
        private Entry next;
        private T value;

        public Entry(T value) {
            this.value = value;
        }
    }

    private Entry top;
    private Entry tail;
    private int count;

    public void add(T value) {
        Entry entry = new Entry(value);
        if (this.count == 0) {
            this.top = entry;
        } else {
            this.tail.next = entry;
            entry.prev = this.tail;
        }
        this.tail = entry;
        this.count++;
    }

    public void addFirst(T value) {
        Entry entry = new Entry(value);
        if (this.count == 0) {
            this.add(value);
        } else {
            this.top.prev = entry;
            entry.next = this.top;
            this.top = entry;
            this.count++;
        }
    }

    public T pop() {
        if (this.count == 0) {
            return null;
        }

        T value = this.top.value;
        this.top = this.top.next;
        this.count--;
        return value;
    }

    public T popEnd() {
        if (this.count == 0) {
            return null;
        }
        T value = this.tail.value;
        this.tail = this.tail.prev;
        this.count--;
        return value;
    }

    public void remove(int number) {
        Entry entry = this.top;
        if (number == 0) {
            this.pop();
        } else {
            for (int i = 1; i <= number; i++) {

                if (i == number) {
                    this.top.next = this.top.next.next;
                    this.count--;
                }
                this.top = this.top.next;
            }
            this.top = entry;
        }
    }


    @Override
    public String toString() {
        Entry entry = this.top;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.count; i++) {
            stringBuilder.append(this.top.value).append('\n');
            this.top = this.top.next;
        }
        this.top = entry;
        return stringBuilder.toString();
    }

    public List<T> subList(int first, int end) {
        Entry entry = this.top;
        List<T> list = new List<>();
        for (int i = 0; i < this.count; i++) {
            if (i >= first && i <= end) {
                list.add(this.top.value);
            }
            this.top = this.top.next;
        }
        this.top = entry;
        return list;
    }

    public T[] toArray(T[] ts) {
        Entry entry = this.top;
        T[] ts1 = (T[]) Array.newInstance(ts.getClass().getComponentType(), this.count);
        for (int i = 0; i < this.count; i++) {
            ts1[i] = this.top.value;
            this.top = this.top.next;
        }
        this.top = entry;
        return ts1;
    }

    public void insert(int number, T value) {
        Entry current = this.top;
        Entry newEntry = new Entry(value);
        for (int i = 0; i < this.count; i++) {
            if (number == i) {
                newEntry.next = current;
                newEntry.prev = current.prev;
                current.prev.next = newEntry;
                current.prev = newEntry;
                this.count++;
                return;
            }
            current = current.next;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Entry entry = top;

            @Override
            public boolean hasNext() {
                return entry != null;
            }

            @Override
            public T next() {
                T value = entry.value;
                entry = entry.next;
                return value;
            }
        };
    }
}
