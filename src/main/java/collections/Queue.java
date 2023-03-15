package collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
    public class Entry {
        private Entry next;
        private T value;

        public Entry() {
        }

        public Entry(T value) {
            this.value = value;
        }
    }

    private Entry top;
    private Entry tail;
    private int count;

    public void add(T value) {
        Entry newEntry = new Entry(value);
        if (this.count == 0) {
            this.top = newEntry;
        } else {
            this.tail.next = newEntry;
        }
        this.tail = newEntry;
        this.count++;
    }

    public T pop() {
        if (this.count == 0) {
            return null;
        } else {
            T value = this.top.value;
            this.top = this.top.next;
            this.count--;
            return value;
        }
    }

    public T peek() {
        return this.top.value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            stringBuilder.append("[").append(this.top.value);
            this.top = this.top.next;
            if (i == this.count - 1) {
                stringBuilder.append("]");
            }
        }
        this.top = entry;
        return stringBuilder.toString();
    }

    public T search(T value) {
        Entry entry = this.top;
        T valueResult = this.top.value;
        for (int i = 0; i < this.count; i++) {
            if (valueResult.equals(value)) {
                this.top = entry;
                return valueResult;
            }
            this.top = this.top.next;
        }
        this.top = entry;
        return null;
    }

    private void reverseFoo(Queue<T> queue, Entry entry) {
        if (entry != null) {
            reverseFoo(queue, entry.next);
            queue.add(entry.value);
        }
    }

    public Queue<T> reverse() {
        Queue<T> queue = new Queue<>();
        reverseFoo(queue, this.top);
        return queue;
    }

    public T remove(int number) {
        if (number == 0) {
            return pop();
        }
        Entry current = this.top;
        Entry next = this.top.next;
        for (int i = 0; i < number; i++) {
            next = current;
            current = current.next;
        }
        next.next = current.next;
        this.count--;
        return current.value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Entry entry = top;

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

    public Queue<T> intersect(Queue<T> queue) {
        Queue<T> queue1 = new Queue<>();
        T[] ts = (T[]) Array.newInstance(this.top.getClass().getComponentType(), this.count);
        T[] ts1 = (T[]) Array.newInstance(queue.getClass().getComponentType(), queue.count);
        for (T t : ts) {
            for (T value : ts1) {
                if (t.equals(value)) {
                    queue1.add(t);
                    break;
                }
            }
        }
        return queue1;
    }
}
