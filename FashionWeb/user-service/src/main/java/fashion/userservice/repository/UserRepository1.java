package fashion.userservice.repository;


import fashion.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository1 extends CrudRepository<UserEntity, Long> {

    //회원가입
    boolean existsById(String id);
    boolean existsByEmail(String email);

    //id로 로그인
    UserEntity findById(String id);

    //email로 로그인
    UserEntity findByEmail(String email);

    //
    UserEntity findByPkId(Long pkId);
}
