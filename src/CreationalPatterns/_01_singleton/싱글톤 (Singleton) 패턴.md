### 구현 방법

- private 생성자와 static 메소드를 이용한 방법

단점 → 멀티 스레드 환경에서 동시에 여러 스레드가 들어왔을때 동시에 new 키워드를 통해 서로 다른 인스턴스를 생성할 수 있다.

- synchronized 키워드 사용하는 방법 : static 메소드를 synchronized static 메소드로 변경하는 방법

```java
public class Settings {
	private static Settings instance;

	private Settings(){
	}

	public static synchronized Settings getInstance(){
		if(instance == null){
			instance = new Settings();
		}

		return instance;
	}
}
```

장점 → 동시에 여러 스레드가 static 메소드에 들어올 수 없기 때문에 하나의 인스턴스만 보장할 수 있다.
단점 → 동기화 처리 작업으로 인한 성능 저하
          (synchronized 키워드 사용시 동작하는 방법에 의한 성능 저하)

 ⇒ synchronized 키워드의 lock 개념 찾아보기

- 이른 초기화 (eager initialization) 사용하는 방법

단점 → application 로딩시 미리 객체를 만들었음에도 불구하고, 이후에 호출하지 않는 경우 자원이 낭비될 수 있다.

→ 미리 인스턴스를 생성해놓는 것이 아닌, 인스턴스가 사용될 때 생성해야할 필요가 있음.

- double checked locking 방법 : 동기화 블럭을 만드는 방법

```java
public class Settings {
	private static volatile Settings instance;

	private Settings(){
	}

	public static Settings getInstance(){
		if(instance == null){
			synchronized (Setting.class){
				if(instance == null){
					instance = new Settings();
				}
			}
		}

		return instance;
	}
}
```

**효율적인 이유 ?**

getInstance()를 호출할 때마다 매번 동기화 매커니즘 ( synchronized 메소드) 을 사용하는 것이 아닌, 트래픽이 많은 경우에만 (getInstance() 메소드에 동시에 여러 개의 스레드가 들어온 경우) 대비해서 동기화 메커니즘을 사용하기 때문에 보다 효율적이다.

또한, 인스턴스를 필요로 하는 시점에만 생성할 수 있다는 장점이 있다.

**단점 ?**

코드 작성 난이도가 높고, 자바버전 1.5 이상인 경우에만 동작. (volatile 키워드)

- static inner 클래스 사용하는 방법 **

```java
public class Settings {

	private Settings(){
	}

	public static class SettingHolder{
		private static final Setting INSTANCE = new Settings();
	}

	public static Settings getInstance(){
		return SettingsHolder.INSTANCE;
	}
}
```

→ 멀티 스레드 환경에서도 안전

→ 인스턴스 호출 시 생성 (lazy loading 가능)

⇒ 왜지 모르겠는데 ? 왜 설명안해줘 이상하네 저 놈

### 싱글톤 패턴 구현 방법을 깨뜨리는 경우

1. 리플렉션을 사용하여 깨뜨리는 경우

```java
public static void main(String[] args) throws Exceptions... {
	Settings settings = Settings.getInstance();

	Constuctor<Settings> constructor = Settings.class.getDeclaredConstructor();
	constructor.setAcessible(true);
	Setting settings1 = constructor.newInstance();

	System.out.println(settings == settings1); // false
}
```

⇒ 리플렉션 ..?

1. 직렬화 & 역직렬화를 사용하여 깨뜨리는 경우

```java
public class Settings implements Serializable {

	private Settings(){
	}

	public static class SettingHolder{
		private static final Setting INSTANCE = new Settings();
	}

	public static Settings getInstance(){
		return SettingsHolder.INSTANCE;
	}
}
```

```java
public static void main(String[] args) throws Exceptions... {
	Settings settings = Settings.getInstance();
	Settings settings1 = null;

	// 객체 직렬화
	try(ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings.obj"))){
		out.writeObject(settings);
	}

	// 객체 역직렬화
	try(ObjectInput in = new ObjectInputStream(new FileInputStream("settings.obj"))){
		settings1 = (Settings) in.readObject();
	}

	System.out.println(settings == settings1); // false
}
```

→ 객체 역직렬화를 하는 경우, 반드시 생성자를 사용해서 인스턴스 다시 만들어주기때문에 서로 다른 인스턴스가 된다...
⇒ 뭔 소리야

⇒ 직렬화 ? 역직렬화 ?

→  대응 방안

```java
public class Settings implements Serializable {

	private Settings(){
	}

	public static class SettingHolder{
		private static final Setting INSTANCE = new Settings();
	}

	public static Settings getInstance(){
		return SettingsHolder.INSTANCE;
	}

	protected Object readResolve(){
		return getInstance();
	}
}
```

역직렬화를 할 때, readResolve()라는 메소드를 사용하게 되는데

이 메소드 안에서 getInstance()를 호출하도록 구현해놓으면, 동일한 인스턴스를 사용하도록 해결할 수 있음.

- 대응방안 : enum 사용
1. 리플렉션을 사용하여 깨뜨리는 경우
2. 직렬화 & 역직렬화를 사용하여 깨뜨리는 경우

```java
public enum Settings{
	INSTANCE;
}

// java enum 의 경우, reflection에서 instance를 생성할 수 없도록 막아놓았기때문에 대응이 가능하다.
// 단점:  미리 인스턴스가 만들어진다.
//       상속을 받지 못한다. (enum만 상속가능)
```

### TODO

1. 자바에서 enum을 사용하지 않고 싱글톤 패턴을 구현하는 방법
2. private 생성자와 static 메소드를 사용하는 방법의 단점?
3. enum을 사용해 싱글톤 패턴을 구현하는 방법과 장점/단점은?
4. static inner 클래스를 사용해 싱글톤 패턴 구현하기

### 실무에서 싱글톤 패턴이 쓰이는 경우

- java.lang.Runtime 라이브러리
- 추가로 참고할 링크

    [https://juns-lee.tistory.com/entry/SpringFramework에서의-싱글톤-전략?category=772885](https://juns-lee.tistory.com/entry/SpringFramework%EC%97%90%EC%84%9C%EC%9D%98-%EC%8B%B1%EA%B8%80%ED%86%A4-%EC%A0%84%EB%9E%B5?category=772885)