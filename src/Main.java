import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public static void main(String[] args) {
    Scanner in;
    Scanner sc = new Scanner(System.in);

    //ASK some basic question to start
    IO.println("which subject that you take and want to calculate?");
    String subject = sc.nextLine();
    IO.println("What is the Quiz percentage?(EX: 2%)");
    int quizPercent = sc.nextInt();
    IO.println("What is the Assignment/Project percentage?(EX: 3%)");
    int assignPercent = sc.nextInt();
    IO.println("What is the Test percentage?(EX: 4%)");
    int testPercent = sc.nextInt();
    IO.println("What is the FinalExam percentage?(EX: 30%)");
    int finalExamPercent = sc.nextInt();
    IO.println("What is you taget? (EX: 85%)");



}