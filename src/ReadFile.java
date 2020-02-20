import java.util.*;
import java.io.*;

public class ReadFile {
    public static void readFileMethod(String fileIn) throws FileNotFoundException {
        List<String> lines = new LinkedList<>();
        Scanner scanner = new Scanner(new File(fileIn));
        int numOfBooks = Integer.parseInt(scanner.next());
        int numOfLibraries = Integer.parseInt(scanner.next());
        int daysForScanning = Integer.parseInt(scanner.next());
        List<Integer> bookScores = new ArrayList<>();
        for (int i=0; i<numOfBooks; i++) {
            bookScores.add(Integer.parseInt(scanner.next()));
        }
        System.out.println(bookScores);
//        System.out.println(numOfBooks);
//        System.out.println(numOfLibraries);
//        System.out.println(daysForScanning);
//        for (int b : books)
//            System.out.println(b);
        int numOfBooksPerLibrary;
        Map<Integer, List<Integer>> booksPerLibrary = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> signupAndShippingRatePerLibrary = new HashMap<>();
        List<Integer> booksInLibrary;
        for (int i=0; i<numOfLibraries; i++) {
            booksInLibrary = new ArrayList<>();
            numOfBooksPerLibrary = Integer.parseInt(scanner.next());
            Map<Integer, Integer> signUpShippingPair = new HashMap<>();
            signUpShippingPair.put(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            signupAndShippingRatePerLibrary.put(i, signUpShippingPair);
            int tempCounter = 0;
            while (tempCounter < numOfBooksPerLibrary) {
                booksInLibrary.add(Integer.parseInt(scanner.next()));
                tempCounter++;
            }
            booksPerLibrary.put(i, booksInLibrary);
        }
        System.out.println("Books per library: " + booksPerLibrary);
        System.out.println("SignUp and shipping info: " + signupAndShippingRatePerLibrary);

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
        readFileMethod("src/Input/a_example.txt");
//        String[] listIn = new String[5];
//        listIn[0] = ("src/a_example.txt");
//        listIn[1] = ("src/b_small.txt");
//        listIn[2] = ("src/c_medium.txt");
//        listIn[3] = ("src/d_quite_big.txt");
//        listIn[4] = ("src/e_also_big.txt");
//        String[] listOut = new String[5];
//        listOut[0] = ("src/a_solution.out");
//        listOut[1] = ("src/b_solution.out");
//        listOut[2] = ("src/c_solution.out");
//        listOut[3] = ("src/d_solution.out");
//        listOut[4] = ("src/e_solution.out");
//        ReadFile ex = new ReadFile();
//        for (int i = 0; i < 5; i++) {
//            List<Integer> list = ex.ReadFileMethod(listIn[i]);
//            ex.WriteList(list, listOut[i]);
//        }
    }
}