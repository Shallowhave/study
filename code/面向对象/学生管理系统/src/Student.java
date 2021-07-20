import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.util.ArrayList;
import java.util.Scanner;

/*
学生类
 */
public class Student {
    private String name;
    private String age;

    public Student() {

    }

    public Student(String name, String age) {
        this.name = name;
        this.age = age;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public void add(ArrayList student) {
        System.out.println("添加学生");
        System.out.println("请输入要添加的学生姓名");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        System.out.println("请输入要添加的学生年龄");
        age = s.nextLine();
        // student.add(new Student(name, age));
        Student student1 = new Student(name, age);
        boolean add = student.add(student1);
        if (add){
            System.out.println("添加学生成功");
        }else System.out.println("添加学生失败，请联系开发者");

    }

    public void delete(ArrayList student) {
        System.out.println("删除学生");
        for (int i = 0; i < student.size(); i++) {
            if (student.get(i) instanceof Student) {
                Student o = (Student) student.get(i);
                System.out.println("编号："+(i+1)+"," + o.getName() + "," + o.getAge());

            }
        }
        System.out.println("请输入要删除学生的编号：");
        Scanner scanner = new Scanner(System.in);
        // name = scanner.nextLine();
        int i = scanner.nextInt();
        Object remove = student.remove(i-1);
        // boolean remove = student.remove();
        // if (remove){
        //     System.out.println("删除成功");
        // }else System.out.println("删除失败");
        if (remove != null) {
            System.out.println("删除成功");
        } else System.out.println("删除失败");

    }
    public void update(ArrayList student) {
        System.out.println("修改学生");
        for (int i = 0; i < student.size(); i++) {
            if (student.get(i) instanceof Student) {
                Student o = (Student) student.get(i);
                System.out.println("编号："+(i+1)+"," + o.getName() + "," + o.getAge());

            }
        }
        System.out.println("请输入要修改学生的编号：");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Object o = student.get(i-1);
        if (o instanceof Student){
            Student s1 = (Student) o;
            System.out.println("请输入要修改的姓名：");
             name = scanner.nextLine();
            System.out.println("请输入要修改的年龄：");
             age = scanner.nextLine();
            s1.setName(name);
            s1.setAge(age);
        }



    }

    public void select(ArrayList student) {
        System.out.println("查看所有学生");

        for (int i = 0; i < student.size(); i++) {
            if (student.get(i) instanceof Student) {
                Student o = (Student) student.get(i);
                System.out.println(o.getName() + "," + o.getAge());

            }
        }
        System.out.println("总共有" + student.size() + "个学生");
    }
}
