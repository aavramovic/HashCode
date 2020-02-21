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
    List<Book> books;
    double grade;
    int booksPerDay;
    float signUpTime;
    int numberOfBooks;
    float averageScore;
    float totalScore;
    int startingDay;
    int availableBooks;
    float averageGrade;

    public Library(int id, List<Book> books, int booksPerDay, int signUpTime) {
        this.numberOfBooks = books.size();
        this.id = id;
        this.books = books;
        this.booksPerDay = booksPerDay;
        this.signUpTime = signUpTime;
        this.availableBooks = 0;
        for (Book book : books) {
            totalScore += book.score;
        }
        this.averageScore = totalScore / numberOfBooks;
        this.grade = 0;
    }

    public void Grade() {
        averageGrade = 0;
        books.forEach(book -> averageGrade += book.grade);
        averageGrade /= books.size();
        double scaledTotalScore = (totalScore / Problem.maxTotalScore.totalScore) * 100;
        double scaledSignUpTime = (signUpTime / Problem.maxSignUpTime.signUpTime) * 100;
        double scaledBooksPerDay = (booksPerDay * 1.0 / Problem.maxBooksPerDay.booksPerDay) * 100;
        double scaledNumberOfBooks = (numberOfBooks * 1.0 / Problem.maxNumOfBooks.numberOfBooks) * 100;
        double scaledAverageScore = (averageScore / Problem.maxAverageScore.averageScore) * 100;
        System.out.println(scaledTotalScore / scaledSignUpTime * 1.5 + " - "
                + scaledBooksPerDay * Math.min(scaledNumberOfBooks * 1.0 / scaledBooksPerDay, Problem.daysForScanning) / 100
                + " - " + scaledAverageScore * 0.01 * 0.2);
        this.grade = (scaledTotalScore / scaledSignUpTime) * 1.5
                + scaledBooksPerDay * Math.min(scaledNumberOfBooks * 1.0 / scaledBooksPerDay, Problem.daysForScanning) / 100
                        + scaledAverageScore * 0.01 * 0.2;
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
    static int daysForScanning;
    String fileIn;
    List<Book> books = new ArrayList<>();
    List<Library> libraries = new ArrayList<>();
    Set<Book> booksScanned = new HashSet<>();
    List<Book> booksInLibrary;
    Solution solution;

    List<Library> listLibraries = new ArrayList<>();
    List<List<Book>> listList = new ArrayList<>();

    static Library maxTotalScore;
    static Library maxNumOfBooks;
    static Library maxBooksPerDay;
    static Library maxSignUpTime;
    static Library maxAverageScore;

    public Problem(String fileIn) throws FileNotFoundException {
        this.fileIn = fileIn;
        Scanner scanner = new Scanner(new File("src/Input/" + fileIn));
        numOfBooks = Integer.parseInt(scanner.next());
        numOfLibraries = Integer.parseInt(scanner.next());
        Problem.daysForScanning = Integer.parseInt(scanner.next());
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

        maxTotalScore = libraries.stream().max(Comparator.comparing(library -> library.totalScore)).orElse(libraries.get(0));
        maxNumOfBooks = libraries.stream().max(Comparator.comparing(library -> library.numberOfBooks)).orElse(libraries.get(0));
        maxBooksPerDay = libraries.stream().max(Comparator.comparing(library -> library.booksPerDay)).orElse(libraries.get(0));
        maxSignUpTime = libraries.stream().max(Comparator.comparing(library -> library.signUpTime)).orElse(libraries.get(0));
        maxAverageScore = libraries.stream().max(Comparator.comparing(library -> library.averageScore)).orElse(libraries.get(0));
    }

    public void Solve() {
        libraries.forEach(library -> library.books.forEach(Book::Grade));
        libraries.forEach(Library::Grade);
        libraries.forEach(library -> library.books.sort(Book::compareTo));
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
                Book book = library.books.get(j);
                if (book.score >= library.averageScore && !booksScanned.contains(book) && availableBooks < realisticNumberOfBooks) {
                    booksScanned.add(book);
                    availableBooks++;
                    books.add(book);
                }
            }
            for (int j = 0; j < library.numberOfBooks; j++) {
                Book book = library.books.get(j);
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
