/**
 * This program is used to allow users to add and delete data
 * All related functions are included in this package
 */

import static java.lang.IO.println;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataModify {
	//Basic information input
	public record BasicInfo(String subject, int quizPercent, int assignmentPercent, int testPercent,
							int finalExamPercent, int finalExamScore, int tagetPercent) {}
	public static BasicInfo BasicInforInput() {
		Scanner sc = DataProcessing.sc;

		//ASK some basic question to start
		println("which subject that you take and want to calculate?");
		String subject = sc.nextLine();

		int quizPercent;
		int assignmentPercent;
		int testPercent;
		int finalExamPercent;
		int totalPercent;
		while (true) {
			println("What is the Quiz total percentage?(EX: 20%)");
			quizPercent = sc.nextInt();

			println("What is the Assignment/Project total percentage?(EX: 30%)");
			assignmentPercent = sc.nextInt();

			println("What is the Test total percentage?(EX: 20%)");
			testPercent = sc.nextInt();

			println("What is the FinalExam percentage?(EX: 30%)");
			finalExamPercent = sc.nextInt();

			// Check whether the total percentage is over 100%; if so, ask again
			totalPercent = quizPercent + assignmentPercent + testPercent + finalExamPercent;
			if (totalPercent <= 100) {
				break;
			}
			println("Warning: total is " + totalPercent + "%, The total exceeds 100%. Please enter the percentages again");
		}

		println("Have you taken the Final Exam? Enter your score (1-100), or 0 if not yet:");
		int finalExamScore = sc.nextInt();

		println("What is you taget? (EX: 85%,type 85)");
		int tagetPercent = sc.nextInt();

		return new BasicInfo(subject, quizPercent, assignmentPercent,
				testPercent, finalExamPercent, finalExamScore, tagetPercent);
	}

	//Input the grades for each category
	public static void InputData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
	Scanner sc = DataProcessing.sc;

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
		if (count >= quizPercentarray.length) {
			println("reach the maximum number of quiz grades: " + quizPercentarray.length);
			break;
		}
		quizPercentarray[count] = a1;
		count++;
	}
	writeBack("quizgrades.txt", quizPercentarray);
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
		if (count >= testPercentarray.length) {
			println("reach the maximum number of test grades: " + testPercentarray.length);
			break;
		}
		testPercentarray[count] = a2;
		count++;
	}
	writeBack("testgrades.txt", testPercentarray);
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
		if (count >= assignmentPercentarray.length) {
			println("reach the maximum number of assignment grades: " + assignmentPercentarray.length);
			break;
		}
		assignmentPercentarray[count] = a2;
		count++;
	}
	writeBack("AssignmentGrades.txt", assignmentPercentarray);
	}

	//Let the user modify a grade and save it back to the file
	public static void ModifyData(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
		Scanner sc = DataProcessing.sc;
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

	//Read grades from the files into the arrays (reload)
	public static void LoadFromFile(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
		loadArray("quizgrades.txt", quizPercentarray);
		loadArray("testgrades.txt", testPercentarray);
		loadArray("AssignmentGrades.txt", assignmentPercentarray);
		println("Grades reloaded from files.");
	}
	private static void loadArray(String fileName, int[] array) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null && i < array.length) {
				line = line.trim();
				if (!line.isEmpty()) {
					array[i] = Integer.parseInt(line);
					i++;
				}
			}
		} catch (IOException e) {
			println("read " + fileName + " fail: " + e.getMessage());
		} catch (NumberFormatException e) {
			println("file " + fileName + " Contains unresolved rows");
		}
	}

	//Delete the grade selected by the user
	public static void DataDelete(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray) {
		Scanner sc = DataProcessing.sc;

		println("1. Delete ALL grades");
		println("2. Delete an Assignment grade");
		println("3. Delete a Test grade");
		println("4. Delete a Quiz grade");

		int option = sc.nextInt();

		switch (option) {
			case 1:
				clearArray(quizPercentarray);
				clearArray(testPercentarray);
				clearArray(assignmentPercentarray);
				writeBack("quizgrades.txt", quizPercentarray);
				writeBack("testgrades.txt", testPercentarray);
				writeBack("AssignmentGrades.txt", assignmentPercentarray);
				println("All grades deleted.");
				break;
			case 2:
				deleteOne("Assignment", assignmentPercentarray, sc, "AssignmentGrades.txt");
				break;
			case 3:
				deleteOne("Test", testPercentarray, sc, "testgrades.txt");
				break;
			case 4:
				deleteOne("Quiz", quizPercentarray, sc, "quizgrades.txt");
				break;
			default:
				println("Invalid option.");
		}
	}
	private static void clearArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}
	private static void deleteOne(String name, int[] array, Scanner sc, String fileName) {
		int count = countEntered(array);
		if (count == 0) {
			println(name + ": no grades yet.");
			return;
		}
		println(name + " grades:");
		for (int i = 0; i < count; i++) {
			println(name + " " + (i + 1) + " : " + array[i]);
		}
		println("Which " + name + " do you want to delete? (1-" + count + "), or 0 = cancel");
		int choice = sc.nextInt();
		if (choice >= 1 && choice <= count) {
			int idx = choice - 1;
			// Shift the following grades forward to fill the deleted position
			for (int i = idx; i < count - 1; i++) {
				array[i] = array[i + 1];
			}
			array[count - 1] = 0;
			println(name + " " + choice + " deleted.");
			writeBack(fileName, array);
		} else {
			println("No deletion.");
		}
	}
	private static void writeBack(String fileName, int[] array) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(fileName));
			int count = countEntered(array);
			for (int i = 0; i < count; i++) {
				writer.println(array[i]);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




