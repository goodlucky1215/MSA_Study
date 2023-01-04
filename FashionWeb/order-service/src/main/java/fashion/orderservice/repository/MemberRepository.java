package fashion.orderservice.repository;


import fashion.orderservice.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    //회원 정보
    Member findByPkId(Long pkId);
}
