package CreationalPatterns._05_prototype;

import java.util.ArrayList;
import java.util.List;

public class JavaCollectionExample {
    public static void main(String[] args){
        Student gaeun = new Student("gaeun");
        Student keesun = new Student("keesun");
        ArrayList<Student> studentArr1 = new ArrayList<>();
        studentArr1.add(gaeun);
        studentArr1.add(keesun);
        List<Student> studentArr2 = new ArrayList<>();
        studentArr2.add(gaeun);
        studentArr2.add(keesun);

        // 방법 1 : ArrayList의 clone 메소드를 사용하는 방법
        ArrayList<Student> clone1 = (ArrayList<Student>) studentArr1.clone();
        System.out.println(clone1);

        // 방법 2 [실무에서 적용] ArrayList의 생성자를 통해 복사하는 방법
        List<Student> clone2 = new ArrayList<>(studentArr2);
        System.out.println(clone2);
    }
}

