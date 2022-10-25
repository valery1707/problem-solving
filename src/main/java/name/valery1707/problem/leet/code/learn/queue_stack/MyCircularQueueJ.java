package name.valery1707.problem.leet.code.learn.queue_stack;

import java.util.function.Function;

/**
 * Design your implementation of the circular queue.
 * <p>
 * The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle,
 * and the last position is connected back to the first position to make a circle.
 * It is also called "Ring Buffer".
 * <p>
 * One of the benefits of the circular queue is that we can make use of the spaces in front of the queue.
 * In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue.
 * But using the circular queue, we can use the space to store new values.
 * <p>
 * You must solve the problem without using the built-in queue data structure in your programming language.
 *
 * @see <a href="https://leetcode.com/explore/learn/card/queue-stack/228/first-in-first-out-data-structure/1337/">Design Circular Queue</a>
 */
public interface MyCircularQueueJ {

    /**
     * Gets the front item from the queue.
     * If the queue is empty, return {@code -1}.
     */
    int Front();

    /**
     * Gets the last item from the queue.
     * If the queue is empty, return {@code -1}.
     */
    int Rear();

    /**
     * Inserts an element into the circular queue.
     * Return {@code true} if the operation is successful.
     */
    boolean enQueue(int value);

    /**
     * Deletes an element from the circular queue.
     * Return {@code true} if the operation is successful.
     */
    boolean deQueue();

    /**
     * Checks whether the circular queue is empty or not.
     */
    boolean isEmpty();

    /**
     * Checks whether the circular queue is full or not.
     */
    boolean isFull();

    enum Implementation implements Function<Integer, MyCircularQueueJ> {
        arrayBased {
            @Override
            public MyCircularQueueJ apply(Integer k) {
                return new ArrayBased(k);
            }
        },
    }

    class ArrayBased implements MyCircularQueueJ {

        private final int[] values;
        private int head = 0;
        private int tail = -1;
        private int count = 0;

        public ArrayBased(int k) {
            this.values = new int[Math.max(0, k)];
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            tail = inc(tail);
            count++;
            values[tail] = value;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            head = inc(head);
            count--;
            return true;
        }

        private int inc(int index) {
            return (index + 1) % values.length;
        }

        public int Front() {
            return isEmpty() ? -1 : values[head];
        }

        public int Rear() {
            return isEmpty() ? -1 : values[tail];
        }

        public boolean isEmpty() {
            return count <= 0;
        }

        public boolean isFull() {
            return count >= values.length;
        }

    }

}
