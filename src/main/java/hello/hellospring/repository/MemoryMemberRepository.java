package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); // 인스턴스를 생성할 때 사용한다.
    private static long sequence = 0L; // sequence는 사용자가 생성한 데이터베이스 객체이다.

    @Override
    public Member save(Member member) { // member를 저장한다.
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
//        findAny는 Stream에서 가장 먼저 탐색되는 요소를 리턴하고
//        findFirst()는 조건에 일치하는 요소들 중에 Stream에서 순서가 가장 앞에 있는 요소를 리턴합니다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
