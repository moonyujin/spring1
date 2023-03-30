package hello.hellospring.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 스프링 컨테이너에 AOP 담당 객체임을 알린다.
@Component // 컴포넌트 어노테이션을 명시해 스프링 컨테이너가 객체 생성하도록 한다.
//AOP란 흩어진 Aspect들을 모아서 모듈화 해줘서 관심사 분리라는 개념을 갖고 객체지향 프로그래밍을 통해
//더욱 객체지향적으로 만들어주는 기술이다.
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 패키지 하위에 다 적용시킨다.
    // Around는 횡단 관심사항의 대상 지정과 적용 시점을 지정한다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
//        throws는 현재 메소드에서 상위 메소드로 예외를 던진다.
//        execute는 실행한다라는 뜻이다.
//        joinpoint는 메서드 매개변수, 리턴 값, throw 된 예외 같은 조인 지점에서
//        사용할 수 있는 상태에 대한 액세스를 제공하는 AspectJ 인터페이스 이다.
        long start = System.currentTimeMillis();
//        currentTimeMillis는 현재 시간과 밀리초 단위를 반환한다.
        System.out.println("START: " + joinPoint.toString());
        try { // try는 중괄호 안에 있는 문장 실행을 시도한다.
            return joinPoint.proceed(); // 메서드를 실행
        } finally { // finally은 try의 실행문이 다 실행되면 최종적으로 finally 안에 있는 구문을 실행하라는 뜻이다.
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() +" " + timeMs + "ms");
//            시간 측정을 하기 위해서 이 코드를 추가시켰다.

        }

    }
}
