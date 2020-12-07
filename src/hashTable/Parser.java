package hashTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    //----------------
    // Attribute(s).
    //----------------

    private ArrayList<String> commands;
    private final String fileName = "myCommand.txt";

    //----------------
    // Constructor(s).
    //----------------

    Parser() {
        commands = new ArrayList<>();
    }

    //----------------
    // Accessor(s).
    //----------------

    ArrayList<String> getCommands() {
        return this.commands;
    }

    String getFileName() {
        return this.fileName;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------

    /**
     * Read command file and store the Strings into ArrayList.
     * @return an ArrayList which stores the commands
     * @throws IOException if anything goes wrong with reading file.
     */
    public void readCommand() throws IOException {
        File file = new File(getFileName());
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                getCommands().add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInsert(String command) {
        return command.substring(0, 6).equals("insert");
    }

    public boolean isDelete(String command) {
        return command.substring(0, 6).equals("delete");
    }

    public boolean isSearch(String command) {
        return command.substring(0, 6).equals("search");
    }

    public Info insertParser(String command) {
        // insert(aaa,bbb)
        // First we extract "aaa,bbb", then we split the substring by ","
        String substr = command.substring(7, command.length()-1);
        Info info = new Info(
                Integer.parseInt(substr.split(",")[0]),
                Integer.parseInt(substr.split(",")[1])
        );
        return info;
    }

    public int deleteParser(String command) {
        // delete(aaa)
        String substr = command.substring(7, command.length()-1);
        return Integer.parseInt(substr);
    }

    public int searchParser(String command) {
        // search(aaa)
        String substr = command.substring(7, command.length()-1);
        return Integer.parseInt(substr);
    }
}