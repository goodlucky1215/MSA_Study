package fashion.sellerservice.service;

import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerInfoDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.entity.Item;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface SellerService extends UserDetailsService {

    //회원가입
    boolean joinSeller(SellerJoinDto sellerJoinDto);

    // => 아래 메소드는 spring security에서 로그인시 쓰이는 메소드다. UserDetailsService의 메서드로 일반클래스에서 오버라이드해서 받아온다.
    //public UserDetails loadUserByUsername(String id)

    //로그인 성공후 사용자 정보 가져오기
    SellerInfoDto getSellerInfo(Long id);

    //고객 주문 목록
    List<Map> getMemberOrderList(Long sellerId);

    //고객 주문 확인(ORDER => READY)로 변경
    void changeMemberOrderitemStatus(Long orderitemId);

    //상품 등록
    void saveItem(Long sellerId, ItemRegisterDto itemRegisterDto);

    //판매자가 판매하는 상품 목록
    List<SellerItemListDto> getSellerItems(Long sellerId);
}
