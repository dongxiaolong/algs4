import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rgArrays;
    private int size;

    public Iterator<Item> iterator(){
        return new RandomIterator();
    }
    private class RandomIterator implements Iterator<Item> {
        private int rank;
        private Item[] iterArrays;

        public RandomIterator(){
            rank = size;
            iterArrays = (Item[]) new Object[rank];
            for(int i = 0; i < size; i++){
                iterArrays[i] = rgArrays[i];
            }
        }
        public boolean hasNext(){
            return (rank > 0);
        }
        public Item next(){
            if (rank == 0)
                throw new NoSuchElementException("there are no more items");
            int r = StdRandom.uniform(0, rank);
            rank --;
            Item item = iterArrays[r];
            iterArrays[r] = iterArrays[rank];
            iterArrays[rank] = item;
            return item;
        }
    }
    public RandomizedQueue(){
        rgArrays = (Item[]) new Object[1];
        size = 0;
    }
    private void validate(Item item){
        if (item == null)
            throw new IllegalArgumentException("the item is null");
    }
    public boolean isEmpty() {
        return (size == 0);
    }
    public int size(){
        return size;
    }
    private void resize(int cap) {
        Item[] temp = (Item[]) new Object[cap];
        for (int i = 0; i < size; i++)
            temp[i] = rgArrays[i];
        rgArrays = temp;
    }
    public void enqueue(Item item) {
        validate(item);
        rgArrays[size++] = item;
        if (size == rgArrays.length)
            resize(2 * rgArrays.length);
    }
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException("the RandomizeQueue is empty!");
        int r = StdRandom.uniform(0, size);
        size --;
        Item delItem = rgArrays[r];
        rgArrays[r] = rgArrays[size];
        rgArrays[size] = null;
        if (size> 0 && size == rgArrays.length / 4)
            resize(rgArrays.length / 2 );
        return delItem;
    }
    public Item Sample(){
        if (size == 0)
            throw new NoSuchElementException("the RandomizeQueue is empty");
        return rgArrays[StdRandom.uniform(0, size)];
    }
}
