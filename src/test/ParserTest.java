package test;

import java.util.ArrayList;
import Parser.CommandParser;
import Parser.OperationExpression;

public class ParserTest {

    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        String c1 = "R := inputfromfile(sales1.txt)";
        String c3 = "R := project(S, Clist)";
        String c4 = "R := sum(S, C1)";
        String c5 = "R := avg(S, C1)";
        commands.add(c1);
        commands.add(c3);
        commands.add(c4);
        commands.add(c5);
        commands.add("Btree(R, C1)");
        commands.add("Hash(R, C2)");
        commands.add("outputtofile(filename)");
//        commands.add();

        CommandParser commandParser = new CommandParser();
        String c6 = "R3 := join(R1, R2, R1.saleid > R2.saleid)";
        c6 = c6.replaceAll("\\s", "");
        String strParenthesis = c6.split(":=")[1];
        System.out.println(strParenthesis);
        String commandName = strParenthesis.split("\\(")[0].toLowerCase();
        String strArguments = strParenthesis.substring(commandName.length() + 1, strParenthesis.length() - 1);
//        commandParser.parseCommand(c6);
        System.out.println(strArguments);
        String[] argumentsSplit = strArguments.split(",");
        String targetTable1 = argumentsSplit[0];
        String targetTable2 = argumentsSplit[1];
        String condition = argumentsSplit[2];
        System.out.println(targetTable1);
        System.out.println(targetTable2);
        System.out.println(condition);
        System.out.println(condition.split("\\.").length);

//        for (int i = 0; i < commands.size(); i++) {
//            commandParser.parseCommand(commands.get(i));
//            System.out.println(commandParser.getTableName() + " " +
//                    commandParser.getCommandName());
//            for (int j = 0; j < commandParser.getArguments().size(); j++)
//                System.out.println(commandParser.getArguments().get(j));
//            System.out.println();
//        }

//        String command = "R2 := select(R1, saleid > 500)";
//        String command = "R2 := select(R1, saleid > itemid)";
//        String command2 = "Hash(R2, saleid)";
//        CommandParser commandParser = new CommandParser();
//        commandParser.parseCommand(command);
//        OperationExpression condition = commandParser.getCondition();
//        System.out.println("Table name: " + commandParser.getTableName());
//        System.out.println("Target table: " + commandParser.getArguments().get(0));
//        System.out.println("Command name: " + commandParser.getCommandName());
//        System.out.println(condition.getOperand1());
//        System.out.println(condition.getOperand2());
//        System.out.println(condition.getOperator());
////        System.out.println(condition.getOperand2Int());
////        Integer.parseInt("hhhhh");
//        ArrayList<Integer> a1 = new ArrayList<>();
//        ArrayList<Integer> a2 = new ArrayList<>();
//        a1.add(2);
//        a1.add(1);
//        a2.add(1);
//        a2.add(2);
//        System.out.println(a1.contains(1));
//        System.out.println(a1.equals(a2));

//        ArrayList<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        a.add(4);
//        System.out.println(getMin(a));


    }

    private static int getMin(ArrayList<Integer> a) {
        double min = Double.POSITIVE_INFINITY;
        for (int data : a)
            if (data < min)
                min = data;
        return (int) min;
    }

}
