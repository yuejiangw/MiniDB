package com.nyu.database.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataReader {

    private List<String> tableHead;
    private List<List<String>> values;
    private List<List<Integer>> formalTypeValues;

    public DataReader() {
        tableHead = new ArrayList<>();
        values = new ArrayList<>();
        formalTypeValues = new ArrayList<>();
    }

    public List<String> getTableHead() {
        return tableHead;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public List<List<Integer>> getFormalTypeValues() {
        return formalTypeValues;
    }

    /**
     * Load data files into memory and link the data with corresponding column
     * @param fileName Either "sales1.txt" or "sales2.txt"
     */
    public void readFile(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] strSplit = line.trim().split("\\|");
                counter += 1;
                if (counter == 1) {
                    for (String s : strSplit) {
                        getTableHead().add(s);
                    }
                } else {
                    ArrayList<String> tmp = new ArrayList<>();
                    Collections.addAll(tmp, strSplit);
                    getValues().add(tmp);
                }
            }
        } catch (IOException e1) {
            System.out.println("Error! Something is wrong when reading file! "
                    + "Please recheck!");
            return;
        } catch (IndexOutOfBoundsException e2) {
            System.out.println("Error! There may be extra line feed in the target "
                    + "file, please recheck!");
            return;
        }
        formatValue();
    }

    /**
     * To judge whether a string can be parsed into integer.
     * @param str the target string
     * @return true if the string can be parsed into integer, otherwise false.
     */
    public static boolean canParseInt(String str) {
        if (str != null) {
            return str.matches("\\d+");
        }
        return false;
    }

    /**
     * Change the string values into integer format.
     */
    public void formatValue() {
        List<String> tmp;
        for (int i = 0; i < getValues().size(); i++) {
            List<Integer> rowData = new ArrayList<>();
            tmp = getValues().get(i);
            for (int j = 0; j < tmp.size(); j++) {
                if (canParseInt(tmp.get(j))) {
                    rowData.add(Integer.parseInt(tmp.get(j)));
                } else {
                    System.out.println("The data of " + i + "th row "
                            + j + "th column can't be converted into integer type!");
                }

            }
            getFormalTypeValues().add(rowData);
        }
    }
}
