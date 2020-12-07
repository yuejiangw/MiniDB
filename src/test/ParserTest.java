package test;

import java.util.ArrayList;
import Parser.CommandParser;

public class ParserTest {

    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        String c1 = "R := inputfromfile(sales1.txt)";
        String c2 = "R := select(S, CONDITION)";
        String c3 = "R := project(S, Clist)";
        String c4 = "R := sum(S, C1)";
        String c5 = "R := avg(S, C1)";
        commands.add(c1);
        commands.add(c2);
        commands.add(c3);
        commands.add(c4);
        commands.add(c5);
        commands.add("Btree(R, C1)");
        commands.add("outputfile(filename)");

        CommandParser commandParser = new CommandParser();

        for (int i = 0; i < commands.size(); i++) {
            commandParser.parseCommand(commands.get(i));
            System.out.println(commandParser.getTableName() + " " +
                    commandParser.getCommandName());
            for (int j = 0; j < commandParser.getArguments().size(); j++)
                System.out.println(commandParser.getArguments().get(j));
            System.out.println();

        }
    }

}
