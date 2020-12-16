package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriter {

    /**
     * Write the name of each column of the table into a file.
     * @param tableHead an ArrayList represents for the names of all columns
     * @param bw the target BufferedWriter
     * @param delimiter the character to seperate two column names
     * @throws IOException if anything goes wrong when writing file
     */
    private static void writeHead(ArrayList<String> tableHead,
                                  BufferedWriter bw, String delimiter) throws IOException {
        for (int i = 0; i < tableHead.size(); i++) {
            bw.write(tableHead.get(i));
            if (i < tableHead.size() - 1)
                bw.write(delimiter);
        }
        bw.write("\n");
    }

    /**
     * Write the table's data (int type) into a file.
     * @param data an ArrayList that stores all the data
     * @param bw the BufferedWriter of the target file
     * @param delimiter the delimiter that separates two columns
     * @throws IOException if anything is wrong when reading files.
     */
    private static void writeData(ArrayList<ArrayList<Integer>> data,
                                  BufferedWriter bw,
                                  String delimiter) throws IOException {
        for (ArrayList<Integer> datum : data) {
            for (int j = 0; j < datum.size(); j++) {
                bw.write(Integer.toString(datum.get(j)));
                if (j < datum.size() - 1)
                    bw.write(delimiter);
            }
            bw.write("\n");
        }
    }

    public static void writeFile(ArrayList<String> tableHead,
                                 ArrayList<ArrayList<Integer>> data,
                                 String fileName) throws IOException {
        String delimiter = "|";
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName))) {
            writeHead(tableHead, bw, delimiter);
            writeData(data, bw, delimiter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(ArrayList<String> tableHead,
                                 ArrayList<ArrayList<Integer>> data,
                                 String fileName,
                                 String delimiter) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName))) {
            writeHead(tableHead, bw, delimiter);
            writeData(data, bw, delimiter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write
     * @param str
     * @throws IOException
     */
    public static void writeLog(String str) throws IOException {
        String logFileName = "log.txt";
        File file = new File(logFileName);
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(file, true))) {
            bw.write(str+"\n");
        }
        catch (IOException e) {
            System.out.println("Something is wrong when writing log file.");
        }
    }
}
