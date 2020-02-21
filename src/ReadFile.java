import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        //Grade libraries
        //Grade books
        int grade = booksPerLibrary.size() / booksPerDay + totalScore / signUpTime;
        return 0;
    }

}

class Solution{
    List<Integer> listLibraries = new ArrayList<>();
    List<Integer> listAvailableBooks = new ArrayList<>();
    List<List<Integer>> listList = new ArrayList<>();
    String fileOut;

    public Solution(List<Integer> listLibraries, List<Integer> listAvailableBooks, List<List<Integer>> listList, String fileOut) {
        this.listLibraries = listLibraries;
        this.listAvailableBooks = listAvailableBooks;
        this.listList = listList;
        this.fileOut = fileOut;
    }
    public void WriteList() throws IOException {
        FileWriter fileWriter = new FileWriter("src/Output/" + fileOut);
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
}
class Problem{
    int numOfBooks;
    int numOfLibraries;
    int daysForScanning;
    String fileIn;
    List<Integer> bookScores = new ArrayList<>();
    List<Library> libraries = new ArrayList<>();
    Set<Integer> booksScanned = new HashSet<>();
    List<Integer> booksInLibrary = new ArrayList<>();
    Solution solution;

    List<Integer> listLibraries = new ArrayList<>();
    List<Integer> listAvailableBooks = new ArrayList<>();
    List<List<Integer>> listList = new ArrayList<>();
    public Problem(String fileIn) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Input/" + fileIn));
        numOfBooks = Integer.parseInt(scanner.next());
        numOfLibraries = Integer.parseInt(scanner.next());
        daysForScanning = Integer.parseInt(scanner.next());
        bookScores = new ArrayList<>();
        for (int i = 0; i < numOfBooks; i++) {
            bookScores.add(Integer.parseInt(scanner.next()));
        }
        Library.bookScores = bookScores; //static
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

    }

    public void Solve() throws IOException {
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
        for (int i = 0; i < numberOfLibraries; i++) {
            Library library = libraries.get(i);
            int availableBooks = 0;

            List<Integer> books = new ArrayList<>();
            int realisticNumberOfBooks =
                    Math.min(library.numberOfBooks, (daysForScanning - library.startingDay + 1) * library.booksPerDay);

            for (int j = 0; j < library.numberOfBooks; j++) {
                int book = library.booksPerLibrary.get(j);
                if (bookScores.get(book) >= library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
                }
            }
            for (int j = 0; j < library.numberOfBooks; j++) {
                int book = library.booksPerLibrary.get(j);
                if (bookScores.get(book) < library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
                }
            }
            if (availableBooks > 0) {
                listLibraries.add(library.id);
                listAvailableBooks.add(availableBooks);

                List<Integer> temp = new ArrayList<>(books);
                listList.add(temp);
            }
            solution = new Solution(listLibraries, listAvailableBooks, listList, fileIn);
        }

    }
}

public class ReadFile {
    public static void main(String[] args) throws IOException {
        String[] listIn = new String[6];
        listIn[0] = ("a_example.txt");
        listIn[1] = ("b_read_on.txt");
        listIn[2] = ("c_incunabula.txt");
        listIn[3] = ("d_tough_choices.txt");
        listIn[4] = ("e_so_many_books.txt");
        listIn[5] = ("f_libraries_of_the_world.txt");
        for (int i = 0; i < 6; i++) {
            Problem problem = new Problem(listIn[i]);
            problem.Solve();
            problem.solution.WriteList();
        }
    }
}
