package test;

import bPlusTree.BplusTree;
import bPlusTree.Info;
import bPlusTree.Parser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TreeTest {

    // Test
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        BplusTree<Integer, Integer> tree = new BplusTree<>(5);
        Parser parser = new Parser();

        // Read files.
        parser.readData();
        parser.readCommand();

        // Create a B+Tree.
        ArrayList<Integer> keys = parser.getKeys();
        ArrayList<Integer> values = parser.getValues();
        assert keys.size() == values.size();

        for (int i = 0; i < keys.size(); i++) {
            tree.insertOrUpdate(keys.get(i), values.get(i));
        }
        // Print the original shape of the tree.
        tree.printTree(tree);

        // Test the commands.
        ArrayList<String> commands = parser.getCommands();
        int key;
        for (int i = 0; i < commands.size(); i++) {
            String command = commands.get(i);

            if (parser.isInsert(command)) {
                Info info = parser.insertParser(command);
                System.out.println("*************************** insert(" +
                        info.getKey() +"," + info.getValue() + ") ***************************");
                tree.insertOrUpdate(info.getKey(), info.getValue());

                // Update the array that stores the data.
                // If the key has already existed, then we just update the value.
                if (parser.getKeys().contains(info.getKey())) {
                    int index = parser.getKeys().indexOf(info.getKey());
                    parser.getValues().set(index, info.getValue());
                }
                // If the key does not exist, then we add the key and the value into the arrays.
                else {
                    parser.getKeys().add(info.getKey());
                    parser.getValues().add(info.getValue());
                }

                // Print the shape of the tree.
                // tree.printTree(tree);

            }

            if (parser.isDelete(command)) {
                key = parser.deleteParser(command);
                System.out.println("*************************** delete(" +
                        key + ") ***************************");
                tree.remove(key);

                // Update the array stores the data.
                if (parser.getKeys().contains(key)) {
                    int index = parser.getKeys().indexOf(key);
                    parser.getKeys().remove(index);
                    parser.getValues().remove(index);
                }

                // Print the shape of the tree.
                // tree.printTree(tree);

            }

            if (parser.isSearch(command)) {
                key = parser.searchParser(command);
                System.out.println("*************************** search(" +
                        key + ") ***************************");
                System.out.println("The value of key " + key + " is: " + tree.get(key));

                // Print the shape of the tree.
                // tree.printTree(tree);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

    }
}
