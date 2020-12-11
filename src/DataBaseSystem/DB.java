package DataBaseSystem;

import Parser.CommandParser;
import Parser.OperationExpression;
import file.FileReader;

import java.io.IOException;
import java.util.*;

public class DB {
    //----------------
    // Attributes
    //----------------

    private LinkedHashMap<String, Table> tables;

    //----------------
    // Constructor(s)
    //----------------
    public DB() {
        tables = new LinkedHashMap<>();
    }


    //----------------
    // Accessors
    //----------------
    public void setTables(LinkedHashMap<String, Table> tables) {
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

    public void getDBMessage() {
        if (getTableNum() == 0) {
            System.out.println("There is no table in the current DB!");
            return;
        }
        System.out.println("Total number of tables:" + getTableNum());
        for (Map.Entry<String, Table> entry : getTables().entrySet()) {
            System.out.print(entry.getKey() + "\t");
        }
        System.out.println();
    }

    /**
     * To get a table from DB by the table's name.
     *
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
     * @param parser used for parsing command.
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
        else
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
     * @param parser used for parsing the command
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
     * Calculate the summation or average value of certain column.
     * @param parser used for parsing the command
     * @param mode either "avg" or "sum", "avg" means get the average value of the
     *             target column, "sum" means get the summation value of the target
     *             column.
     */
    public void avgOrSum(CommandParser parser,
                         String mode) throws NullPointerException {
        try {
            // Create a new table.
            String newName = parser.getTableName();
            Table newTable = new Table(newName);

            // Get the target column name and data.
            String columnName = parser.getArguments().get(1);
            ArrayList<Integer> targetColumn = getTargetColumnData(parser);


            // Calculate the average/sum value of the column.
            int sum = 0;
            for (int columnValue : targetColumn)
                sum += columnValue;
            int avgValue = sum / targetColumn.size();

            // Set column name and the corresponding data.
            // Update the row data as well.
            String newColumnName = mode + "(" + columnName + ")";
            newTable.getColumnNames().add(newColumnName);
            ArrayList<Integer> newColumnData = new ArrayList<>();

            if (mode.equals("avg"))
                newColumnData.add(avgValue);
            else if (mode.equals("sum"))
                newColumnData.add(sum);
            else {
                System.out.println("Error! Mode can only be avg or sum!");
                return;
            }

            newTable.getColumnData().put(newColumnName, newColumnData);
            newTable.updateRowData(null);
            getTables().put(newName, newTable);
        }
        catch (NullPointerException e) {
            System.out.println("Error! The target table or column doesn't exist, " +
                    "please recheck carefully!");
        }
    }

    /**
     *
     * @param parser used for parsing the command
     */
    public void movAvgOrSum(CommandParser parser, String mode)
            throws NullPointerException, NumberFormatException{
        try {
            // Get the target column name and data.
            String columnName = parser.getArguments().get(1);
            ArrayList<Integer> targetColumn = getTargetColumnData(parser);

            // Create a new table, set its column name.
            String newName = parser.getTableName();
            Table newTable = new Table(newName);
            String newColumnName = "mov" + mode + "(" + columnName + ")";
            newTable.getColumnNames().add(newColumnName);

            // Get the step length.
            int k = Integer.parseInt(parser.getArguments().get(2));

            // Calculate the moving average and the moving summation.
            ArrayList<Integer> newColumnData = new ArrayList<>();
            if (mode.equals("avg")) {
                for (int i = 0; i < targetColumn.size(); i++) {
                    int tmp = 0;
                    if (i < k - 1) {
                        for (int j = 0; j <= i; j++) {
                            tmp += targetColumn.get(j);
                        }
                        newColumnData.add(tmp / (i + 1));
                    }
                    else {
                        for (int j = i - k + 1; j <= i; j++) {
                            tmp += targetColumn.get(j);
                        }
                        newColumnData.add(tmp / k);
                    }
                }
            }
            else if (mode.equals("sum")) {
                for (int i = 0; i < targetColumn.size(); i++) {
                    int tmp = 0;
                    if (i < k - 1) {
                        for (int j = 0; j <= i; j++) {
                            tmp += targetColumn.get(j);
                        }
                    }
                    else {
                        for (int j = i - k + 1; j <= i; j++) {
                            tmp += targetColumn.get(j);
                        }
                    }
                    newColumnData.add(tmp);
                }
            }
            newTable.getColumnData().put(newColumnName, newColumnData);
            newTable.updateRowData(null);
            getTables().put(newName, newTable);
        }
        catch (NullPointerException e1) {
            System.out.println("Moving command Error! The target table or column " +
                    "doesn't exist, please recheck carefully!");
        }
        catch (NumberFormatException e2) {
            System.out.println("Moving command Error! The step length of the mov " +
                    "commands must be integer, please recheck carefully!");
        }
    }

    private ArrayList<Integer> getTargetColumnData(CommandParser parser) {
        String targetTableName = parser.getArguments().get(0);
        Table targetTable = getTableByName(targetTableName);
        String targetColumnName = parser.getArguments().get(1);
        assert targetTable != null;
        return targetTable.getColumnData().get(targetColumnName);
    }



    /**
     * Select certain columns from the target table under the restriction of
     * some conditions.
     * @param parser used for parsing the command.
     */
    public void select(CommandParser parser) {
        Table targetTable = getTableByName(parser.getArguments().get(0));

        // Check if the target table exists.
        if (targetTable == null) {
            System.out.println("Error! The target table doesn't exist, please " +
                    "recheck carefully!");
            return;
        }

        // Create a new table and copy all the column names.
        String newName = parser.getTableName();
        Table newTable = new Table(newName);
        newTable.setColumnNames(targetTable.getColumnNames());

        // Get the select conditions.
        OperationExpression condition = parser.getCondition();
        // Operand 1 is an integer
        if (condition.isOperand1Int()) {
            int constant = Integer.parseInt(condition.getOperand1());
            String columnName = condition.getOperand2();
            selectByConstant(targetTable, newTable, columnName,
                    condition.getOperator(), constant);
        }
        // Operand 2 is an integer
        else if (condition.isOperand2Int()) {
            int constant = Integer.parseInt(condition.getOperand2());
            String columnName = condition.getOperand1();
            selectByConstant(targetTable, newTable, columnName,
                    condition.getOperator(), constant);
        }
        // Operand 1 and operand 2 are two column names
        else {
            String column1 = condition.getOperand1();
            String column2 = condition.getOperand2();
            selectByColumn(targetTable, newTable, column1,
                    condition.getOperator(), column2);
        }
        getTables().put(newName, newTable);
    }

    private void selectByConstant(Table targetTable, Table newTable,
                                  String columnName, String operator,
                                  int constant) throws NullPointerException {
        try {
            ArrayList<Integer> columnData = targetTable.getColumnData().get(columnName);
            ArrayList<ArrayList<Integer>> oldRowData = targetTable.getRowData();
            ArrayList<ArrayList<Integer>> newRowData = new ArrayList<>();
            for (int i = 0; i < columnData.size(); i++) {
                switch (operator) {
                    case "<":
                        if (columnData.get(i) < constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case ">":
                        if (columnData.get(i) > constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "=":
                        if (columnData.get(i) == constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "<=":
                        if (columnData.get(i) <= constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case ">=":
                        if (columnData.get(i) >= constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "!=":
                        if (columnData.get(i) != constant) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    default:
                        System.out.println("Error! The operator can only be '>', '<', " +
                                "'=', '>=', '<=', or '!='. Please recheck.");
                        return;
                }
            }
            newTable.setRowData(newRowData);
            newTable.updateColumnData();
        }
        catch (NullPointerException e) {
            System.out.println("Error! The column name doesn't exist! Please recheck.");
        }
    }

    private void selectByColumn(Table targetTable, Table newTable,
                                  String column1, String operator,
                                  String column2) throws NullPointerException {
        try {
            ArrayList<Integer> columnData1 = targetTable.getColumnData().get(column1);
            ArrayList<Integer> columnData2 = targetTable.getColumnData().get(column2);
            ArrayList<ArrayList<Integer>> oldRowData = targetTable.getRowData();
            ArrayList<ArrayList<Integer>> newRowData = new ArrayList<>();

            assert columnData1.size() == columnData2.size();

            for (int i = 0; i < columnData1.size(); i++) {
                switch (operator) {
                    case "<":
                        if (columnData1.get(i) < columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case ">":
                        if (columnData1.get(i) > columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "=":
                        if (columnData1.get(i) == columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "<=":
                        if (columnData1.get(i) <= columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case ">=":
                        if (columnData1.get(i) >= columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    case "!=":
                        if (columnData1.get(i) != columnData2.get(i)) {
                            newRowData.add(oldRowData.get(i));
                        }
                        break;
                    default:
                        System.out.println("Error! The operator can only be '>', '<', " +
                                "'=', '>=', '<=', or '!='. Please recheck.");
                        return;
                }
            }
            newTable.setRowData(newRowData);
            newTable.updateColumnData();
        }
        catch (NullPointerException e) {
            System.out.println("Error! The column name doesn't exist! Please recheck.");
        }
    }



}
