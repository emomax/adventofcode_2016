package se.maxjonsson.utils;

public class MutableInt {
    int count = 1; // note that we start at 1 since we're counting

    public void increment() {
        ++count;
    }

    public int get() {
        return count;
    }

    @Override
    public String toString() {
        return Integer.toString(count);
    }
}

