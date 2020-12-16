package hashTable;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HashTable {
    /*
     * Attributes
     */
    private HashMap<Info, LinkedHashMap<Integer, Integer>> hashTables;

    /*
     * Constructor(s)
     */
    public HashTable(HashMap<Info, LinkedHashMap<Integer, Integer>> hashTables) {
        this.hashTables = hashTables;
    }

    public HashTable() {
        this.hashTables = new HashMap<>();
    }

    /*
     * Accessors
     */
    public HashMap<Info, LinkedHashMap<Integer, Integer>> getHashTables() {
        return hashTables;
    }

    public void setHashTables(HashMap<Info, LinkedHashMap<Integer, Integer>> hashTables) {
        this.hashTables = hashTables;
    }

}