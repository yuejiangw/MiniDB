import bPlusTree.*;
import DataBaseSystem.*;
import file.*;
import hashTable.*;
import Parser.*;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String command;
        DB dataBase = new DB();

        while (!(command = input.nextLine()).equals("exit")) {
            CommandParser commandParser = new CommandParser();
            commandParser.parseCommand(command);

            // R1 := inputfromfile(sales1.txt)
            if (commandParser.isInputFromFile()) {
                long startTime = System.currentTimeMillis();
                dataBase.inputFromFile(commandParser);
                long endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
            }

            // outputtofile(R1)
            else if (commandParser.isOutputToFile()) {
                long startTime = System.currentTimeMillis();
                dataBase.outputToFile(commandParser);
                long endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
            }

            // R2 := project(R1, saleid, itemid)
            else if (commandParser.isProject()) {
                long startTime = System.currentTimeMillis();
                dataBase.project(commandParser);
                long endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
            }

            // R3 := avg(R1, saleid)
            else if (commandParser.isAvg()) {
                long startTime = System.currentTimeMillis();
                dataBase.avg(commandParser);
                long endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");

            }

        }
    }
}
