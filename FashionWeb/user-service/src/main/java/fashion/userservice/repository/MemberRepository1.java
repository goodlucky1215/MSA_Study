package fashion.userservice.repository;


import fashion.userservice.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository1 extends CrudRepository<Member, Long> {

    //회원가입
    boolean existsById(String id);
    boolean existsByEmail(String email);

    //id로 로그인
    Member findById(String id);

    //email로 로그인
    Member findByEmail(String email);

    //
    Member findByPkId(Long pkId);
}
