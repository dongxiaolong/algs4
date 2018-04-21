import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;
    private class Node
    {
        Item item;
        Node preNode;
        Node nextNode;
    }
    public Deque(){
        first = null;
        last = null;
        size = 0;
    }
    public boolean isEmpty(){
        return first == null;
    }
    public int size(){
        return size;
    }
    public void addFirst(Item item){
        validate(item);
        Node newNode = new Node();
        newNode.item = item;
        if (isEmpty()){
            newNode.preNode = null;
            newNode.nextNode = null;
            first = newNode;
            last = newNode;
        }else {
            newNode.preNode = null;
            newNode.nextNode = first;
            first.preNode = newNode;
            first = newNode;
        }
        size ++;
    }
    public void addLast(Item item){
        validate(item);
        Node newNode = new Node();
        newNode.item = item;
        if (isEmpty()){
            newNode.preNode = null;
            newNode.nextNode = null;
            first = newNode;
            last = newNode;
        }else {
            newNode.nextNode = null;
            last.nextNode = newNode;
            newNode.preNode = last;
            last = newNode;
        }
        size ++;
    }
    public Item removeFirst(){
        if (isEmpty())
            throw new NoSuchElementException("the deque is empty");
        Item returnItem = null;
        if (size == 1) {
            returnItem = first.item;
            first = null;
            last = null;
        }else{
            Node oldfirst = first;
            returnItem = oldfirst.item;
            first = oldfirst.nextNode;
            first.preNode = null;
            oldfirst.nextNode = null;
            oldfirst.item = null;
        }
        size --;
        return returnItem;
    }
    public Item removeLast(){
        if (isEmpty())
            throw new NoSuchElementException("the deque is empty");
        Item returnItem = null;
        if (size == 1) {
            returnItem = last.item;
            first = null;
            last = null;
        }else {
            Node oldlast = last;
            returnItem = oldlast.item;
            last = oldlast.preNode;
            last.nextNode = null;
            oldlast.preNode = null;
            oldlast.item = null;
        }
        size--;
        return returnItem;
    }
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext()
        {return current != null;}
        public void remove(){}
        public Item next()
        {
            if (current == null)
                throw new NoSuchElementException("there are no more item");
            Item item = current.item;
            current = current.nextNode;
            return item;
        }
    }
    private void validate(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the item is null");
    }
    public static void main(String[] args){

    }
}
