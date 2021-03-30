/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.queuestack;

class DesignCircularQueue {
    private Integer[] array = null;
    private int front;
    private int end;
    private int size;

    public DesignCircularQueue(int k) {
        this.array = new Integer[k];
        this.front = 0;
        this.end = 0;
        this.size = 0;
    }

    public boolean enQueue(int value) {
        if (size == array.length) {
            return false;
        }

        array[end] = value;
        if (end < array.length - 1) {
            end++;
        } else {
            end = 0;
        }
        size++;
        return true;
    }

    public boolean deQueue() {
        if (size == 0) {
            return false;
        }

        array[front] = null;
        if (front != array.length - 1) {
            front++;
        } else {
            front = 0;
        }
        size--;
        return true;
    }

    public int Front() {
        if (size == 0) {
            return -1;
        } else {
            return array[front];
        }
    }

    public int Rear() {
        if (size == 0) {
            return -1;
        } else if (end == 0) {
            return array[array.length - 1];
        } else {
            return array[end - 1];
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such: MyCircularQueue obj = new
 * MyCircularQueue(k); boolean param_1 = obj.enQueue(value); boolean param_2 = obj.deQueue(); int
 * param_3 = obj.Front(); int param_4 = obj.Rear(); boolean param_5 = obj.isEmpty(); boolean param_6
 * = obj.isFull();
 */
