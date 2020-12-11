package DataBaseSystem;

import file.FileReader;
import file.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Table {

    //----------------
    // Attributes
    //----------------

    private String tableName;
    private ArrayList<String> columnNames;
    private ArrayList<ArrayList<Integer>> rowData;

    // To make sure that we can get ordered items,
    // we need to use LinkedHashMap instead of HashMap.
    private LinkedHashMap<String, ArrayList<Integer>> columnData;

    //----------------
    // Constructors
    //----------------

    public Table() {
        this.tableName = null;
        this.columnNames = new ArrayList<>();
        this.rowData = new ArrayList<>();
        this.columnData = new LinkedHashMap<>();
    }

    public Table(String tableName) {
        this.tableName = tableName;
        this.columnNames = new ArrayList<>();
        this.rowData = new ArrayList<>();
        this.columnData = new LinkedHashMap<>();
    }

    //----------------
    // Accessors
    //----------------

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<String> getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<ArrayList<Integer>> getRowData() {
        return this.rowData;
    }

    public void setRowData(ArrayList<ArrayList<Integer>> rowData) {
        this.rowData = rowData;
    }

    public LinkedHashMap<String, ArrayList<Integer>> getColumnData() {
        return this.columnData;
    }

    public void setColumnData(LinkedHashMap<String, ArrayList<Integer>> columnData) {
        this.columnData = columnData;
    }

    //----------------
    // Other Methods
    //----------------

    public void clearTable() {
        setTableName(null);
        setColumnNames(new ArrayList<>());
        setRowData(new ArrayList<>());
        setColumnData(new LinkedHashMap<>());
    }

    private void resetRowData() {
        setColumnNames(new ArrayList<>());
        setRowData(new ArrayList<>());
    }


    /**
     * Organize the column ordered data into the row form.
     * Be aware that we need to have column data and column names first.
     * @param fileReader
     */
    public void updateRowData(FileReader fileReader) {
        // To avoid adding duplicate values,
        // we need to clear the column names and the row data first.
        resetRowData();

        // If the current table is created through reading file, then we just
        // need to copy the column names and row data from the file reader.
        if (fileReader != null) {
            setColumnNames((ArrayList<String>) fileReader.getTableHead().clone());
            setRowData((ArrayList<ArrayList<Integer>>) fileReader.getFormalTypeValues().clone());
        }

        // Otherwise, we need to generate row data from the existing columns.
        else {
            // Update column name
            for (String columnName : getColumnData().keySet()) {
                getColumnNames().add(columnName);
            }

            // Update row data.
            // To facilitate data processing, we put all the column
            // data into an ArrayList.
            ArrayList<ArrayList<Integer>> tmpTable = new ArrayList<>();
            for (String columnName : columnNames) {
                tmpTable.add(getColumnData().get(columnName));
            }

            // The total number of rows
            int rowNumber = getColumnData().get(getColumnNames().get(0)).size();
            for (int i = 0; i < rowNumber; i++) {
                getRowData().add(new ArrayList<>());
            }

            for (ArrayList<Integer> column : tmpTable) {
                for (int j = 0; j < column.size(); j++) {
                    getRowData().get(j).add(column.get(j));
                }
            }
        }
    }


    /**
     * Organize the row ordered data into the column form.
     * Be aware that we need to have row data and the column names first.
     */
    public void updateColumnData() {
        // To avoid duplicate insertion, we should initialize column data first.
        setColumnData(new LinkedHashMap<>());

        int rowNumber = getRowData().size();
        int columnNumber = getColumnNames().size();

        for (int i = 0; i < columnNumber; i++) {
            String currentColumnName = getColumnNames().get(i);
            getColumnData().put(currentColumnName, new ArrayList<>());
            for (int j = 0; j < rowNumber; j++) {
                ArrayList<Integer> currentRowData = getRowData().get(j);
                getColumnData().get(currentColumnName).add(currentRowData.get(i));
            }
        }
    }


    /**
     * Import data from current file.
     * The method is overloaded, one is without any parameter and will use
     * the object's fileReader, the other one should be given a specific
     * fileReader.
     */
    public void importFile(FileReader fileReader) {

        // For each column, create a corresponding ArrayList first.
        for (int i = 0; i < fileReader.getTableHead().size(); i++) {
            getColumnData().put(fileReader.getTableHead().get(i), new ArrayList<>());
        }

        // Add data.
        for (int i = 0; i < fileReader.getFormalTypeValues().size(); i++) {
            for (int j = 0; j < fileReader.getTableHead().size(); j++) {

                // The column name.
                String columnName = fileReader.getTableHead().get(j);

                // The data in row order.
                ArrayList<Integer> rowData = fileReader.getFormalTypeValues().get(i);

                // Append each data into the corresponding column.
                getColumnData().get(columnName).add(rowData.get(j));
            }
        }
        // Update column names and row data.
        updateRowData(fileReader);
    }

    /**
     * Output current table to the target file. The method is overloaded,
     * one is with default delimiter "|" and the other one should be given
     * a specific delimiter.
     *
     * @param fileName the path of the target file.
     * @throws IOException if anything goes wrong when writing file.
     */
    public void outputFile(String fileName) throws IOException {
        FileWriter.writeFile(getColumnNames(), getRowData(), fileName);
    }

    public void outputFile(String fileName, String delimiter) throws IOException {
        FileWriter.writeFile(getColumnNames(), getRowData(), fileName, delimiter);
    }

    /**
     * Show current table.
     */
    public void showTable() {
        for (int i = 0; i < getColumnNames().size(); i++) {
            System.out.print(getColumnNames().get(i));
            if (i < getColumnNames().size() - 1)
                System.out.print("|");
        }
        System.out.println();
        for (int i = 0; i < getRowData().size(); i++) {
            for (int j = 0; j < getRowData().get(i).size(); j++) {
                System.out.print(getRowData().get(i).get(j));
                if (j < getRowData().get(i).size() - 1)
                    System.out.print("|");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("There are " + getRowData().size() + " row(s) in the table.");
    }
}
