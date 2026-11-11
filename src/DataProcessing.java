/**
 * This program is used to process data, such as judgment, calculation, sorting
 * All related functions are included in this package
 */

import static java.lang.IO.println;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataProcessing {

	//判断用户的成绩等级
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

	//通过用户的输入推断出成绩百分比
	public static double CurrentGrade(int[] quizPercentarray, int[] testPercentarray, int[] assignmentPercentarray,
			int quizPercent, int testPercent, int assignmentPercent,
			int finalExamPercent, int finalExamScore) {
		// 动态统计实际输入的成绩个数（成绩范围 1-100，未输入的位置是 0）
		int quizCount = 0;
		int quizSum = 0;
		for (int i = 0; i < quizPercentarray.length; i++) {
			if (quizPercentarray[i] != 0) {
				quizCount++;
				quizSum += quizPercentarray[i];
			}
		}
		int testCount = 0;
		int testSum = 0;
		for (int i = 0; i < testPercentarray.length; i++) {
			if (testPercentarray[i] != 0) {
				testCount++;
				testSum += testPercentarray[i];
			}
		}
		int assignmentCount = 0;
		int assignmentSum = 0;
		for (int i = 0; i < assignmentPercentarray.length; i++) {
			if (assignmentPercentarray[i] != 0) {
				assignmentCount++;
				assignmentSum += assignmentPercentarray[i];
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

		// 期末：只有输入了期末分数(>0)才计入
		if (finalExamScore > 0) {
			weightedSum += finalExamScore * finalExamPercent;
			totalWeight += finalExamPercent;
		}

		return totalWeight == 0 ? 0 : weightedSum / totalWeight;
	}


}
