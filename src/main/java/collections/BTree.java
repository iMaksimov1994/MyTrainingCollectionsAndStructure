package collections;

public class BTree<T extends Comparable<T>> {
    class Entry {
        private T value;
        private Entry parent;
        private Entry left;
        private Entry right;

        public Entry(T value) {
            this.value = value;
        }
    }

    private Entry root;
    private int size;
    private StringBuilder toString;

    public BTree() {
        this.size = 0;
        this.root = null;
        this.toString = new StringBuilder();
    }

    public void add(T value) {
        Entry x = this.root;
        Entry y = null;
        while (x != null) {
            y = x;
            if (value.compareTo(x.value) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        Entry z = new Entry(value);
        z.parent = y;
        if (root == null) {
            root = z;
        } else {
            if (z.value.compareTo(y.value) < 0) {
                y.left = z;
            } else {
                y.right = z;
            }
        }
        size++;
    }

    private void toString(Entry e) {
        if (e != null) {
            toString(e.left);
            if (this.size - 1 != 0) {
                toString.append(e.value).append(", ");
                this.size--;
            } else {
                toString.append(e.value);
            }
            toString(e.right);
        }
    }

    @Override
    public String toString() {
        int actualSize = this.size;
        toString.append("[");
        toString(this.root);
        toString.append("]");
        this.size = actualSize;
        return toString.toString();
    }

    public T min() {
        if (this.root != null) {
            Entry newRoot = this.root;
            while (newRoot.left != null) {
                newRoot = newRoot.left;
            }
            return newRoot.value;
        } else {
            throw new ArrayIndexOutOfBoundsException("Btree is empty!");
        }
    }

    private Entry minBST(Entry e) {
        Entry minBST = this.root;
        while (minBST.left != null) {
            minBST = minBST.left;
        }
        return minBST;
    }

    public T max() {
        if (this.root != null) {
            Entry newRoot = this.root;
            while (newRoot.right != null) {
                newRoot = newRoot.right;
            }
            return newRoot.value;
        } else {
            throw new ArrayIndexOutOfBoundsException("Btree is empty");
        }
    }

    private Entry maxBST() {
        Entry maxBST = this.root;
        while (maxBST.right != null) {
            maxBST = maxBST.right;
        }
        return maxBST;
    }

    public Entry findEntity(T value) {
        Entry x = this.root;
        while (x != null && !x.value.equals(value)) {
            if (value.compareTo(x.value) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public void transplant(Entry u, Entry v) {
        if (v != null) {
            v.parent = u.parent;
        }
        if (u.parent == null) {
            this.root = v;
        } else {
            if (u == u.parent.left) {
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
        }
    }

    public void delete(T value) {
        Entry findEntity = findEntity(value);
        if (findEntity.left == null) {
            transplant(findEntity, findEntity.right);
        } else if (findEntity.right == null) {
            transplant(findEntity, findEntity.left);
        } else {
            Entry y = minBST(findEntity.right);
            if (y.parent != findEntity) {
                transplant(y, y.right);
                y.right = findEntity.right;
                findEntity.right.parent = y;
            }
            y.left = findEntity.left;
            findEntity.left.parent = y;
            transplant(findEntity, y);
        }
    }

    public Entry successor(Entry x) {
        if (x.right != null) {
            return minBST(x.right);
        }
        while (x != root && x == x.parent.right) {
            x = x.parent;
        }
        return x.parent;
    }
}
