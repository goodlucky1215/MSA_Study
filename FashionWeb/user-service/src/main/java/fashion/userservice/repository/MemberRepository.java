package fashion.userservice.repository;


import fashion.userservice.entity.Member;

public interface MemberRepository {//extends CrudRepository<UserEntity, Long> {

    //회원가입
    boolean existsById(String id);
    boolean existsByEmail(String email);
    void save(Member memberEntity);

    //id로 로그인
    Member findById(String id);

    //email로 로그인
    Member findByEmail(String email);

    //닉네임 변경
    Member findByPkId(Long pkId);
}
