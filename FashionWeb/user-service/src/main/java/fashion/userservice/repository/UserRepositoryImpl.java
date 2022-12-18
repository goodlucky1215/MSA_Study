package fashion.userservice.repository;

import fashion.userservice.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

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
    public void save(UserEntity memberEntity) {
        em.persist(memberEntity);
    }

    @Override
    public UserEntity findById(String id) {
        List<UserEntity> memberEntity = em.createQuery("select m from UserEntity m where m.id=:id", UserEntity.class)
                .setParameter("id",id).getResultList();
        if(memberEntity.isEmpty()) return null;
        return memberEntity.get(0);
    }

    @Override
    public UserEntity findByEmail(String email) {
        List<UserEntity> memberEntity = em.createQuery("select m from UserEntity m where m.email=:email",
                UserEntity.class).setParameter("email",email).getResultList();
        if(memberEntity.isEmpty()) return null;
        return memberEntity.get(0);
    }

    @Override
    public UserEntity findByPkId(Long pkId) {
        return em.find(UserEntity.class, pkId);
    }
}
