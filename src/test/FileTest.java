package test;

import DataBaseSystem.Table;
import file.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FileTest {
    public static void main (String[] args) throws IOException {
//        FileReader fr = new FileReader();
//        fr.readFile(1);
//
//        ArrayList<String> tmp = fr.getValues().get(0);
//        for (int i = 0; i < tmp.size(); i++)
//            System.out.println(tmp.get(i));

//        FormalType ft = new FormalType();
//        for (int i = 0; i < tmp.size(); i++) {
//            if (fr.canParseInt(tmp.get(i)))
//                ft.getNumbers().add(Integer.parseInt(tmp.get(i)));
//            else
//                ft.getStrings().add(tmp.get(i));
//        }
//
//        System.out.println(fr.getValues().size());
//
//        fr.formatValue();
//        System.out.println(fr.getFormalTypeValues().size());
//        showArray(fr.getFormalTypeValues().get(0));
//        FormalType test = fr.getFormalTypeValues().get(0);
//        System.out.println("Number size: "+ test.getNumbers().size());
//        System.out.println("String size:" + test.getStrings().size());

//        HashMap<String, ArrayList<Integer>> table = new HashMap<>();
//        table.put("c1", new ArrayList<>());
//        showArray(table.get("c1"));
//        table.get("c1").add(1);
//        table.get("c1").add(2);
//        table.get("c1").add(3);
//        showArray(table.get("c1"));

        String testFileName = "sales3.txt";
        FileReader fileReader = new FileReader();
        fileReader.readFile("sales1.txt");
        System.out.println("*************** File Reader *****************");
        System.out.println("Value number: " + fileReader.getValues().size());
        System.out.println("Format Value number: " + fileReader.getFormalTypeValues().size());

        Table tbl = new Table();
        tbl.importFile(fileReader);

        System.out.println("*************** Column Name *****************");
        for (String c : tbl.getColumnNames())
            System.out.println(c);
        System.out.println("*************** Value Number *****************");
        String firstColumn = tbl.getColumnNames().get(0);
        System.out.println("First column name: " + firstColumn);

        System.out.println("First column data number: " +
                tbl.getColumnData().get(tbl.getColumnNames().get(0)).size());
        System.out.println("Row number: " + tbl.getRowData().size());
        ArrayList<Integer> firstRow = tbl.getRowData().get(0);
        ArrayList<Integer> secondRow = tbl.getRowData().get(1);
        ArrayList<Integer> lastRow = tbl.getRowData().get(999);

        showArray(firstRow);
        showArray(secondRow);
        showArray(lastRow);

        System.out.println("*************** Column Value *****************");
        ArrayList<Integer> firstColumnData = tbl.getColumnData().get(firstColumn);
        System.out.println("First Column Data number: " + firstColumnData.size());
        showArray(firstColumnData);

        tbl.outputFile(testFileName);

        LinkedHashMap<Integer, Integer> hashMap = new LinkedHashMap<>();
        hashMap.put(2, 1);
        hashMap.put(2, 5);
        hashMap.put(2, 6);
        System.out.println(hashMap.get(2));

        ArrayList<Integer> a1 = new ArrayList<>();
        ArrayList<Integer> a2 = new ArrayList<>();
        ArrayList<Integer> a3 = new ArrayList<>();

        a1.add(1); a1.add(2); a1.add(3);
        a2.add(4); a2.add(5); a2.add(6);

        a3.addAll(a1);
        a3.addAll(a2);

        showArray(a3);
    }

    public static void showArray(ArrayList<Integer> a) {
        if (a.size() == 0) {
            System.out.println("Array is empty");
        }
        else {
            System.out.println("***********************");
            for (int i : a) {
                System.out.println(i);
            }
            System.out.println("Element number: " + a.size());
            System.out.println("***********************");
        }
    }
}
