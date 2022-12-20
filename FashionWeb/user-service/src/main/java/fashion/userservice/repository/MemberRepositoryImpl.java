package fashion.userservice.repository;

import fashion.userservice.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    final private EntityManager em;

    @Override
    public boolean existsById(String id) {
        return findById(id) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public void save(Member memberEntity) {
        em.persist(memberEntity);
    }

    @Override
    public Member findById(String id) {
        List<Member> memberEntity = em.createQuery("select m from Member m where m.id=:id", Member.class)
                .setParameter("id",id).getResultList();
        if(memberEntity.isEmpty()) return null;
        return memberEntity.get(0);
    }

    @Override
    public Member findByEmail(String email) {
        List<Member> memberEntity = em.createQuery("select m from Member m where m.email=:email",
                Member.class).setParameter("email",email).getResultList();
        if(memberEntity.isEmpty()) return null;
        return memberEntity.get(0);
    }

    @Override
    public Member findByPkId(Long pkId) {
        return em.find(Member.class, pkId);
    }
}
