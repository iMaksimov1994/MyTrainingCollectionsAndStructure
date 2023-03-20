package collections;

public class AVLTree<T extends Comparable<T>> {
    class Entry {
        private final T key;
        private Entry parent, left, right;
        private int height;

        public Entry(T value) {
            key = value;
            parent = right = left = null;
            height = 1;
        }

        public void setHeight() {
            this.height = 1 + Math.max(getHeight(this.left), getHeight(this.right));
        }
    }

    private Entry root;

    public AVLTree() {
        root = null;
    }

    private int getHeight(Entry x) {
        if (x == null)
            return 0;
        return x.height;
    }

    private void rightRotate(Entry x) {
        Entry y = x.left;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.parent = x.parent;
        x.left = y.right;
        if (x.left != null)
            x.left.parent = x;
        x.parent = y;
        y.right = x;
        x.setHeight();
        y.setHeight();
    }

    private void LeftRotate(Entry x) {
        Entry y = x.right;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.right)
            x.parent.right = y;
        else
            x.parent.left = y;
        y.parent = x.parent;
        x.right = y.left;
        if (x.right != null)
            x.right.parent = x;
        x.parent = y;
        y.left = x;
        x.setHeight();
        y.setHeight();
    }

    private int getSize(Entry x) {
        if (x == null)
            return 0;
        return 1 + getSize(x.left) + getSize(x.right);
    }

    public void insertAVL(T value) {
        Entry x = root;
        Entry y = null;
        while (x != null) {
            y = x;
            if (value.compareTo(x.key) < 0)
                x = x.left;
            else
                x = x.right;
        }
        Entry z = new Entry(value);
        z.parent = y;
        if (root == null)
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;
        while (y != null) {
            correctBalance(y);
            y = y.parent;
        }
    }

    private void correctBalance(Entry y) {
        y.setHeight();
        int balance = getHeight(y.left) - getHeight(y.right);
        if (balance == 2) {
            if (y.left.left == null)
                LeftRotate(y.left);
            rightRotate(y);
        } else if (balance == -2) {
            if (y.right.right == null)
                rightRotate(y.right);
            LeftRotate(y);
        }
    }

    public void printAVL(Entry x) {
        if (x != null) {
            printAVL(x.left);
            System.out.print(x.key + " ");
            System.out.print(getHeight(x) + "\t");
            printAVL(x.right);
        }
    }

    private void Transplant(Entry u, Entry v) {
        if (v != null)
            v.parent = u.parent;
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
    }

    private Entry minAVL(Entry z) {
        if (z == null)
            return null;
        while (z.left != null)
            z = z.left;
        return z;
    }

    private Entry searchAVL(T value) {
        Entry x = root;
        while (x != null && x.key != value) {
            if (value.compareTo(x.key) < 0)
                x = x.left;
            else
                x = x.right;
        }
        return x;
    }

    public void deleteAVL(T value) {
        Entry z = searchAVL(value);
        if (z == null) return;
        Entry repair = z.parent;
        if (z.left == null)
            Transplant(z, z.right);
        else if (z.right == null)
            Transplant(z, z.left);
        else {
            Entry y = minAVL(z.right);
            if (y.parent == z)
                repair = y;
            else {
                repair = y.parent;
                Transplant(y, y.right);
                y.right = z.right;
                z.right.parent = y;
            }
            y.left = z.left;
            z.left.parent = y;
            Transplant(z, y);
        }
        while (repair != null) {
            correctBalance(repair);
            repair = repair.parent;
        }
    }
}
