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
    public void Report(){
        System.out.println("ID: " + id);
        System.out.println("Grade: " + grade);
        System.out.println("Books per day: " + booksPerDay);
        System.out.println("SignUp time: " + signUpTime);

    }
}


public class ReadFile {
    public static void readFileMethod(String fileIn) throws FileNotFoundException {
        List<String> lines = new LinkedList<>();
        Scanner scanner = new Scanner(new File(fileIn));
        int numOfBooks = Integer.parseInt(scanner.next());
        int numOfLibraries = Integer.parseInt(scanner.next());
        int daysForScanning = Integer.parseInt(scanner.next());
        List<Integer> bookScores = new ArrayList<>();
        for (int i = 0; i < numOfBooks; i++) {
            bookScores.add(Integer.parseInt(scanner.next()));
        }
        Library.bookScores = bookScores;
        List<Library> libraries = new ArrayList<>();

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
        int daysForScanningClone = daysForScanning;
        for (Library library : libraries) {
            if (library.signUpTime < daysForScanningClone) {
                numberOfLibraries++;
                daysForScanningClone -= library.signUpTime;
            }
        }
        System.out.println(numberOfLibraries);
        for(int i=0; i<numberOfLibraries; i++){
            Library library = libraries.get(i);
            System.out.println(library.id);

            System.out.println(library.numberOfBooks);
        }
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
        String[] listIn = new String[6];
        listIn[0] = ("src/Input/a_example.txt");
        listIn[1] = ("src/Input/b_read_on.txt");
        listIn[2] = ("src/Input/c_incunabula.txt");
        listIn[3] = ("src/Input/d_tough_choices.txt");
        listIn[4] = ("src/Input/e_so_many_books.txt");
        listIn[5] = ("src/Input/f_libraries_of_the_world.txt");

        //        String[] listOut = new String[6];
//        listOut[0] = ("src/a_solution.out");
//        listOut[1] = ("src/b_solution.out");
//        listOut[2] = ("src/c_solution.out");
//        listOut[3] = ("src/d_solution.out");
//        listOut[4] = ("src/e_solution.out");
//        ReadFile ex = new ReadFile();
        for (int i = 0; i < 5; i++) {
            readFileMethod(listIn[i]);
        }
    }
}
