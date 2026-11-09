/**
 * 本程序用于处理数据，如判断，计算，排序……
 * 相关的功能都在这个包内
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

		// 每类总权重 = 单项百分比 × 已输入个数
		int quizWeight = quizPercent * quizCount;
		int testWeight = testPercent * testCount;
		int assignmentWeight = assignmentPercent * assignmentCount;

		double weightedSum = 0;
		int totalWeight = 0;

		if (quizCount > 0) {
			weightedSum += quizSum * quizPercent;
			totalWeight += quizWeight;
		}
		if (testCount > 0) {
			weightedSum += testSum * testPercent;
			totalWeight += testWeight;
		}
		if (assignmentCount > 0) {
			weightedSum += assignmentSum * assignmentPercent;
			totalWeight += assignmentWeight;
		}

		// 期末：只有输入了期末分数(>0)才计入
		if (finalExamScore > 0) {
			weightedSum += finalExamScore * finalExamPercent;
			totalWeight += finalExamPercent;
		}

		return totalWeight == 0 ? 0 : weightedSum / totalWeight;
	}


}
