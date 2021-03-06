import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randomizedQueue.enqueue(str);
        }

        for (int i = 0; i < size; ++i) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
