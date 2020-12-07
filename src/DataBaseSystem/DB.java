package DataBaseSystem;

import Parser.CommandParser;
import bPlusTree.Parser;
import file.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {
    //----------------
    // Attributes
    //----------------

    private HashMap<String, Table> tables;

    //----------------
    // Constructor(s)
    //----------------
    public DB() {
        tables = new HashMap<>();
    }


    //----------------
    // Accessors
    //----------------
    public void setTables(HashMap<String, Table> tables) {
        this.tables = tables;
    }

    public HashMap<String, Table> getTables() {
        return this.tables;
    }

    //----------------
    // Other Methods
    //----------------

    public int getTableNum() {
        return getTables().size();
    }

    /**
     *  To get a table from DB by the table's name.
     * @param name the table's name that we want to get.
     * @return target table if the DB contains the table, otherwise null.
     */
    private Table getTableByName(String name) {
        for (String tableName : getTables().keySet()) {
            if (tableName.equals(name)) {
                return getTables().get(name);
            }
        }
        return null;
    }

    /**
     * Read data from a target file and create a corresponding table if
     * there is no table with the same name. Otherwise, the old table with
     * the same name will be overlapped.
     *
     * @param parser
     * @throws IOException if anything goes wrong when reading files.
     */
    public void inputFromFile(CommandParser parser) throws IOException {
        String tableName = parser.getTableName();
        Table table = new Table(tableName);
        if (getTables().containsKey(tableName)) {
            System.out.println("Warning! There has already existed " +
                    "a table with the same name, the old one will" +
                    "be overlapped.");
            getTables().remove(tableName);
        }

        // Read file.
        String fileName = parser.getArguments().get(0);
        FileReader fileReader = new FileReader();
        fileReader.readFile(fileName);
        table.importFile(fileReader);

        // Add table to the DB.
        getTables().put(tableName, table);
    }

    public void outputToFile(String tableName, String fileName) throws IOException {
        Table table = getTableByName(tableName);
        if (table == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }
        table.outputFile(fileName);
    }

    public void outputToFile(String tableName,
                             String fileName, String delimiter) throws IOException {
        Table table = getTableByName(tableName);
        if (table == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }
        table.outputFile(fileName, delimiter);
    }

    public void outputToFile(CommandParser parser) {
        String tableName = parser.getArguments().get(0);
        Table table = getTableByName(tableName);
        if (table == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }
        table.showTable();
    }

    /**
     * Get certain column from the target table by column's name, and update
     * the corresponding message in the new table.
     *
     * @param targetTable the target table
     * @param newTable the new table
     * @param columnName the column's name
     */
    private void updateColumns(Table targetTable, Table newTable, String columnName) {
        if (!targetTable.getColumnNames().contains(columnName)) {
            System.out.println("Error! There is something wrong with the column " +
                    "name, please recheck carefully!");
            return;
        }
        // Update column names.
        newTable.getColumnNames().add(columnName);
        // Update corresponding column data.
        ArrayList<Integer> columnData = targetTable.getColumnData().get(columnName);
        newTable.getColumnData().put(columnName, columnData);
    }

    /**
     * Do projection operation on certain columns of the target table.
     *
     * @param parser
     */
    public void project(CommandParser parser) {
        Table targetTable = getTableByName(parser.getArguments().get(0));

        // Check if the target exists.
        if (targetTable == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }

        String newName = parser.getTableName();
        Table newTable = new Table(newName);

        for (int i = 1; i < parser.getArguments().size(); i++) {
            String columnName = parser.getArguments().get(i);
            updateColumns(targetTable, newTable, columnName);
        }
        // Update row data.
        newTable.updateRowData(null);

        // Add the new table to current DB.
        getTables().put(newName, newTable);
    }

    /**
     *
     * @param parser
     */
    public void avg(CommandParser parser) {
        Table targetTable = getTableByName(parser.getArguments().get(0));

        // Check if the target exists.
        if (targetTable == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }

        // Create a new table.
        String newName = parser.getTableName();
        Table newTable = new Table(newName);

        // Get the target column data.
        String columnName = parser.getArguments().get(1);
        ArrayList<Integer> targetColumn = targetTable.getColumnData().get(columnName);

        // Calculate the average value of the column.
        int sum = 0;
        for (int columnValue : targetColumn) {
            sum += columnValue;
        }
        int avgValue = sum / targetColumn.size();

        // Set column name and the average value.
        // Update the row data as well.
        newTable.getColumnNames().add("avg(" + columnName + ")");
        newTable.getColumnData().get(columnName).add(avgValue);
        newTable.getRowData().get(0).add(avgValue);
    }

    public void sum(CommandParser parser) {

    }
}
