# 프로토타입 (Prototype) 패턴

**정의** 

기존 인스턴스를 복제하여 새로운 인스턴스를 만드는 방법
- 자신을 복제 (clone)

**프로토타입 패턴을 사용하는 이유**

기존 객체를 응용해서 새로운 객체를 만들때

예를 들어, 데이터베이스에서 데이터를 읽어와서 인스턴스를 생성하거나

네트워크를 거쳐서 http 요청을 보내서 가져온 데이터 기반으로 인스턴스를 만드는 경우

⇒ 인스턴스를 만들때마다 오래걸리고, 자원도 많이 사용하게 되는 경우

**java의 clone 메소드를 알아보자.**

기본적으로 java에서 지원하는 clone 메소드는 ‘얕은 복사 (shallow copy)’ 를 지원함.

- 얕은 복사 (shallow copy) 란 ? 
주소 값을 복사함.
- 깊은 복사 (deep copy) 란 ?
    
    실제 값을 새로운 메모리 공간에 복사함.
    

**장점** 

- 복잡한 객체 생성 과정을 clone()메소드 내에 숨길 수 있음.
- ‘기존 객체를 복제하는 비용’ > ‘새 인스턴스를 만드는 비용’ 이 효율적일 수 있음.
- 추상적인 타입을 리턴할 수 있음. 
⇒ clone() 에서 return 하는 객체 타입은 반드시 해당 클래스와 동일할 필요가 없기때문에 클래스 계층 구조 내에서 추상화된 타입을 리턴할 수 있기 때문에 좀 더 유연하게 인스턴스를 생성할 수 있음.

**단점** 

복제한 객체를 만드는 과정이 복잡할 수 있음 (예를 들어, 순환 참조가 있는 경우)

**실무에서 쓰이는 방법**

```java
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
```

- collection 타입을 복사하는 경우
[방법 1] ArrayList의 경우 Cloneable interface를 상속하지만 보통 변수의 타입을 지정할 때 유연하게 사용하기 위해 추상적인 타입으로 타입을 지정하기 때문에 보통 변수의 타입을 List로 많이 쓴다.
    
    [방법 2] 그러나 List 의 경우 Cloneable interface를 상속하지 않기 때문에 보통 ArrayList의 생성자를 사용하여 clone 한다.
    
- ModelMapper 라이브러리를 사용하는 경우