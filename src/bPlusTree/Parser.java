package bPlusTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {
    //----------------
    // Attribute(s).
    //----------------

    private ArrayList<String> commands;
    private final String fileName = "myCommand.txt";

    private final String dataFile = "myIndex.txt";
    private ArrayList<Integer> keys;
    private ArrayList<Integer> values;

    //----------------
    // Constructor(s).
    //----------------

    public Parser() {
        commands = new ArrayList<>();
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    //----------------
    // Accessor(s).
    //----------------

    public ArrayList<String> getCommands() {
        return this.commands;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDataFile() {
        return this.dataFile;
    }

    public ArrayList<Integer> getKeys() {
        return this.keys;
    }

    public ArrayList<Integer> getValues() {
        return this.values;
    }

    //-----------------------------
    // Miscellaneous other methods.
    //-----------------------------


    /**
     * Read key-value data file into two ArrayLists.
     * @throws IOException if anything goes wrong when reading file.
     */
    public void readData() throws IOException {
        File file = new File(getDataFile());
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                counter += 1;
                if (counter == 1)
                    continue;
                String[] strSplit = line.split("\\|");
                String key = strSplit[0];
                String value = strSplit[1];
                this.keys.add(Integer.parseInt(key));
                this.values.add(Integer.parseInt(value));

//                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read command file and store the Strings into ArrayList.
     * @return an ArrayList which stores the commands
     * @throws IOException when something is wrong with reading file.
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