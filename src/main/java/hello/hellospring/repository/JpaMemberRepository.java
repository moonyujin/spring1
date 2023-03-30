package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;
//    EntityManager는 실제 트랜잭션 단위가 수행될 때마다 생성된다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) { // Member에 member를 저장한다.
        em.persist(member);
//        em.persist: 객체를 저장한 상태
        return member; // member를 반환해준다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
//        ofNullable : 명시된 값이 null이 아니면 명시된 값을 가지는 Optional 객체를 반환하며,
//        명시된 값이 null이면 비어있는 Optional 객체를 반환합니다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
//        Query: 반환 타입이 명확하지 않을 때 사용
//        getResultList() : 결과를 리스트로 반환하고 없다면 빈 리스트를 반환

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
//        getResultList(): 결과를 컬렉션으로 반환한다. 결과가 없으면 빈 컬렉션이 반환된다.

    }
}
