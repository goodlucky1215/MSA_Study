package fashion.userservice.repository;


import fashion.userservice.entity.UserEntity;

public interface UserRepository {//extends CrudRepository<UserEntity, Long> {

    //회원가입
    boolean existsById(String id);
    boolean existsByEmail(String email);
    void save(UserEntity memberEntity);

    //id로 로그인
    UserEntity findById(String id);

    //email로 로그인
    UserEntity findByEmail(String email);

    //닉네임 변경
    UserEntity findByPkId(Long pkId);
}
