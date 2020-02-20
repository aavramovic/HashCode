import java.util.*;
import java.io.*;

class Library {
    int id;
    List<Integer> booksPerLibrary = new ArrayList<>();
    float grade;
    int booksPerDay;
    int signUpTime;
    int numberOfBooks;
    int averageScore;
    int totalScore;
    int startingDay;
    static List<Integer> bookScores = new ArrayList<>();

    public Library(int id, List<Integer> booksPerLibrary, int booksPerDay, int signUpTime) {
        this.numberOfBooks = booksPerLibrary.size();
        this.id = id;
        this.booksPerLibrary = booksPerLibrary;
        this.booksPerDay = booksPerDay;
        this.signUpTime = signUpTime;
        for (Integer book : booksPerLibrary) {
            totalScore += bookScores.get(book);
        }
        this.averageScore = totalScore / numberOfBooks;
        this.grade = Grade();

    }

    public Integer Grade() {
        int grade = booksPerLibrary.size() / booksPerDay + totalScore / signUpTime;
        return 0;
    }

}


public class ReadFile {
    public static void readFileMethod(String fileIn) throws IOException {
        List<String> lines = new LinkedList<>();
        Scanner scanner = new Scanner(new File("src/Input/" + fileIn));
        int numOfBooks = Integer.parseInt(scanner.next());
        int numOfLibraries = Integer.parseInt(scanner.next());
        int daysForScanning = Integer.parseInt(scanner.next());
        List<Integer> bookScores = new ArrayList<>();
        for (int i = 0; i < numOfBooks; i++) {
            bookScores.add(Integer.parseInt(scanner.next()));
        }
        Library.bookScores = bookScores;
        List<Library> libraries = new ArrayList<>();
        HashSet<Integer> booksScanned = new HashSet<>();

        List<Integer> booksInLibrary;
        for (int i = 0; i < numOfLibraries; i++) {
            booksInLibrary = new ArrayList<>();
            int numberOfBooks = Integer.parseInt(scanner.next());
            int signUpTime = Integer.parseInt(scanner.next());
            int booksPerDay = Integer.parseInt(scanner.next());
            for (int j = 0; j < numberOfBooks; j++) {
                int bookId = Integer.parseInt(scanner.next());
                booksInLibrary.add(bookId);
            }
            libraries.add(new Library(i, booksInLibrary, booksPerDay, signUpTime));
        }
        libraries.sort(Comparator.comparing(Library::Grade));
        int numberOfLibraries = 0;
        int daysPassed = 0;
        for (Library library : libraries) {
            if (daysPassed < daysForScanning) {
                numberOfLibraries++;
                daysPassed += library.signUpTime;
                library.startingDay = daysPassed;
            }
        }
        if (daysPassed > daysForScanning)
            numberOfLibraries--;

        FileWriter fileWriter = new FileWriter("src/Output/" + fileIn);

//        System.out.println(numberOfLibraries);
        List<Integer> listLibraries = new ArrayList<>();
        List<Integer> listAvailableBooks = new ArrayList<>();
        List<List<Integer>> listList = new ArrayList<>();
        for (int i = 0; i < numberOfLibraries; i++) {
            Library library = libraries.get(i);
            int availableBooks = 0;

            List<Integer> books = new ArrayList<>();
//            System.out.println(library.id);
            int realisticNumberOfBooks =
                    Math.min(library.numberOfBooks, (daysForScanning - library.startingDay + 1) * library.booksPerDay);

//            System.out.println(realisticNumberOfBooks);
            for (int j = 0; j < library.numberOfBooks; j++) {
                int book = library.booksPerLibrary.get(j);
                if (bookScores.get(book) >= library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
//                    fileWriter.write(book + " ");
//                    System.out.println(book);
                }
            }
            for (int j = 0; j < library.numberOfBooks; j++) {
                int book = library.booksPerLibrary.get(j);
                if (bookScores.get(book) < library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
//                    fileWriter.write(book + " ");
//                    System.out.println(book);
                }
            }
            if (availableBooks > 0) {
                listLibraries.add(library.id);
                listAvailableBooks.add(availableBooks);
                List<Integer> temp = new ArrayList<>();

                for (int book : books) {
                    temp.add(book);
                }
                listList.add(temp);
            }


        }
        fileWriter.write(listLibraries.size() + "\n");
        for (int i = 0; i < listLibraries.size(); i++) {
            fileWriter.write(listLibraries.get(i) + " ");
            fileWriter.write(listAvailableBooks.get(i) + "");
            fileWriter.write("\n");
            for (int j = 0; j < listList.get(i).size(); j++) {
                fileWriter.write(listList.get(i).get(j) + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

//    public void WriteList(List<Integer> list, String fileOut) throws IOException {
//        FileWriter fileWriter = new FileWriter(fileOut);
//        fileWriter.write(list.size() + "\n");
//        for (Integer item : list) {
//            fileWriter.write(item + " ");
//        }
//        fileWriter.close();
//    }

    public static void main(String[] args) throws IOException {
        String[] listIn = new String[6];
        listIn[0] = ("a_example.txt");
        listIn[1] = ("b_read_on.txt");
        listIn[2] = ("c_incunabula.txt");
        listIn[3] = ("d_tough_choices.txt");
        listIn[4] = ("e_so_many_books.txt");
        listIn[5] = ("f_libraries_of_the_world.txt");

        //        String[] listOut = new String[6];
//        listOut[0] = ("src/a_solution.out");
//        listOut[1] = ("src/b_solution.out");
//        listOut[2] = ("src/c_solution.out");
//        listOut[3] = ("src/d_solution.out");
//        listOut[4] = ("src/e_solution.out");
//        ReadFile ex = new ReadFile();
        for (int i = 0; i < 6; i++) {
            readFileMethod(listIn[i]);
        }
    }
}
