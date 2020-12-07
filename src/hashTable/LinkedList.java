package hashTable;

public class LinkedList {
    
    /**
     * The node of the linked list.
     */
     class Node {
        Info info;
        Node next;

        Node(Info i) {
            info = i;
            next = null;
        }

        Node() {
            info = null;
            next = null;
        }
    }
    
    // Attribute
    private Node head;

    // Constructor
    public LinkedList() {
        head = null;
    }

    //------------
    // Accessors.
    //------------

    public Node getHead() {
        return this.head;
    }

    public void setHead(Node hd){
        this.head = hd;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    boolean isEmpty() {
        if (getHead() == null)
            return true;
        return false;
    }

    /**
     * Insert a new node into the linked list.
     * When the target key has already existed, we should
     * update the value of the old node instead of insertion.
     * @param info the key-value pair to be inserted.
     */
    public void insert(Info info) {
        Node newNode = new Node(info);
        if (isEmpty()) {
            head = newNode;
        }   
        else {
            Node current = getHead();
            while (current != null) {
                if (current.info.getKey() == info.getKey()) {
                    current.info.setValue(info.getValue());
                    return;
                }
                current = current.next;
            }
            newNode.next = getHead().next;
            getHead().next = newNode;
        }
    }

    /**
     * Search the node whose key is k in the linked list.
     * @param k represents for the target key.
     * @return null if not found, else return the exact node.
     */
    public Node search(int k) {
        if (isEmpty()) {
            return null;
        }
        Node current = getHead();
        while (current != null) {
            if (current.info.getKey() == k)
                return current;
            current = current.next;
        }
        return null;
    }

    /**
     * Delete the node whose key is k in the linked list.
     * @param k represents for the target key.
     * @return null if not found, else return the exact node.
     */
    public Node delete(int k) {
        Node current = getHead();
        Node currentNext = getHead().next;

        if (isEmpty())
            return null;

        // Delete Head Node, we should update the head as well.
        if (current.info.getKey() == k) {
            setHead(currentNext);
            current.next = null;
            return current;
        }

        // Delete Other Node
        else {
            while(currentNext != null) {
                if (currentNext.info.getKey() == k) {
                    current.next = currentNext.next;
                    currentNext.next = null;
                    return currentNext;
                }
                current = current.next;
                currentNext = current.next;
            }
            return null;
        }
    }

    /**
     * Show the structure of the linked list.
     * In the form of key1|value1 -> key2|value2 -> ... -> key|value
     */
    public void showLinkedList() {
        Node current = getHead();
        while (current != null) {

            System.out.format(
                    "%d|%d",
                    current.info.getKey(),
                    current.info.getValue()
            );

            if (current.next != null)
                System.out.print(" -> ");

            current = current.next;
        }
        System.out.println();
    }
}