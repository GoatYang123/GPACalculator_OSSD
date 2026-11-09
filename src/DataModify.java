/**
 * 本程序用于让用户添加和删改数据
 * 相关的功能均放在这个包里
 */

import static java.lang.IO.println;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataModify {
	//基础信息录入
	public record BasicInfo(String subject, int quizPercent, int assignmentPercent, int testPercent,
							int finalExamPercent, int finalExamScore, int tagetPercent) {}
	public static BasicInfo BasicInforInput() {
		Scanner sc = new Scanner(System.in);

		//ASK some basic question to start
		println("which subject that you take and want to calculate?");
		String subject = sc.nextLine();

		println("What is the Quiz percentage?(EX: 2%, Type 2)");
		int quizPercent = sc.nextInt();

		println("What is the Assignment/Project percentage?(EX: 3%,Type 3)");
		int assignmentPercent = sc.nextInt();

		println("What is the Test percentage?(EX: 4%,Type 4)");
		int testPercent = sc.nextInt();

		println("What is the FinalExam percentage?(EX: 30%,Type 30)");
		int finalExamPercent = sc.nextInt();
		println("Have you taken the Final Exam? Enter your score (1-100), or 0 if not yet:");
		int finalExamScore = sc.nextInt();

		println("What is you taget? (EX: 85%,type 85)");
		int tagetPercent = sc.nextInt();

		return new BasicInfo(subject, quizPercent, assignmentPercent,
				testPercent, finalExamPercent, finalExamScore, tagetPercent);
	}

	//输入每一个项目的成绩
	public static void InputData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
	Scanner sc = new Scanner(System.in);

	//Quiz added
	println("Enter your quiz grades one by one (1-100). Enter 0 when done:");
	int count = 0;
	while (true) {
		println("Quiz grade " + (count + 1) + " (or 0 to stop):");
		int a1 = sc.nextInt();
		if (a1 == 0) {
			break;
		}
		if (a1 < 1 || a1 > 100) {
			println("the range is from 1 - 100! try again");
			continue;
		}
		quizPercentarray[count] = a1;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("quizgrades.txt", true));
			writer.println(a1);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		count++;
	}
	//Test Added
	println("Enter your test grades one by one (1-100). Enter 0 when done:");
	count = 0;
	while (true) {
		println("Test grade " + (count + 1) + " (or 0 to stop):");
		int a2 = sc.nextInt();
		if (a2 == 0) {
			break;
		}
		if (a2 < 1 || a2 > 100) {
			println("the range is from 1 - 100! try again");
			continue;
		}
		testPercentarray[count] = a2;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("testgrades.txt", true));
			writer.println(a2);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		count++;
	}
	//Assignment Added
	println("Enter your assignment grades one by one (1-100). Enter 0 when done:");
	count = 0;
	while (true) {
		println("Assignment grade " + (count + 1) + " (or 0 to stop):");
		int a2 = sc.nextInt();
		if (a2 == 0) {
			break;
		}
		if (a2 < 1 || a2 > 100) {
			println("the range is from 1 - 100! try again");
			continue;
		}
		assignmentPercentarray[count] = a2;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("AssignmentGrades.txt", true));
			writer.println(a2);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		count++;
	}
	}

	//让用户修改自己输入的成绩并保存进FileName.txt
	public static void ModifyData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
		Scanner sc = new Scanner(System.in);
		modifyGrade("Quiz", quizPercentarray, countEntered(quizPercentarray), sc, "quizgrades.txt");
		modifyGrade("Test", testPercentarray, countEntered(testPercentarray), sc, "testgrades.txt");
		modifyGrade("Assignment", assignmentPercentarray, countEntered(assignmentPercentarray), sc, "AssignmentGrades.txt");
	}
	private static int countEntered(int[] array) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				count++;
			}
		}
		return count;
	}
	private static void modifyGrade(String name, int[] array, int count, Scanner sc, String fileName) {
		if (count <= 0) {
			println(name + ": no grades yet.");
			return;
		}
		println(name + " grades:");
		for (int i = 0; i < count; i++) {
			println(name + " " + (i + 1) + " : " + array[i]);
		}
		println("Which " + name + " do you want to modify? (1-" + count + "), or 0 = I don't change this one");
		int choice = sc.nextInt();
		if (choice >= 1 && choice <= count) {
			int newGrade;
			while (true) {
				println("Enter the new grade (1-100):");
				newGrade = sc.nextInt();
				if (newGrade >= 1 && newGrade <= 100) {
					break;
				}
				println("the range is from 1 - 100! try again");
			}
			array[choice - 1] = newGrade;
			println(name + " " + choice + " updated to " + newGrade);

			try {
				PrintWriter writer = new PrintWriter(new FileWriter(fileName));
				for (int i = 0; i < count; i++) {
					writer.println(array[i]);
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			println("No modification.");
		}
	}

}




