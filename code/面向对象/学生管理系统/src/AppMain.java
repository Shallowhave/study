import java.util.ArrayList;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Student student = new Student();
        ArrayList<Student> students = new ArrayList<>();

        do {
    System.out.println("------------欢迎来到学生管理系统------------");
    System.out.println("1 添加学生");
    System.out.println("2 修改学生");
    System.out.println("3 删除学生");
    System.out.println("4 查看所有学生");
    System.out.println("请输入您的选择：");
    Scanner scanner = new Scanner(System.in);
    String number= scanner.nextLine();
    switch (number){
        case "1":
            student.add(students);
            break;

        case "2":
            student.update(students);
            break;
        case "3":
            student.delete(students);
            break;
        case "4":
            student.select(students);
        default:
            System.out.println("请输入正确的选项");
    }

}while (true);


    }
}
