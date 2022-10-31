package coffee.userservice.repository;

import coffee.userservice.Entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements UserRepositoryImpl{

    @Override
    public Member findById() {
        return null;
    }

    @Override
    public Member findByEmail() {
        return null;
    }

}
