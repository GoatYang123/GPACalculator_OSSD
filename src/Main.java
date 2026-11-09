import java.util.Scanner;
import java.io.IOException;
import static java.lang.IO.println;

void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    //ASK some basic question to start
    println("which subject that you take and want to calculate?");
    String subject = sc.nextLine();

    println("What is the Quiz percentage?(EX: 2%, Type 2)");
    int quizPercent = sc.nextInt();
    println("how many quiz you have in total?");
    int quiznum = sc.nextInt();

    println("What is the Assignment/Project percentage?(EX: 3%,Type 3)");
    int assignmentPercent = sc.nextInt();
    println("how many assignments you have in total?");
    int assignmentnum = sc.nextInt();

    println("What is the Test percentage?(EX: 4%,Type 4)");
    int testPercent = sc.nextInt();
    println("how many tests you have in total?");
    int testnum = sc.nextInt();

    println("What is the FinalExam percentage?(EX: 30%,Type 30)");
    int finalExamPercent = sc.nextInt();

    println("If you have attendant type here(1-100)(this has a stable value which is 10%):");
    int attendancePercent = sc.nextInt();

    println("What is you taget? (EX: 85%,type 85)");
    int tagetPercent = sc.nextInt();

    //创建一个文件来保存成绩
    File quizgrades = new File("quizgrades.txt");
    File testgrades = new File("testgrades.txt");
    File AssignmentGrades = new File("AssignmentGrades.txt");
    File finalexamgrades = new File("finalexamgrades.txt");
    File tagetgrades = new File("tagetgrades.txt");

    //创建四个文件夹用于储存数据
    try {
        if (quizgrades.createNewFile() || testgrades.createNewFile() || finalexamgrades.createNewFile() ||
                tagetgrades.createNewFile() || AssignmentGrades.createNewFile()) {
            System.out.println("Files created at: \n" + quizgrades.getAbsolutePath());
        } else {
            System.out.println("File already exists.\n" + quizgrades.getAbsolutePath());
        }
    } catch (IOException e) {
        e.printStackTrace();
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
            case 1:DataModify.InputData(quizPercentarray,testPercentarray,assignmentPercentarray);
            case 2:
        }

        //展示菜单
    while (true) {
        double CurrentGrade = DataModify.CurrentGrade(quizPercentarray,testPercentarray,assignmentPercentarray,
                quizPercent,testPercent,assignmentPercent,quiznum,testnum,assignmentnum);
        String Level = DataModify.level(CurrentGrade);
        println(subject + " grade");
        println("your taget:" + tagetPercent + "%");
        println("your current grade:" + CurrentGrade +"%" + " level " + Level);
        println("1.Modify your grade");
        println("2.To achieve the target score, it is necessary to……");
        println("3.show all you grade");

        int option = sc.nextInt();

        switch (option) {
            case 1: DataModify.ModifyData(quizPercentarray,testPercentarray,assignmentPercentarray,quiznum,testnum,assignmentnum);
                break;
        }
    }

}