package fashion.sellerservice.repository;

import fashion.sellerservice.entity.Seller;

public interface SellerRepository {

    //회원가입
    boolean existsById(String id);
    void save(Seller seller);

    //사용자 정보 가져오기_PK
    Seller findBySellerId(Long sellerId);

    //로그인 성공후 사용자 정보 가져오기_spring-security(id이용)
    Seller findById(String id);
}
