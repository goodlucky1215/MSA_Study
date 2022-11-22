package coffee.userservice.repository;


import coffee.userservice.Entity.UserEntity;

public interface UserRepository {//extends CrudRepository<MemberEntity, Long> {

    //회원가입
    boolean existsById(String id);
    boolean existsByEmail(String email);
    void save(UserEntity memberEntity);

    //id로 로그인
    UserEntity findById(String id);

    //email로 로그인
    UserEntity findByEmail(String email);

    //
    UserEntity findByPkId(Long pkId);
}
