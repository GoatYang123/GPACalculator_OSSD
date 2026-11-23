/**
 * This program is used to process data, such as judgment, calculation, sorting
 * All related functions are included in this package
 */

import static java.lang.IO.println;
import java.util.Scanner;


public class DataProcessing {
	static Scanner sc = new Scanner(System.in);

	//Determine the user's grade level
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

	//Calculate the grade percentage from the user's input
	public static double CurrentGrade(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray,
			int quizPercent, int testPercent, int assignmentPercent, int finalExamPercent, int finalExamScore) {
		// Dynamically count the entered grades (grades are 1-100; empty slots are 0)
		int quizCount = 0;
		int quizSum = 0;
		for (int j : quizPercentarray) {
			if (j != 0) {
				quizCount++;
				quizSum += j;
			}
		}
		int testCount = 0;
		int testSum = 0;
		for (int j : testPercentarray) {
			if (j != 0) {
				testCount++;
				testSum += j;
			}
		}
		int assignmentCount = 0;
		int assignmentSum = 0;
		for (int j : assignmentPercentarray) {
			if (j != 0) {
				assignmentCount++;
				assignmentSum += j;
			}
		}

		double weightedSum = 0;
		int totalWeight = 0;

		if (quizCount > 0) {
			double quizAvg = (double) quizSum / quizCount;
			weightedSum += quizAvg * quizPercent;
			totalWeight += quizPercent;
		}
		if (testCount > 0) {
			double testAvg = (double) testSum / testCount;
			weightedSum += testAvg * testPercent;
			totalWeight += testPercent;
		}
		if (assignmentCount > 0) {
			double assignmentAvg = (double) assignmentSum / assignmentCount;
			weightedSum += assignmentAvg * assignmentPercent;
			totalWeight += assignmentPercent;
		}

		// Final exam: only counted when a score (>0) has been entered
		if (finalExamScore > 0) {
			weightedSum += finalExamScore * finalExamPercent;
			totalWeight += finalExamPercent;
		}

		return totalWeight == 0 ? 0 : weightedSum / totalWeight;
	}

	//Show all grades to the user
	public static void ShowGrades(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray,int finalExamScore) {
		println("Score menu");
		println("your final exam mark is:" + finalExamScore);
		println("1.show all the quiz Grades");
		println("2.show all the test Grades");
		println("3.show all the assignment Grades");

		int option = sc.nextInt();

		switch (option) {
			case 1:
				int quizCount = 0;
				for (int i = 0; i < quizPercentarray.length; i++) {
					if (quizPercentarray[i] != 0) {
						quizCount++;
					}
				}
				if (quizCount == 0) {
					println("there is no grade for quiz");
				}else{
					for (int i = 0; i < quizPercentarray.length; i++) {
						if (quizPercentarray[i] != 0) {
							println("quiz " + (i + 1) + " : " + quizPercentarray[i]);
						}
					}
				}
				break;
			case 2:
				int testCount = 0;
				for (int i = 0; i < testPercentarray.length; i++) {
					if (testPercentarray[i] != 0) {
						testCount++;
					}
				}
				if (testCount == 0) {
					println("there is no grade for test");
				}else{
					for (int i = 0; i < testPercentarray.length; i++) {
						if (testPercentarray[i] != 0) {
							println("test " + (i + 1) + " : " + testPercentarray[i]);
						}
					}
				}
				break;
			case 3:
				int assignmentCount = 0;
				for (int i = 0; i < assignmentPercentarray.length; i++) {
					if (assignmentPercentarray[i] != 0) {
						assignmentCount++;
					}
				}
				if (assignmentCount == 0) {
					println("there is no grade for assignment");
				}else{
					for (int i = 0; i < assignmentPercentarray.length; i++) {
						if (assignmentPercentarray[i] != 0) {
							println("assignment " + (i + 1) + " : " + assignmentPercentarray[i]);
						}
					}
				}
				break;
		}
	}

	//Show all grades sorted from lowest to highest (without changing the original arrays)
	public static void SortGrades(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray){
		sortAndShow("quiz", quizPercentarray);
		sortAndShow("test", testPercentarray);
		sortAndShow("assignment", assignmentPercentarray);
	}

	private static void sortAndShow(String name, int[] array) {
		int count = 0;
		for (int value : array) {
			if (value != 0) {
				count++;
			}
		}
		if (count == 0) {
			println("there is no " + name + " grade");
			return;
		}
		int[] grades = new int[count];
		int idx = 0;
		for (int value : array) {
			if (value != 0) {
				grades[idx] = value;
				idx++;
			}
		}
		for (int i = 0; i < grades.length - 1; i++) {
			for (int j = 0; j < grades.length - 1 - i; j++) {
				if (grades[j] > grades[j + 1]) {
					int temp = grades[j];
					grades[j] = grades[j + 1];
					grades[j + 1] = temp;
				}
			}
		}
		println("your " + name + " grade sorted from lowest to highest:");
		for (int grade : grades) {
			println(grade);
		}
	}

