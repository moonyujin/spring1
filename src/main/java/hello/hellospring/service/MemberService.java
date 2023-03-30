package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
// @Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랙잭션이 되도록 보장해준다.
// 트랜잭션이란 데이터베이스 관리 시스템 또는 유사한 시스템에서 상호작용의 단위이다.
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입
     */

    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member); // memberRepository에 member를 저장한다.
        return member.getId(); // member.getId를 반환해준다.
    }

    private void validateDuplicateMember(Member member) { // 회원 중복 검사
//        validateDuplicateMember로 동일한 이름을 가진 회원이 있는 지 검사하고 통과하면
//        memberRepository에 전달받은 회원을 저장한다.
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
//        회원을 찾는 코드이다.
//        findByName() 의 반환값은 Optional 로 감쌌기 때문에 ifPresent() 사용이 가능합니다.
    }
}

