package com.nyu.database.index.hash;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HashIndex {
    /*
     * Attributes
     */
    private HashMap<Info, LinkedHashMap<Integer, Integer>> hashTables;

    /*
     * Constructor(s)
     */
    public HashIndex(HashMap<Info, LinkedHashMap<Integer, Integer>> hashTables) {
        this.hashTables = hashTables;
    }

    public HashIndex() {
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