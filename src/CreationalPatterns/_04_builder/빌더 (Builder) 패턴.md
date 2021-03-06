# 빌더 (Builder) 패턴

## 정의

동일한 프로세스를 거쳐 다양한 구성의 인스턴스를 만드는 방법

## 빌더 패턴을 사용하는 이유

빌더 인터페이스를 사용하는 클라이언트 코드에서 호출했을 때 내부적으로 처리하기 위함

## 장점

→ 필요한 데이터만 설정할 수 있음

→ 유연성을 확보할 수 있음

→ 가독성을 높일 수 있음

→ 불변성을 확보할 수 있음

## 단점

→ 객체를 조금 더 만들어야 함

→ 구조가 복잡해짐

- 실무에서 쓰이는 방법
    - StringBuilder
    - Stream.Builder
    - StringBuffer
    - lombok라이브러리의 @Builder → annotation processor를 통해 동작
    - UriComponents
