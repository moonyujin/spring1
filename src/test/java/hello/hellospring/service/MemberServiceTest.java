package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
//    Repository는 기본적으로 Entity를 DB에 저장하고 불러오는 작업을 한다.
//    Entity는 실체, 객체라는 의미로 업무에 필요하고 유용한 정보를 저장하고 관리하기 위한 집합적인 것이다.

    @BeforeEach // 여러 테스트함수 들이 각각 실행될때마다 매번 불러와주는 개념이다.
    public void beforeEach() {
//        void는 리턴이 되는 타입이 없음을 의미한다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //    Repository는 기본적으로 Entity를 DB에 저장하고 불러오는 작업을 한다.

    }

    @AfterEach // 각 테스트가 종료된 후 실행되는 함수이다.
    public void afterEach() {
        memberRepository.clearStore(); // memberRepository의 데이터를 클리어 해준다.
//
    }

    @Test // 해당 메소드가 단위 테스트임을 명시하는 어노테이션이다
//    어노테이션 : 주석처럼 쓰이며, 특별한 의미, 기능을 수행하도록 하는 기술이다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
//        setName은 method는 보통 어떤 객체의 이름을 바꾸는 기능을 합니다.
//        method는 어떠한 특정 작업을 수행하기 위한 명령문의 집합이다.

        //when
        Long saveId = memberService.join(member);
//      Long은 객체가 기본 데이터 유형을 저장할 수 있는 특정 유형의 클래스이다.
//      join은 데이터베이스 내의 여러 테이블에서 가져온 레코드를 조합하여 하나의 테이블이나 결과 집합으로 표현해준다.
//      레코드는 파일을 엑세스할 때 실제로 읽고 쓰는 단위
//      엑세스는 기억 장치에 데이터를 쓰거나 기억 장치에 들어 있는 데이터를 탐색하고 읽는 과정이다.


        //then
        Member findMember = memberService.findOne(saveId).get(); // findOne은 즉시 조회하여 객체를 전달한다.
        assertThat(member.getName()).isEqualTo(findMember.getName());
//      assertThat는 메서드를 사용하여 두 값을 비교할 수 있다
//      getName는 메서드는 해당 객체의 이름을 제공한다.
//      isEqualTo는 해당 값이 같은지를 확인하고, 객체가 비교 대상이 된다면 Java 문법의 equals() 메서드와 같은 기능을 한다.
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member(); // member1을 Member에 만든다.
        member1.setName("spring");
//        member1의 메소드의 이름을 spring으로 바꾼다.

        Member member2 = new Member(); // member2을 Member에 만든다.
        member2.setName("spring");
//          member2의 메소드의 이름을 spring으로 바꾼다.

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//        IllegalStateException은  객체 상태가 메서드 호출을 처리하기에 적절치 않을 때 사용한다.
//        assertThrows은 예외 발생을 확인한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 값이 같지 않으면 에러를 출력한다.
//        e.getMessage은 에러의 원인을 간단하게 출력합니다.

    }

}