package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository{

    final private EntityManager em;

    @Override
    public MemberEntity findById(String id) {
        List<MemberEntity> memberEntity = em.createQuery("select m from MemberEntity m where m.id=:id", MemberEntity.class)
                .setParameter("id",id).getResultList();
        if(memberEntity.isEmpty()) return null;
        return memberEntity.get(0);
    }

    @Override
    public MemberEntity findByEmail(String email) {
        MemberEntity memberEntity = em.createQuery("select m from MemberEntity m where m.email=:email",
                MemberEntity.class).setParameter("email",email).getResultList().get(0);
        return memberEntity;
    }
}
