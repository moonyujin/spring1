package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 테스트가 종료된 후 실행되는 함수이다.
    public void afterEach() {
        repository.clearStore(); // memberRepository의 데이터를 클리어 해준다.
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring"); // member에 springd을 저장해준다.

        repository.save(member); // repository에 member를 저장해준다.

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
//        assertThat은 메서드를 사용하여 두 값을 비교할 수 있다.
//       isEqualTo는 해당 값이 같은지를 확인하고, 객체가 비교 대상이 된다면 Java 문법의 equals() 메서드와 같은 기능을 한다.
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1); // member1을 만들고 spring1를 저장한다.

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2); // member2을 만들고 spring2를 저장한다.

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member(); // member 선언
        member1.setName("spring1"); // 이름 설정한다.
        repository.save(member1); // 저장한다.

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll(); // 회원 리스트를 가져온다.

        assertThat(result.size()).isEqualTo(2); // 회원 리스트 수와 현재 생성한 회원 수가 일치한지 비교한다.
    }
}
