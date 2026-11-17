import java.util.Scanner;
import java.io.IOException;
import static java.lang.IO.println;

void main() throws IOException {
    Scanner sc = new Scanner(System.in);

    //ASK some basic question to start
    DataModify.BasicInfo info = DataModify.BasicInforInput();
    String subject = info.subject();
    int quizPercent = info.quizPercent();
    int assignmentPercent = info.assignmentPercent();
    int testPercent = info.testPercent();
    int finalExamPercent = info.finalExamPercent();
    int finalExamScore = info.finalExamScore();
    int tagetPercent = info.tagetPercent();

    //创建一个文件来保存成绩
    File quizgrades = new File("quizgrades.txt");
    File testgrades = new File("testgrades.txt");
    File AssignmentGrades = new File("AssignmentGrades.txt");
    File finalexamgrades = new File("finalexamgrades.txt");
    File tagetgrades = new File("tagetgrades.txt");

    //创建四个文件夹用于储存数据
        if (quizgrades.createNewFile() || testgrades.createNewFile() || finalexamgrades.createNewFile() ||
                tagetgrades.createNewFile() || AssignmentGrades.createNewFile()) {
            System.out.println("Files created at: \n" + quizgrades.getAbsolutePath() + "\n" + testgrades.createNewFile()
                    + "\n" + AssignmentGrades.createNewFile() + "\n" + finalexamgrades.createNewFile());
        } else {
            System.out.println("File already exists.\n" + quizgrades.getAbsolutePath() + "\n" + testgrades.createNewFile()
                    + "\n" + AssignmentGrades.createNewFile() + "\n" + finalexamgrades.createNewFile());
        }

    //设置数组，最大值为1000
	int[] quizPercentarray = new int[1000];
    int[] testPercentarray = new int[1000];
    int[] assignmentPercentarray = new int[1000];

    //读取文件内信息及引导用户在此处输入
        println("you can check the GPA location and modify it or you can choose input data at here.");
        println("1. Add Grade");
        println("2. I modify the file by myself");
        switch (sc.nextInt()) {
            case 1: DataModify.InputData(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;
            case 2: DataModify.LoadFromFile(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;
        }

        //展示菜单
    while (true) {
        double CurrentGrade = DataProcessing.CurrentGrade(quizPercentarray,testPercentarray,assignmentPercentarray,
                quizPercent,testPercent,assignmentPercent,
                finalExamPercent,finalExamScore);
        String Level = DataProcessing.level(CurrentGrade);
        println(subject + " grade");
        println("your taget:" + tagetPercent + "%");
        println("your current grade:" + CurrentGrade +"%" + " level " + Level);
        println("1.Modify your grade");
        println("2.show all the grade");
        println("3.show all the grades from Lowest to highest");
        println("4.delete data from your grade");
        println("5.sreach grade from your grade");
        println("6.Reload grades from file");

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

            //
            case 4:

            case 5:

            //reload the file
            case 6: DataModify.LoadFromFile(quizPercentarray,testPercentarray,assignmentPercentarray);
                break;
        }
    }

}