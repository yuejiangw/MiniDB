package test;

import java.util.ArrayList;
import Parser.CommandParser;
import Parser.OperationExpression;

public class ParserTest {

    public static void main(String[] args) {
//        ArrayList<String> commands = new ArrayList<>();
//        String c1 = "R := inputfromfile(sales1.txt)";
//        String c2 = "R := select(S, CONDITION)";
//        String c3 = "R := project(S, Clist)";
//        String c4 = "R := sum(S, C1)";
//        String c5 = "R := avg(S, C1)";
//        commands.add(c1);
//        commands.add(c2);
//        commands.add(c3);
//        commands.add(c4);
//        commands.add(c5);
//        commands.add("Btree(R, C1)");
//        commands.add("outputfile(filename)");
//
//        CommandParser commandParser = new CommandParser();
//
//        for (int i = 0; i < commands.size(); i++) {
//            commandParser.parseCommand(commands.get(i));
//            System.out.println(commandParser.getTableName() + " " +
//                    commandParser.getCommandName());
//            for (int j = 0; j < commandParser.getArguments().size(); j++)
//                System.out.println(commandParser.getArguments().get(j));
//            System.out.println();
//        }

//        String command = "R2 := select(R1, saleid > 500)";
        String command = "R2 := select(R1, saleid > itemid)";
        CommandParser commandParser = new CommandParser();
        commandParser.parseCommand(command);
        OperationExpression condition = commandParser.getCondition();
        System.out.println("Table name: " + commandParser.getTableName());
        System.out.println("Target table: " + commandParser.getArguments().get(0));
        System.out.println("Command name: " + commandParser.getCommandName());
        System.out.println(condition.getOperand1());
        System.out.println(condition.getOperand2());
        System.out.println(condition.getOperator());
//        System.out.println(condition.getOperand2Int());
//        Integer.parseInt("hhhhh");
        ArrayList<Integer> a1 = new ArrayList<>();
        ArrayList<Integer> a2 = new ArrayList<>();
        a1.add(1);
        a2.add(2);
        System.out.println(a1.equals(a2));

        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        System.out.println(getMin(a));
    }
    private static int getMin(ArrayList<Integer> a) {
        double min = Double.POSITIVE_INFINITY;
        for (int data : a)
            if (data < min)
                min = data;
        return (int) min;
    }

}
