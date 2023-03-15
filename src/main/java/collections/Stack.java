package collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    public Stack() {
    }

    public class Entry {
        private Entry prev;
        private T value;

        public Entry(Entry prev, T value) {
            this.prev = prev;
            this.value = value;
        }

        public Entry(T value) {
            this.value = value;
        }
    }

    private Entry top;
    private int count;

    public void add(T value) {
        this.top = new Entry(this.top, value);
        this.count++;
    }

    public T pop() {
        if (this.count == 0) {
            return null;
        }
        this.top = this.top.prev;
        this.count--;
        return this.top.value;

    }

    @Override
    public String toString() {
        Entry entry = this.top;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.count; i++) {
            stringBuilder.append(this.top.value).append('\n');
            this.top = this.top.prev;
        }
        this.top = entry;
        return stringBuilder.toString();
    }

    public T peek() {
        return this.top.value;
    }

    public int search(T value) {
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            if (this.top.value.equals(value)) {
                return i;
            }
            this.top = this.top.prev;
        }
        this.top = entry;
        return -1;
    }

    public Stack(Stack<T> stack) {
        Entry entry = stack.top;
        for (int i = 0; i < stack.count; i++) {
            this.add(stack.top.value);
            stack.top = stack.top.prev;
        }
        stack.top = entry;
    }

    public void reverse() {
        Stack<T> stack = new Stack<>(this);
        this.top = stack.top;
    }

    public void replace(int number, T value) {
        Entry entry = this.top;
        for (int i = 0; i < this.count; i++) {
            if (i == number) {
                this.top.value = value;

            }
            this.top = this.top.prev;
        }
        this.top = entry;
    }

    public T remove(int number) {
        if (number == 0) {
            return pop();
        }
        Entry current = this.top;
        Entry prev = current.prev;
        for (int i = 0; i < number; i++) {
            current = prev;
            prev = current.prev;
        }
        this.count--;
        prev.prev = current.prev;
        return current.value;
    }

    public void addAll(Stack<T> stack) {
        Entry entry = stack.top;
        for (int i = 0; i < stack.count; i++) {
            this.add(entry.value);
            entry = entry.prev;
        }
    }

    public void addAll(int number, Stack<T> stack) {
        Entry entry = stack.top;
        for (int i = 0; i < stack.count; i++) {
            if (i >= number) {
                this.add(entry.value);
            }
            entry = entry.prev;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Entry current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.prev;
                return value;
            }
        };
    }

    private void reverse(T[] ts) {
        for (int i = 0; i < ts.length / 2; i++) {
            T tmp = ts[i];
            ts[i] = ts[ts.length - 1];
            ts[ts.length - 1] = tmp;
        }
    }

    public T[] toArray(T[] ts) {
        Entry entry = this.top;
        T[] ts1 = (T[]) Array.newInstance(ts.getClass().getComponentType(), this.count);
        for (int i = 0; i < this.count; i++) {
            ts1[i] = this.top.value;
            this.top = this.top.prev;

        }
        this.top = entry;
        reverse(ts1);
        return ts1;

    }

    public Stack<T> intersect(Stack<T> stack) {
        Stack<T> stack1 = new Stack<>();
        T[] ts = this.toArray((T[]) Array.newInstance(this.top.value.getClass(), this.count));
        T[] ts1 = stack.toArray((T[]) Array.newInstance(stack.top.value.getClass(), stack.count));
        for (int i = 0; i < ts.length; i++) {
            for (int j = 0; j < ts1.length; j++) {
                if (ts[i].equals(ts1[j])) {
                    stack1.add(ts[i]);
                    break;
                }
            }
        }
        return stack1;
    }
}
