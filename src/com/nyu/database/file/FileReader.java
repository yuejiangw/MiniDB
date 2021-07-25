package com.nyu.database.file;

import java.io.*;
import java.util.ArrayList;

public class FileReader {
    //----------------
    // Attributes
    //----------------
    private ArrayList<String> tableHead;
    private ArrayList<ArrayList<String>> values;
    private ArrayList<ArrayList<Integer>> formalTypeValues;


    //----------------
    // Constructor(s)
    //----------------
    public FileReader() {
        tableHead = new ArrayList<>();
        values = new ArrayList<>();
        formalTypeValues = new ArrayList<>();
    }


    //----------------
    //   Accessors
    //----------------
    public ArrayList<String> getTableHead() {
        return tableHead;
    }

    public ArrayList<ArrayList<String>> getValues() {
        return values;
    }

    public ArrayList<ArrayList<Integer>> getFormalTypeValues() {
        return formalTypeValues;
    }


    //----------------
    // Other methods
    //----------------

    private void reset() {
        tableHead = new ArrayList<>();
        values = new ArrayList<>();
        formalTypeValues = new ArrayList<>();
    }

    /**
     * Load data files into memory and link the data with corresponding column
     * @param fileName Either "sales1.txt" or "sales2.txt"
     * @throws IOException if anything is wrong when reading files.
     */
    public void readFile(String fileName) throws IOException, IndexOutOfBoundsException {
        File file = new File(fileName);

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            String line = null;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] strSplit = line.trim().split("\\|");
                counter += 1;
                if (counter == 1) {
                    for (int i = 0; i < strSplit.length; i++)
                        getTableHead().add(strSplit[i]);
                }
                else {
                    ArrayList<String> tmp = new ArrayList<>();
                    for (int i = 0; i < strSplit.length; i++)
                        tmp.add(strSplit[i]);
                    getValues().add(tmp);
                }
            }
        } catch (IOException e1) {
            System.out.println("Error! Something is wrong when reading file! " +
                    "Please recheck!");
            e1.printStackTrace();
        } catch (IndexOutOfBoundsException e2) {
            System.out.println("Error! There may be extra line feed in the target " +
                    "file, please recheck!");
            e2.printStackTrace();
        }
        formatValue();
    }


    /**
     * To judge whether a string can be parsed into integer.
     * @param str the target string
     * @return true if the string can be parsed into integer, otherwise false.
     */
    public static boolean canParseInt(String str) {
        if (str == null)
            return false;
        return str.matches("\\d+");
    }

    /**
     * Change the string values into integer format.
     */
    public void formatValue() {
        ArrayList<ArrayList<String>> values = getValues();
        ArrayList<String> tmp = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            ArrayList<Integer> rowData = new ArrayList<>();
            tmp = values.get(i);
            for (int j = 0; j < tmp.size(); j++) {
                if (canParseInt(tmp.get(j)))
                    rowData.add(Integer.parseInt(tmp.get(j)));
                else
                    System.out.println("The data of " + i + "th row " +
                            j + "th column can't be converted into integer type!");
            }
            getFormalTypeValues().add(rowData);
        }
    }
}
