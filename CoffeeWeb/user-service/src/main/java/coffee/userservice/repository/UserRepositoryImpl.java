package coffee.userservice.repository;

import coffee.userservice.Entity.Member;

public interface UserRepositoryImpl {

    public Member findById();

    public Member findByEmail();
}
