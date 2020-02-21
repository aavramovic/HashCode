import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Book implements Comparable<Book> {
    double grade;
    int id;
    int score;
    int noOfBooks;

    public Book(int id, int score) {
        this.id = id;
        this.score = score;
        this.noOfBooks = 1;
        this.grade = 0;
    }

    public void Grade() {
        grade = score / (noOfBooks * 1.0);
    }

    @Override
    public int compareTo(Book book) {
        return book.score - this.score;
    }

}

class Library implements Comparable<Library> {
    int id;
    List<Book> booksPerLibrary;
    double grade;
    int booksPerDay;
    int signUpTime;
    int numberOfBooks;
    float averageScore;
    float totalScore;
    int startingDay;
    int availableBooks;
    float averageGrade;

    public Library(int id, List<Book> booksPerLibrary, int booksPerDay, int signUpTime) {
        this.numberOfBooks = booksPerLibrary.size();
        this.id = id;
        this.booksPerLibrary = booksPerLibrary;
        this.booksPerDay = booksPerDay;
        this.signUpTime = signUpTime;
        this.availableBooks = 0;
        for (Book book : booksPerLibrary) {
            totalScore += book.score;
        }
        this.averageScore = totalScore / numberOfBooks;
        this.grade = 0;
    }

    public void Grade() {
        averageGrade = 0;
        booksPerLibrary.forEach(book -> averageGrade += book.grade);
        averageGrade /= booksPerLibrary.size();
        this.grade = (booksPerLibrary.size() * 1.0 / booksPerDay) + (totalScore / signUpTime);
    }

    @Override
    public int compareTo(Library library) {
        if (this.grade < library.grade)
            return 1;
        else if (this.grade > library.grade)
            return -1;
        return 0;
    }
}

class Solution {
    List<Library> listLibraries;
    List<List<Book>> listList;
    String fileOut;

    public Solution(List<Library> listLibraries, List<List<Book>> listList, String fileOut) {
        this.listLibraries = listLibraries;
        this.listList = listList;
        this.fileOut = fileOut;
    }

    public void WriteList() throws IOException {
        FileWriter fileWriter = new FileWriter("src/Output/" + fileOut);
        fileWriter.write(listLibraries.size() + "\n");
        for (int i = 0; i < listLibraries.size(); i++) {
            fileWriter.write(listLibraries.get(i).id + " ");
            fileWriter.write(listLibraries.get(i).availableBooks + "");
            fileWriter.write("\n");
            for (int j = 0; j < listList.get(i).size(); j++) {
                fileWriter.write(listList.get(i).get(j).id + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}

class Problem {
    int numOfBooks;
    int numOfLibraries;
    int daysForScanning;
    String fileIn;
    List<Book> books = new ArrayList<>();
    List<Library> libraries = new ArrayList<>();
    Set<Book> booksScanned = new HashSet<>();
    List<Book> booksInLibrary;
    Solution solution;

    List<Library> listLibraries = new ArrayList<>();
    List<List<Book>> listList = new ArrayList<>();

    public Problem(String fileIn) throws FileNotFoundException {
        this.fileIn = fileIn;
        Scanner scanner = new Scanner(new File("src/Input/" + fileIn));
        numOfBooks = Integer.parseInt(scanner.next());
        numOfLibraries = Integer.parseInt(scanner.next());
        daysForScanning = Integer.parseInt(scanner.next());
        for (int i = 0; i < numOfBooks; i++) {
            books.add(new Book(i, Integer.parseInt(scanner.next())));
        }
        for (int i = 0; i < numOfLibraries; i++) {
            booksInLibrary = new ArrayList<>();
            int numberOfBooks = Integer.parseInt(scanner.next());
            int signUpTime = Integer.parseInt(scanner.next());
            int booksPerDay = Integer.parseInt(scanner.next());
            for (int j = 0; j < numberOfBooks; j++) {
                int bookId = Integer.parseInt(scanner.next());
                booksInLibrary.add(books.get(bookId));
                books.get(bookId).noOfBooks++;
            }
            libraries.add(new Library(i, booksInLibrary, booksPerDay, signUpTime));
        }

    }

    public void Solve() {
        libraries.forEach(library -> library.booksPerLibrary.forEach(Book::Grade));
        libraries.forEach(Library::Grade);

        libraries.forEach(library -> library.booksPerLibrary.sort(Book::compareTo));
        libraries.sort(Library::compareTo);
        int numberOfLibraries = 0;
        int daysPassed = 0;
        for (Library library : libraries) {
            if (daysPassed + library.signUpTime < daysForScanning) {
                numberOfLibraries++;
                daysPassed += library.signUpTime;
                library.startingDay = daysPassed;
            }
        }
        for (int i = 0; i < numberOfLibraries; i++) {
            Library library = libraries.get(i);
            int availableBooks = 0;

            List<Book> books = new ArrayList<>();
            int realisticNumberOfBooks =
                    Math.min(library.numberOfBooks, (daysForScanning - library.startingDay) * library.booksPerDay);

            for (int j = 0; j < library.numberOfBooks; j++) {
                Book book = library.booksPerLibrary.get(j);
                if (book.score >= library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
                }
            }
            for (int j = 0; j < library.numberOfBooks; j++) {
                Book book = library.booksPerLibrary.get(j);
                if (book.score < library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
                }
            }
            if (availableBooks > 0) {
                library.availableBooks = availableBooks;
                listLibraries.add(library);

                List<Book> temp = new ArrayList<>(books);
                listList.add(temp);
            }
            solution = new Solution(listLibraries, listList, fileIn);
        }

    }
}

public class ReadFile {
    public static void main(String[] args) throws IOException {
        List<String> listIn = new ArrayList<>();
        listIn.add("a_example.txt");
        listIn.add("b_read_on.txt");
        listIn.add("c_incunabula.txt");
        listIn.add("d_tough_choices.txt");
        listIn.add("e_so_many_books.txt");
        listIn.add("f_libraries_of_the_world.txt");
        for (String s : listIn) {
            Problem problem = new Problem(s);
            problem.Solve();
            problem.solution.WriteList();
        }
    }
}
