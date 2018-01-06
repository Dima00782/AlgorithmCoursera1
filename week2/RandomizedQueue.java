import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int head = 0;
    private int tail = 0;
    private int numberOfElements = 0;
    private Item[] data;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices = new int[numberOfElements];
        private int idx = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < numberOfElements; ++i) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return idx < indices.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[(head + indices[idx++]) % data.length];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] newData = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfElements; ++i) {
            newData[i] = data[(head + i) % data.length];
        }
        data = newData;
        head = 0;
        tail = (head + numberOfElements) % data.length;
    }

    private int getRandomIdx() {
        return (head + StdRandom.uniform(0, numberOfElements)) % data.length;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfElements;
    }

    // add the item
    public void enqueue(Item item) {
        if (numberOfElements == data.length) {
            resize(2 * numberOfElements);
        }
        
        if (item == null) {
            throw new IllegalArgumentException();
        }
        data[tail] = item;
        tail = (tail + 1) % data.length;
        ++numberOfElements;
    }

    // remove and return a random item
    public Item dequeue() {
        if (numberOfElements > 0 && numberOfElements == data.length / 4) {
            resize(data.length / 2);
        }
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIdx = getRandomIdx();
        Item item = data[randomIdx];
        data[randomIdx] = data[head];
        data[head] = null;

        head = (head + 1) % data.length;
        --numberOfElements;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[getRandomIdx()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.dequeue();
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();

        for (int val : rq) {
            System.out.println(val);
        }
    }
}
