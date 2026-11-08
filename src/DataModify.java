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

}




