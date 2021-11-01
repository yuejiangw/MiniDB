import com.nyu.database.index.hash.HashIndex;
import com.nyu.database.parser.CommandParser;
import com.nyu.database.system.DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        String fileName = "commands.txt";
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        Scanner input = new Scanner(System.in);
        String command;
        DataBase dataBase = new DataBase();
        HashIndex hashTable = new HashIndex();
        LinkedHashMap<Integer, Integer> hashIndex = new LinkedHashMap<>();

        try {

            while ((command = br.readLine()) != null) {
                if (command.equals(""))
                    continue;

//            System.out.print(">>> ");
//            while (!(command = input.nextLine()).equals("exit")) {

                // showDB
                if (command.equals("showDB")) {
                    dataBase.getDBMessage();
                }

                CommandParser commandParser = new CommandParser();
                commandParser.parseCommand(command);

                // R1 := inputfromfile(sales1.txt)
                if (commandParser.isInputFromFile()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.inputFromFile(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("InputFromFile Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // outputtofile(R1)
                else if (commandParser.isOutputToFile()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.outputToFile(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("OutputToFile Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R2 := project(R1, saleid, itemid)
                else if (commandParser.isProject()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.project(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Project Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R3 := avg(R1, saleid)
                else if (commandParser.isAvg()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.avgOrSum(commandParser, "avg");
                    long endTime = System.currentTimeMillis();
                    System.out.println("Avg Command Execution time: " +
                            (endTime - startTime) + "ms");

                }

                // R4 := sum(R1, saleid)
                else if (commandParser.isSum()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.avgOrSum(commandParser, "sum");
                    long endTime = System.currentTimeMillis();
                    System.out.println("Sum Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R5 := select(R1, CONDITION)
                else if (commandParser.isSelect()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.select(commandParser, hashTable);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Select Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R6 := movavg(R1, C1, k)
                else if (commandParser.isMovAvg()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.movAvgOrSum(commandParser, "avg");
                    long endTime = System.currentTimeMillis();
                    System.out.println("MovAvg Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R7 := movsum(R1, C1, k)
                else if (commandParser.isMovSum()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.movAvgOrSum(commandParser, "sum");
                    long endTime = System.currentTimeMillis();
                    System.out.println("MovSum Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R8 := concat(R1, R2)
                else if (commandParser.isConcat()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.concat(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Concat Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R9 := sort(R1, C1)
                else if (commandParser.isSort()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.sort(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Sort Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R10 := sumgroup(R1, C1, CList)
                else if (commandParser.isSumGroup()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.sumOrAvgGroup(commandParser, "sum");
                    long endTime = System.currentTimeMillis();
                    System.out.println("SumGroup Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R11 := avggroup(R1, C1, CList)
                else if (commandParser.isAvgGroup()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.sumOrAvgGroup(commandParser, "avg");
                    long endTime = System.currentTimeMillis();
                    System.out.println("AvgGroup Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // R12 := join(R1, R2, R1.C1 > R2.C2)
                else if (commandParser.isJoin()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.join(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Join Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // Hash(R1, C1)
                else if (commandParser.isHash()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.hash(commandParser, hashTable);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Hash Command Execution time: " +
                            (endTime - startTime) + "ms");
                }

                // BTree(R1, C1)
                else if (commandParser.isBtree()) {
                    long startTime = System.currentTimeMillis();
                    dataBase.bTree(commandParser);
                    long endTime = System.currentTimeMillis();
                    System.out.println("BTree Command Execution time: " +
                            (endTime - startTime) + "ms");
                }
                System.out.print(">>> ");
            }
            System.out.println("Bye");
        }
        catch (IOException e) {
            System.out.println("I/O Error Occurs when reading command file.");
        }

        // When exist the program, the log file should be cleared.
//        FileWriter.writeLog("");
    }
}
