import static java.lang.IO.println;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataModify {

	public static void InputData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
	Scanner in;
	Scanner sc = new Scanner(System.in);

	//Quiz added
	println("how many quiz you want to add?(range is 1000)");
	int quizrange = sc.nextInt();
	for (int i=0;i<quizrange;i++){
		int a1;
		while (true){
			println("quiz grade:(1-100)");
			a1 = sc.nextInt();
			if (a1 >= 1 && a1 <= 100){
				break;
			}
			println("the range is from 1 - 100! try again");
		}
		quizPercentarray[i] = a1;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("quizgrades.txt", true));
			writer.println(a1);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Test Added
	println("how many tests you want to add?(range is 1000)");
	int testrange = sc.nextInt();
	for (int i=0;i<testrange;i++){
		int a2;
		while (true){
			println("Test grade:(1-100");
			a2 = sc.nextInt();
			if (a2 >= 1 && a2 <= 100){
				break;
			}
			println("the range is from 1 - 100! try again");
		}
		testPercentarray[i] = a2;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("testgrades.txt", true));
			writer.println(a2);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Assignment Added
	println("how many assignments you want to add?(range is 1000)");
	int assignmentrange = sc.nextInt();
	for (int i=0;i<assignmentrange;i++){
		int a2;
		while (true){
			println("Test grade:(1-100");
			a2 = sc.nextInt();
			if (a2 >= 1 && a2 <= 100){
				break;
			}
			println("the range is from 1 - 100! try again");
		}
		assignmentPercentarray[i] = a2;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("AssignmentGrades.txt", true));
			writer.println(a2);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}

	public static double CurrentGrade(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray, int quizPercent, int testPercent, int assignmentPercent, int quiznum, int testnum, int assignmentnum) {
		int quizSum = 0;
		for (int i = 0; i < quiznum; i++) {
			quizSum += quizPercentarray[i];
		}
		int testSum = 0;
		for (int i = 0; i < testnum; i++) {
			testSum += testPercentarray[i];
		}
		int assignmentSum = 0;
		for (int i = 0; i < assignmentnum; i++) {
			assignmentSum += assignmentPercentarray[i];
		}

		double quizAvg = quiznum == 0 ? 0 : (double) quizSum / quiznum;
		double testAvg = testnum == 0 ? 0 : (double) testSum / testnum;
		double assignmentAvg = assignmentnum == 0 ? 0 : (double) assignmentSum / assignmentnum;

		int totalWeight = quizPercent + testPercent + assignmentPercent;
		double current = totalWeight == 0 ? 0
				: (quizAvg * quizPercent + testAvg * testPercent + assignmentAvg * assignmentPercent) / totalWeight;

		return current;
	}

	public static String level(double CurrentGrade){
		String level;
		if (CurrentGrade >= 80){
			level = "4";
		}else if (CurrentGrade >= 70){
			level = "3";
		}else if (CurrentGrade >= 60){
			level = "2";
		}else if (CurrentGrade >= 50){
			level = "1";
		}else{
			level = "R";
		}
		return level;
	}

	public static void ModifyData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray, int quiznum, int testnum, int assignmentnum) {
		Scanner sc = new Scanner(System.in);
		modifyGrade("Quiz", quizPercentarray, quiznum, sc);
		modifyGrade("Test", testPercentarray, testnum, sc);
		modifyGrade("Assignment", assignmentPercentarray, assignmentnum, sc);
	}
	private static void modifyGrade(String name, int[] array, int count, Scanner sc) {
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
		} else {
			println("No modification.");
		}
	}
}




