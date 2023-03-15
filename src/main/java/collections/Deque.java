package collections;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class Deque<T> implements Iterable<T> {
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

    public Deque() {
    }

    public Deque(Deque<T> deque) {
        Deque<T> dequeNew = new Deque<>();
        for (int i = 0; i < deque.count; i++) {
            dequeNew.add(deque.top.value);
            deque.top = deque.top.next;
        }
    }

    public void add(T value) {
        Entry entry = new Entry(value);
        if (this.count == 0) {
            this.top = entry;
        } else {
            entry.prev = this.tail;
            this.tail.next = entry;
        }
        this.tail = entry;
        this.count++;
    }

    public T pop() {
        if (this.count == 0) {
            return null;
        }
        T value = this.tail.value;
        this.tail = this.tail.prev;
        this.count--;
        return value;
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

    public T peek() {
        return this.tail.value;
    }

    public T search(int number) {
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            if (i == number) {
                T value = this.top.value;
                this.top = entry;
                return value;
            }
            this.top = this.top.next;
        }
        this.top = entry;
        return null;
    }

    public boolean contains(T value) {
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            if (this.top.value.equals(value)) {
                return true;
            }
            this.top = this.top.next;
        }
        this.top = entry;
        return false;
    }

    public Deque<T> reverse() {
        Entry entry = this.tail;
        Deque<T> deque = new Deque<>();
        for (int i = this.count; i > 0; i--) {
            deque.add(this.tail.value);
            this.tail = this.tail.prev;
        }
        this.tail = entry;
        return deque;
    }

    /*public void insert(int number, T value) {
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            if (i == number) {
                this.top.value = value;
                break;
            }
            this.top = this.top.next;
        }
        this.top = entry;
    }*/
    public void insert(int number, T value) {
        Entry entry = this.top;
        Entry current = this.top;
        Entry newEntry = new Entry(value);
        for (int i = 0; i < this.count; i++) {
            if (number == i) {
                newEntry.next = current;
                newEntry.prev = current.prev;
                current.prev = newEntry;
                current.prev.next = newEntry;
                this.count++;
                break;
            }
            current = current.next;
        }
    }

    public void addAll(Deque<T> deque) {
        for (int i = 0; i < deque.count; i++) {
            this.add(deque.top.value);
            deque.top = deque.top.next;
        }
    }

    public void addAll(int number, Deque<T> deque) {
        for (int i = 0; i < deque.count; i++) {
            if (i >= number) {
                this.add(deque.top.value);
                deque.top = deque.top.next;
                continue;
            }
            deque.top = deque.top.next;
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

    public Deque<T> intersect(Deque<T> deque) {
        Deque<T> dequeNew = new Deque<>();
        T[] ts = toArray((T[]) Array.newInstance(deque.top.value.getClass(), deque.count));
        T[] ts1 = deque.toArray((T[]) Array.newInstance(this.top.value.getClass(), this.count));
        System.out.println(Arrays.toString(ts));
        System.out.println(Arrays.toString(ts1));
        for (int i = 0; i < ts.length; i++) {
            for (int j = 0; j < ts1.length; j++) {
                if (ts[i].equals(ts1[j])) {
                    dequeNew.add(ts[i]);
                    break;
                }
            }
        }
        return dequeNew;
    }
}
