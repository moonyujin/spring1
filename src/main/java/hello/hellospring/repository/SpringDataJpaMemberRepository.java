package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//SpringDataJpaMemberRepository는 스프링 빈을 자동으로 등록해준다.
    @Override
//    @Override는 클래스나 메서드, 변수에 @을 사용하는 것을 말한다.
    Optional<Member> findByName(String name);
//    Optional은 선택형값을 캡슐화하는 클래스이다.
}
