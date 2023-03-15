package collections;

import java.lang.reflect.Array;
import java.util.*;

public class HashTable<K, T> implements Iterable<T> {
    public class Entry {
        private K key;
        private T value;
        private Entry prev;

        public Entry() {
        }

        public Entry(K key, T value) {
            this.key = key;
            this.value = value;
        }

        public Entry(K key, T value, Entry prev) {
            this.key = key;
            this.value = value;
            this.prev = prev;
        }
    }

    private int size;
    private Entry[] entries;

    public HashTable() {
        this(10);
    }

    public HashTable(int size) {
        this.size = size;
        Entry entry = new Entry();
        this.entries = (Entry[]) Array.newInstance(entry.getClass(), this.size);
    }

    public void put(K key, T value) {
        if (this.entries[key.hashCode() % this.size] == null) {
            Entry newEntry = new Entry(key, value, this.entries[key.hashCode() % this.size]);
            this.entries[key.hashCode() % this.size] = newEntry;
        } else {
            boolean flag = false;
            Entry top = this.entries[key.hashCode() % this.size];
            while (this.entries[key.hashCode() % this.size] != null) {
                if (this.entries[key.hashCode() % this.size].key.equals(key)) {
                    this.entries[key.hashCode() % this.size].value = value;
                    flag = true;
                    break;
                }
                this.entries[key.hashCode() % this.size] = this.entries[key.hashCode() % this.size].prev;
            }
            this.entries[key.hashCode() % this.size] = top;
            if (!flag) {
                Entry newEntry = new Entry(key, value, this.entries[key.hashCode() % this.size]);
                this.entries[key.hashCode() % this.size] = newEntry;
            }
        }
    }

    public void putIfAbsent(K key, T value) {
        if (this.entries[key.hashCode() % this.size] == null) {
            Entry newEntry = new Entry(key, value, this.entries[key.hashCode() % this.size]);
            this.entries[key.hashCode() % this.size] = newEntry;
        } else {
            boolean flag = false;
            Entry top = this.entries[key.hashCode() % this.size];
            while (this.entries[key.hashCode() % this.size] != null) {
                if (this.entries[key.hashCode() % this.size].key.equals(key)) {
                    flag = true;
                    break;
                }
                this.entries[key.hashCode() % this.size] = this.entries[key.hashCode() % this.size].prev;
            }
            this.entries[key.hashCode() % this.size] = top;
            if (!flag) {
                Entry newEntry = new Entry(key, value, this.entries[key.hashCode() % this.size]);
                this.entries[key.hashCode() % this.size] = newEntry;
            }
        }
    }


    public T getOrDefault(K key, T defaultT) {
        if (this.entries[key.hashCode() % this.size] != null) {
            Entry top = this.entries[key.hashCode() % this.size];
            while (this.entries[key.hashCode() % this.size] != null) {
                if (this.entries[key.hashCode() % this.size].key.equals(key)) {
                    this.entries[key.hashCode() % this.size] = top;
                    return this.entries[key.hashCode() % this.size].value;
                }
                this.entries[key.hashCode() % this.size] = this.entries[key.hashCode() % this.size].prev;
            }
            this.entries[key.hashCode() % this.size] = top;
        }
        return defaultT;
    }

    private ArrayList<T> getValues(Entry top) {
        ArrayList<T> getValues = new ArrayList<>();
        while (top != null) {
            getValues.add(top.value);
            top = top.prev;
        }
        return getValues;
    }

    public ArrayList<T> getValues() {
        ArrayList<T> getValues = new ArrayList<>();
        for (int i = 0; i < this.entries.length; i++) {
            Entry entry = this.entries[i];
            ArrayList<T> values = getValues(this.entries[i]);
            this.entries[i] = entry;
            getValues.addAll(values);
        }
        return getValues;
    }

    private HashSet<K> getKeys(Entry top) {
        HashSet<K> getKeys = new HashSet<>();
        while (top != null) {
            getKeys.add(top.key);
            top = top.prev;
        }
        return getKeys;
    }


    public HashSet<K> getKeys() {
        HashSet<K> getKeys = new HashSet<>();
        for (int i = 0; i < this.entries.length; i++) {
            Entry entry = this.entries[i];
            HashSet<K> keys = getKeys(this.entries[i]);
            this.entries[i] = entry;
            getKeys.addAll(keys);
        }
        return getKeys;
    }

    public boolean containsKey(K key) {
        Entry entry = this.entries[key.hashCode() % this.size];
        while (this.entries[key.hashCode() % this.size] != null) {
            if (this.entries[key.hashCode() % this.size].key.equals(key)) {
                return true;
            }
            this.entries[key.hashCode() % this.size] = this.entries[key.hashCode() % this.size].prev;
        }
        this.entries[key.hashCode() % this.size] = entry;
        return false;
    }

    public boolean containsValue(T value) {
        for (int i = 0; i < this.entries.length; i++) {
            Entry entry = this.entries[i];
            while (this.entries[i] != null) {
                if (this.entries[i].value.equals(value)) {
                    return true;
                }
                this.entries[i] = this.entries[i].prev;
            }
            this.entries[i] = entry;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.entries.length; i++) {
            Entry entry = this.entries[i];
            while (this.entries[i] != null) {
                stringBuilder.append(this.entries[i].key).append(" => ").append(this.entries[i].value).append('\n');
                this.entries[i] = this.entries[i].prev;
            }
            this.entries[i] = entry;
        }
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int ind;
            private Entry entry;

            {
                for (int i = 0; i < entries.length; i++) {
                    if (entries[i] != null) {
                        ind = i;
                        break;
                    }
                }
                entry = entries[ind];
            }

            @Override
            public boolean hasNext() {
                return ind < entries.length;
            }

            @Override//!!!
            public T next() {
                T value = entry.value;
                entry = entry.prev;
                if (entry == null) {
                    boolean has = false;
                    for (int i = ind + 1; i < entries.length; i++) {
                        if (entries[i] != null) {
                            ind = i;
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        ind = entries.length + 1;
                        return value;
                    }
                    entry = entries[ind];
                }
                return value;
            }
        };
    }

    public HashTable<String, Integer> groupBy(String input) {
        input = input.toLowerCase(Locale.ROOT);
        String[] string = input.split(" ");
        HashTable<String, Integer> hashTable = new HashTable<>();
        for (int i = 0; i < string.length; i++) {
            Integer orDefault = hashTable.getOrDefault(string[i], 0);
            hashTable.put(string[i], orDefault + 1);
        }
        return hashTable;
    }
}
