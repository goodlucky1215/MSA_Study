package fashion.sellerservice.repository;

import fashion.sellerservice.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SellerRepositoryImplTest {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    EntityManager em;

    @DisplayName("판매자 정보가 없는 경우_false")
    @Test
    public void not_seller_info_false(){

        //when
        boolean result = sellerRepository.existsById("seller1");

        //then
        assertEquals(false,result);
    }

    @DisplayName("판매자 정보가 있는 경우_true")
    @Test
    public void seller_info_true(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        boolean result = sellerRepository.existsById("seller1");

        //then
        assertEquals(true,result);
    }

    @DisplayName("판매자 가입_성공")
    @Test
    public void seller_join_success(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();

        //when
        sellerRepository.save(seller);
        Seller result = em.find(Seller.class,seller.getSellerId());

        //then
        assertEquals("원숭이",result.getCompanyName());
        assertEquals("seller1",result.getId());
    }

    @DisplayName("판매자 로그인을 위한 판매자 정보_성공")
    @Test
    public void seller_login_info_success(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        Seller result = sellerRepository.findById("seller1");

        //then
        assertEquals("원숭이",result.getCompanyName());
        assertEquals("seller1",result.getId());
        assertEquals("1234",result.getPasswordEncrypt());
    }

}