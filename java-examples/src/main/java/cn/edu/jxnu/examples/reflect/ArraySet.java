package cn.edu.jxnu.examples.reflect;

/** @author Eric Bruneton */
public class ArraySet {

    private int[] values = new int[3];

    private int size;

    public boolean contains(final int v) {
        for (int i = 0; i < size; ++i) {
            if (values[i] == v) {
                return true;
            }
        }
        return false;
    }

    public void add(final int v) {
        if (!contains(v)) {
            if (size == values.length) {
                System.err.println("[enlarge]");
                int[] newValues = new int[values.length + 3];
                System.arraycopy(values, 0, newValues, 0, size);
                values = newValues;
            }
            values[size++] = v;
        }
    }

    public void remove(final int v) {
        int i = 0;
        int j = 0;
        while (i < size) {
            int u = values[i];
            if (u != v) {
                values[j++] = u;
            }
            ++i;
        }
        size = j;
    }

    // test method
    public static void main(final String[] args) {
        ArraySet s = new ArraySet();
        System.err.println("add 1");
        s.add(1);
        System.err.println("add 1");
        s.add(1);
        System.err.println("add 2");
        s.add(2);
        System.err.println("add 4");
        s.add(4);
        System.err.println("add 8");
        s.add(8);
        System.err.println("contains 3 = " + s.contains(3));
        System.err.println("contains 1 = " + s.contains(1));
        System.err.println("remove 1");
        s.remove(1);
        System.err.println("contains 1 = " + s.contains(1));
    }
}
