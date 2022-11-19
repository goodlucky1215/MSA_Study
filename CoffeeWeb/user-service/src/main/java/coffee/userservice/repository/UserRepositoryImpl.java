package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;

public interface UserRepositoryImpl {

    //1. id로 로그인
    //2. 별명 변경
    MemberEntity findById(String id);

    //1. email로 로그인
    //2. 별명 변경
    MemberEntity findByEmail(String email);

}
