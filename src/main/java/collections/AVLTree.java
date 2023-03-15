package collections;

public class AVLTree<T extends Comparable<T>> {
    class Entry {
        private T value;
        int height;
        private Entry parent, left, right;

        public Entry(T value) {
            this.value = value;
            this.parent = this.left = this.right = null;
            this.height = 1;
        }

        private Entry root;
        private int size;

        public Entry() {
            this.root = null;
            this.size = 0;
        }

        public int getHeight(Entry entry) {
            if (entry == null) {
                return 0;
            }
            return entry.height;
        }

        public void rightRotate(Entry entry) {
            Entry newEntry = entry.left;
            if (entry.parent == null) {
                this.root = newEntry;
            } else if (entry == entry.parent.left) {
                entry.parent.left = newEntry;
            } else {
                entry.parent.right = newEntry.parent;
            }
            newEntry.parent = entry.parent;
            entry.left = newEntry.right;
            if (entry.left != null) {
                entry.left.parent = entry;
            }
            entry.parent = newEntry;
            newEntry.right = entry;
        }

        public void leftRotate(Entry entry) {
            Entry newEntry = entry.right;
            if (entry.parent == null) {
                this.root = newEntry;
            } else if (entry == entry.parent.right) {
                entry.parent.right = newEntry;
            } else {
                entry.parent.left = newEntry;
            }
            newEntry.parent = entry.parent;
            entry.right = newEntry.left;
            if (entry.right != null) {
                entry.right.parent = entry;
            }
            entry.parent = newEntry;
            newEntry.left = entry;
        }

        public int getSize(Entry entry) {
            if (entry == null)
                return 0;
            return 1 + getSize(entry.left) + getSize(entry.right);
        }

    }
}
