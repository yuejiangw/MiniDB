package hashTable;
import java.util.ArrayList;
import java.io.*;

public class HashTable {
    //----------------
    // Attribute(s).
    //----------------
    private final int tableLength = 997;
    private final String fileName = "myIndex.txt";

    private ArrayList<Integer> keys;
    private ArrayList<Integer> values;

    private LinkedList[] arrays;

    //----------------
    // Constructor(s).
    //----------------
    HashTable () {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        arrays = new LinkedList[tableLength];
    }

    HashTable(int tableSize) {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        arrays = new LinkedList[tableSize];
    }

    //-------------------
    // Accessor methods.
    //-------------------
    public int getTableLength() {
        return this.tableLength;
    }

    public String getFileName() {
        return this.fileName;
    }

    public ArrayList<Integer> getValues() {
        return this.values;
    }

    public ArrayList<Integer> getKeys() {
        return this.keys;
    }

    public LinkedList[] getArrays() {
        return this.arrays;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    /**
     * Calculate the hash code of k.
     * @param k represents for the target key.
     */
    public int hashCode(int k) {
        return k % (getArrays().length);
    }
    
    /**
     * Insert data.
     * @param info represents for the key-value pair.
     */
    public void createTable(Info info) {
        int key = info.getKey();
        
        // We use the hash code of key to be the array's index.
        int hashValue = hashCode(key);  

        if (arrays[hashValue] == null) {
            arrays[hashValue] = new LinkedList();
        }
        arrays[hashValue].insert(info);
    }

    public void insert(Info info) {
        int key = info.getKey();

        // We use the hash code of key to be the array's index.
        int hashValue = hashCode(key);

        if (arrays[hashValue] == null) {
            arrays[hashValue] = new LinkedList();
        }
        arrays[hashValue].insert(info);

        // Update the arrays that store data.
        if (getKeys().contains(info.getKey())) {
            int index = getKeys().indexOf(info.getKey());
            getValues().set(index, info.getValue());
        }
        else {
            getKeys().add(info.getKey());
            getValues().add(info.getValue());
        }
    }


    /**
     * Search data.
     * @param key
     * @return the target key-value pair.
     */
    public Info search(int key) {
        int hashValue = hashCode(key);

        // Corresponding linked list.
        LinkedList lk = getArrays()[hashValue];

        // Find the right node in the mentioned linked list.
        LinkedList.Node lkNode = lk.search(key);
        if (lkNode == null) {
            return null;
        }
        return lkNode.info;
    }

    /**
     * Delete a value by its key.
     * @param key can be used to locate value.
     * @return null if key doesn't exist, else the deleted key-value info.
     */
    public Info delete(int key) {
        int hashValue = hashCode(key);

        // Corresponding linked list.
        LinkedList lk = getArrays()[hashValue];

        // Delete the right node in the mentioned linked list.
        LinkedList.Node lkNode = lk.delete(key);

        // If the key does not exist, return null.
        if (lkNode == null)
            return null;
        else {
            // Delete the data in the array accordingly.
            getKeys().remove(lkNode.info.getKey());
            getValues().remove(lkNode.info.getValue());
            return lkNode.info;
        }
    }

    /**
     * Read file.
     * @throws IOException if fail to open files.
     */
    public void readFile() throws IOException {
        File file = new File(getFileName());
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                counter += 1;
                if (counter == 1)
                    continue;
                String[] strSplit = line.split("\\|");
                String key = strSplit[0];
                String value = strSplit[1];
                this.keys.add(Integer.parseInt(key));
                this.values.add(Integer.parseInt(value));

//                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the structure of the Hash Table.
     */
    public void showHashTable() {
        for (int i = 0; i < getArrays().length; i++) {
            getArrays()[i].showLinkedList();
        }
        System.out.println();
    }
}