package Parser;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {
    //----------------
    // Attributes
    //----------------

    private final static String[] allCommands = {"inputfromfile", "outputtofile",
            "select", "project", "sum", "avg", "sumgroup", "avggroup", "join",
            "sort", "movavg", "movsum", "Btree", "Hash", "concat", "showDB"};

    private String tableName;
    private String commandName;
    private ArrayList<String> arguments;


    //----------------
    // Constructor(s)
    //----------------

    public CommandParser () {
        tableName = null;
        commandName = null;
        arguments = new ArrayList<>();
    }


    //----------------
    // Accessors
    //----------------


    public static String[] getAllCommands() {
        return allCommands;
    }

    public void setTableName(String tb) {
        this.tableName = tb;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setCommandName(String cn) {
        this.commandName = cn;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public ArrayList<String> getArguments() {
        return this.arguments;
    }


    //----------------
    // Other Methods.
    //----------------

    public void resetArguments() {
        this.arguments = new ArrayList<>();
    }

    public boolean isValidCommand(String command) {
        return Arrays.asList(getAllCommands()).contains(command);
    }

    public boolean isManageCommand(String command) {
        if (!command.contains(":=") && !command.contains("(") && !command.contains(")"))
            return isValidCommand(command);
        return false;
    }

    public void parseCommand(String str) {

        // Delete all the spaces
        str = str.replaceAll("\\s", "");
        String[] strSplit = str.split(":=");
        String strParenthesis = null;

        // Normal commands with certain table names.
        if (strSplit.length > 1) {
            setTableName(strSplit[0]);
            strParenthesis = strSplit[1];
        }
        // Add index and output file commands, such commands don't have table names.
        else if (strSplit.length == 1) {
            setTableName("");
            strParenthesis = strSplit[0];
        }

        setCommandName(strParenthesis.split("\\(")[0]);

        if (!isValidCommand(getCommandName())) {
            System.out.println("Error! Not a valid command, please recheck.");
            return;
        }

        String strArguments = strParenthesis.substring(
                getCommandName().length() + 1, strParenthesis.length() - 1);
        String[] argumentsSplit = strArguments.split(",");

        resetArguments();

        for (int i = 0; i < argumentsSplit.length; i++) {
            getArguments().add(argumentsSplit[i]);
        }

    }

    public boolean isInputFromFile() {
        return getCommandName().equals("inputfromfile");
    }

    public boolean isSelect() {
        return getCommandName().equals("select");
    }

    public boolean isProject() {
        return getCommandName().equals("project");
    }

    public boolean isSum() {
        return getCommandName().equals("sum");
    }

    public boolean isAvg() {
        return getCommandName().equals("avg");
    }

    public boolean isSumGroup() {
        return getCommandName().equals("sumgroup");
    }

    public boolean isAvgGroup() {
        return getCommandName().equals("avggroup");
    }

    public boolean isJoin() {
        return getCommandName().equals("join");
    }

    public boolean isSort() {
        return getCommandName().equals("sort");
    }

    public boolean isMovAvg() {
        return getCommandName().equals("movavg");
    }

    public boolean isMovSum() {
        return getCommandName().equals("movsum");
    }

    public boolean isBtree() {
        return this.getCommandName().equals("Btree");
    }

    public boolean isHash() {
        return this.getCommandName().equals("Hash");
    }

    public boolean isConcat() {
        return getCommandName().equals("concat");
    }

    public boolean isOutputToFile() {
        return getCommandName().equals("outputtofile");
    }

}
