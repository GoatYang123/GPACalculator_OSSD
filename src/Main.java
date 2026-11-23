import java.util.Scanner;
import java.io.IOException;
import static java.lang.IO.println;

void main() throws IOException {
    Scanner sc = DataProcessing.sc;

    //ASK some basic question to start
    DataModify.BasicInfo info = DataModify.BasicInforInput();
    String subject = info.subject();
    int quizPercent = info.quizPercent();
    int assignmentPercent = info.assignmentPercent();
    int testPercent = info.testPercent();
    int finalExamPercent = info.finalExamPercent();
    int finalExamScore = info.finalExamScore();
    int tagetPercent = info.tagetPercent();

    //Create the files used to save the grades
    File quizgrades = new File("quizgrades.txt");
    File testgrades = new File("testgrades.txt");
    File AssignmentGrades = new File("AssignmentGrades.txt");

    //Create the files used to store the data
    try {
        quizgrades.createNewFile();
        testgrades.createNewFile();
        AssignmentGrades.createNewFile();
        System.out.println("Files location: \n" + quizgrades.getAbsolutePath());
    } catch (IOException e) {
        e.printStackTrace();
    }

    //Set up the arrays with a maximum size of 1000
	int[] quizPercentarray = new int[1000];
    int[] testPercentarray = new int[1000];
    int[] assignmentPercentarray = new int[1000];

    //Read the files and guide the user to input data here
        println("you can check the GPA location and modify it or you can choose input data at here.");
        println("1. Add Grade");
        println("2. I modify the file by myself");
        switch (sc.nextInt()) {
            case 1: DataModify.InputData(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;
            case 2: DataModify.LoadFromFile(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;
        }

        //Show the menu
    while (true) {
        double CurrentGrade = DataProcessing.CurrentGrade(quizPercentarray,testPercentarray,assignmentPercentarray,
                quizPercent,testPercent,assignmentPercent,
                finalExamPercent,finalExamScore);
        String Level = DataProcessing.level(CurrentGrade);
        println(subject + " grade");
        println("your taget:" + tagetPercent + "%");
        println("your current grade:" + CurrentGrade +"%" + " level " + Level);
        double[] need = DataProcessing.NeedToReachTarget(quizPercentarray, testPercentarray, assignmentPercentarray,
                quizPercent, testPercent, assignmentPercent,
                finalExamPercent, finalExamScore, tagetPercent);
        double quizgradetarget = need[0];
        double assignmentgradetarget = need[1];
        double testgradetraget = need[2];
        System.out.println("to reach your target of " + tagetPercent + "%:");
        System.out.println("next quiz: " + describeNeed(quizgradetarget));
        System.out.println("next assignment: " + describeNeed(assignmentgradetarget));
        System.out.println("next test: " + describeNeed(testgradetraget));
        println("1.Modify your grade");
        println("2.show all the grade");
        println("3.show all the grades from Lowest to highest");
        println("4.delete data from your grade");
        println("5.Search grade");
        println("6.Reload grades from file");
        println("7.Exit");

        int option = sc.nextInt();

        switch (option) {
            //change grade from test,quiz,Assignment
            case 1:DataModify.ModifyData(quizPercentarray,testPercentarray,assignmentPercentarray);
            break;

            //show the grade and sort
            case 2:DataProcessing.ShowGrades(quizPercentarray,testPercentarray,assignmentPercentarray,finalExamScore);
            break;

            //show all the grade
            case 3:DataProcessing.SortGrades(quizPercentarray,testPercentarray,assignmentPercentarray);
            break;

            //delete data
            case 4:DataModify.DataDelete(quizPercentarray,testPercentarray,assignmentPercentarray);
            break;

            //Search
            case 5:DataProcessing.DataSearch(quizPercentarray,testPercentarray,assignmentPercentarray);
            break;

            //reload the file
            case 6: DataModify.LoadFromFile(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;

            //exit
            case 7: return;
        }
    }
}

String describeNeed(double need) {
    if (Double.isNaN(need)) {
        return "this category has 0% weight, so it does not affect your grade";
    }
    if (need > 100) {
        return "impossible - you would need " + (Math.round(need * 10) / 10.0) + ", which is above 100";
    }
    if (need <= 0) {
        return "target already reached - you can score 0";
    }
    return "you need to get " + (Math.round(need * 10) / 10.0) + " or higher";
}