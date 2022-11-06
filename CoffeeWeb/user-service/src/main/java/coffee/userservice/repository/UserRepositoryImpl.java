package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository{

    final private EntityManager em;

    @Override
    public MemberEntity findById(String id) {
        MemberEntity memberEntity = em.createQuery("select m from MemberEntity m where m.id=:id",
                MemberEntity.class).getResultList().get(0);
        return memberEntity;
    }

    @Override
    public MemberEntity findByEmail(String email) {
        MemberEntity memberEntity = em.createQuery("select m from MemberEntity m where m.email=:email",
                MemberEntity.class).getResultList().get(0);
        return memberEntity;
    }
}
