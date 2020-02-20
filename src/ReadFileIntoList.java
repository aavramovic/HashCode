import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class ReadFileIntoList {
    public List<Integer> ReadFile(String fileIn) throws FileNotFoundException {
        List<String> lines = new LinkedList<>();
            Scanner scanner = new Scanner(new File(fileIn));
        int total = Integer.parseInt(scanner.next());
        int noOfItems = Integer.parseInt(scanner.next()) - 1;
        int score = 0;
        while (scanner.hasNext()) {
            lines.add(0, scanner.next());
        }
        List<Integer> list = new LinkedList<>();
        int index = 0;
        for (String slice : lines) {
            if (Integer.parseInt(slice) < total) {
                list.add(noOfItems - index);
                total -= Integer.parseInt(slice);
                score += Integer.parseInt(slice);
            }
            index++;
        }
        return list;
    }


    public void WriteList(List<Integer> list, String fileOut) throws IOException {
        FileWriter fileWriter = new FileWriter(fileOut);
        fileWriter.write(list.size() + "\n");
        for (Integer item : list) {
            fileWriter.write(item + " ");
        }
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        String[] listIn = new String[5];
        listIn[0] = ("src/a_example.in");
        listIn[1] = ("src/b_small.in");
        listIn[2] = ("src/c_medium.in");
        listIn[3] = ("src/d_quite_big.in");
        listIn[4] = ("src/e_also_big.in");
        String[] listOut = new String[5];
        listOut[0] = ("src/a_solution.out");
        listOut[1] = ("src/b_solution.out");
        listOut[2] = ("src/c_solution.out");
        listOut[3] = ("src/d_solution.out");
        listOut[4] = ("src/e_solution.out");
        ReadFileIntoList ex = new ReadFileIntoList();
        for (int i = 0; i < 5; i++) {
            List<Integer> list = ex.ReadFile(listIn[i]);
            ex.WriteList(list, listOut[i]);
        }
    }
}
