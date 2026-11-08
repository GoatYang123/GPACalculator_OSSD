import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.IO.println;

public static void main(String[] args) {
    Scanner in;
    Scanner sc = new Scanner(System.in);

    //ASK some basic question to start
    println("which subject that you take and want to calculate?");
    String subject = sc.nextLine();
    println("What is the Quiz percentage?(EX: 2%, Type 2)");
    int quizPercent = sc.nextInt();
    println("What is the Assignment/Project percentage?(EX: 3%,Type 3)");
    int assignPercent = sc.nextInt();
    println("What is the Test percentage?(EX: 4%,Type 4)");
    int testPercent = sc.nextInt();
    println("What is the FinalExam percentage?(EX: 30%,Type 30)");
    int finalExamPercent = sc.nextInt();
    println("What is you taget? (EX: 85%,type 85)");
    int tagetPercent = sc.nextInt();

    //创建一个文件来保存成绩
    File file = new File("GPA.txt");
    try {
        if (file.createNewFile()) {
            System.out.println("The file was created successfully：" + file.getAbsolutePath());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }





}