	//Search for a specific grade
	public static void DataSearch(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray){
		println("1.search quiz grade");
		println("2.search test grade");
		println("3.search assignment grade");
		println("4.search by level");

		int option = sc.nextInt();

		switch (option) {
			case 1:
				println("which quiz grade do you want to search?");
				int quizTarget = sc.nextInt();
				int quizCount = 0;
				for (int i = 0; i < quizPercentarray.length; i++) {
					if (quizPercentarray[i] != 0 && quizPercentarray[i] == quizTarget) {
						println("quiz " + (i + 1) + " : " + quizPercentarray[i]);
						quizCount++;
					}
				}
				if (quizCount == 0) {
					println("there is no quiz grade " + quizTarget);
				}else{
					println("found " + quizCount + " quiz grade");
				}
				break;
			case 2:
				println("which test grade do you want to search?");
				int testTarget = sc.nextInt();
				int testCount = 0;
				for (int i = 0; i < testPercentarray.length; i++) {
					if (testPercentarray[i] != 0 && testPercentarray[i] == testTarget) {
						println("test " + (i + 1) + " : " + testPercentarray[i]);
						testCount++;
					}
				}
				if (testCount == 0) {
					println("there is no test grade " + testTarget);
				}else{
					println("found " + testCount + " test grade");
				}
				break;
			case 3:
				println("which assignment grade do you want to search?");
				int assignmentTarget = sc.nextInt();
				int assignmentCount = 0;
				for (int i = 0; i < assignmentPercentarray.length; i++) {
					if (assignmentPercentarray[i] != 0 && assignmentPercentarray[i] == assignmentTarget) {
						println("assignment " + (i + 1) + " : " + assignmentPercentarray[i]);
						assignmentCount++;
					}
				}
				if (assignmentCount == 0) {
					println("there is no assignment grade " + assignmentTarget);
				}else{
					println("found " + assignmentCount + " assignment grade");
				}
				break;
			case 4:
				println("which level do you want to search? (4/3/2/1/R)");
				String levelTarget = sc.next();
				int levelCount = 0;
				for (int i = 0; i < quizPercentarray.length; i++) {
					if (quizPercentarray[i] != 0 && level(quizPercentarray[i]).equals(levelTarget)) {
						println("quiz " + (i + 1) + " : " + quizPercentarray[i]);
						levelCount++;
					}
				}
				for (int i = 0; i < testPercentarray.length; i++) {
					if (testPercentarray[i] != 0 && level(testPercentarray[i]).equals(levelTarget)) {
						println("test " + (i + 1) + " : " + testPercentarray[i]);
						levelCount++;
					}
				}
				for (int i = 0; i < assignmentPercentarray.length; i++) {
					if (assignmentPercentarray[i] != 0 && level(assignmentPercentarray[i]).equals(levelTarget)) {
						println("assignment " + (i + 1) + " : " + assignmentPercentarray[i]);
						levelCount++;
					}
				}
				if (levelCount == 0) {
					println("there is no grade in level " + levelTarget);
				}else{
					println("found " + levelCount + " grade in level " + levelTarget);
				}
				break;
		}
	}

	//how to get target mark
	public static double[] NeedToReachTarget(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray,
			int quizPercent, int testPercent, int assignmentPercent,
			int finalExamPercent, int finalExamScore, int targetPercent) {
		double quizNeed = needOnNext(quizPercentarray, quizPercent,
				testPercentarray, testPercent, assignmentPercentarray, assignmentPercent,
				finalExamPercent, finalExamScore, targetPercent);
		double assignmentNeed = needOnNext(assignmentPercentarray, assignmentPercent,
				quizPercentarray, quizPercent, testPercentarray, testPercent,
				finalExamPercent, finalExamScore, targetPercent);
		double testNeed = needOnNext(testPercentarray, testPercent,
				quizPercentarray, quizPercent, assignmentPercentarray, assignmentPercent,
				finalExamPercent, finalExamScore, targetPercent);
		return new double[]{quizNeed, assignmentNeed, testNeed};
	}
	private static double needOnNext(int[] targetArray, int targetWeight,
			int[] otherA, int weightA, int[] otherB, int weightB,
			int finalExamPercent, int finalExamScore, int targetPercent) {
		if (targetWeight <= 0) {
			return Double.NaN;
		}

		// Weighted sum and total weight of the other two categories (+ final exam if taken)
		double otherWeightedSum = 0;
		int otherTotalWeight = 0;

		int countA = 0;
		int sumA = 0;
		for (int v : otherA) {
			if (v != 0) {
				countA++;
				sumA += v;
			}
		}
		if (countA > 0) {
			otherWeightedSum += (double) sumA / countA * weightA;
			otherTotalWeight += weightA;
		}

		int countB = 0;
		int sumB = 0;
		for (int v : otherB) {
			if (v != 0) {
				countB++;
				sumB += v;
			}
		}
		if (countB > 0) {
			otherWeightedSum += (double) sumB / countB * weightB;
			otherTotalWeight += weightB;
		}

		if (finalExamScore > 0) {
			otherWeightedSum += finalExamScore * finalExamPercent;
			otherTotalWeight += finalExamPercent;
		}

		// target = (otherWeightedSum + newAvg * targetWeight) / (otherTotalWeight + targetWeight)
		double required = targetPercent * (otherTotalWeight + targetWeight) - otherWeightedSum;
		double newAvg = required / targetWeight;

		// Add one more grade x so that (sumT + x) / (countT + 1) = newAvg
		int countT = 0;
		int sumT = 0;
		for (int v : targetArray) {
			if (v != 0) {
				countT++;
				sumT += v;
			}
		}
		return newAvg * (countT + 1) - sumT;
	}
}
