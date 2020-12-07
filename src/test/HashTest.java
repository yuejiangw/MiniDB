//package test;
//
//import hashTable.HashTable;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
//public class HashTest {
//
//    public static void main(String[] args) throws IOException {
//
//        long startTime = System.currentTimeMillis();
//
//        HashTable hashTable = new HashTable();
//        hashTable.readFile();
//        ArrayList<Integer> keys = hashTable.getKeys();
//        ArrayList<Integer> values = hashTable.getValues();
//
//        // Make sure that the number of the keys and the values are equal.
//        assert keys.size() == values.size();
//
//        // Create a hash table.
//        for (int i = 0; i < keys.size(); i++) {
//            Info info = new Info(keys.get(i), values.get(i));
//            hashTable.createTable(info);
//        }
//
//        // Show the original hash table.
//        hashTable.showHashTable();
//
//        // Parser for the command file.
//        Parser parser = new Parser();
//        parser.readCommand();
//        ArrayList<String> commands = parser.getCommands();
//        LinkedList[] arrays = null;
//        int key = 0;
//
//        for (int i = 0; i < commands.size(); i++) {
//            String command = commands.get(i);
//
//            // Insert command
//            if (parser.isInsert(command)) {
//                Info info = parser.insertParser(command);
//                hashTable.insert(info);
//                System.out.println("*************************** insert(" +
//                        info.getKey() +"," + info.getValue() + ") ***************************");
//
//                // Show the exact linked list that has been inserted.
//                arrays = hashTable.getArrays();
//                arrays[hashTable.hashCode(info.getKey())].showLinkedList();
//
//                // Show the whole hash table.
//                // hashTable.showHashTable();
//            }
//
//            // Delete command
//            if (parser.isDelete(command)) {
//                key = parser.deleteParser(command);
//                System.out.println("*************************** delete(" +
//                        key + ") ***************************");
//
//                // Show the exact linked list before deletion.
//                System.out.println("Before deletion:");
//                arrays = hashTable.getArrays();
//                arrays[hashTable.hashCode(key)].showLinkedList();
//
//                // Show the deleted info.
//                Info deleteInfo = hashTable.delete(key);
//                if (deleteInfo == null)
//                    System.out.println("Key not found");
//                else {
//                    System.out.print("The node that has been deleted:\t");
//                    System.out.format("%d|%d\n", deleteInfo.getKey(), deleteInfo.getValue());
//                }
//
//                // Show the exact linked list after deletion.
//                System.out.println("After deletion:");
//                arrays = hashTable.getArrays();
//                arrays[hashTable.hashCode(key)].showLinkedList();
//
//                // Show the whole hash table.
//                // hashTable.showHashTable();
//            }
//
//            // Search command
//            if (parser.isSearch(command)) {
//                key = parser.searchParser(command);
//                System.out.println("*************************** search(" +
//                        key + ") ***************************");
//
//                // Show the target key-value in the form of k|v.
//                Info searchInfo = hashTable.search(key);
//                if (searchInfo == null)
//                    System.out.println("Key not found");
//                System.out.format("%d|%d\n", searchInfo.getKey(), searchInfo.getValue());
//
//                // Show the whole hash table.
//                // hashTable.showHashTable();
//            }
//        }
//
//        long endTime = System.currentTimeMillis();
//        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
//    }
//}