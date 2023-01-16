package fashion.sellerservice.repository;

import fashion.sellerservice.entity.Category;
import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Seller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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

    @DisplayName("주문 상품 정보 가져오기_성공")
    @Test
    public void orderitem_info_success(){
        //given



        //when


        //then

    }

    @DisplayName("상품 등록_성공")
    @Test
    public void item_save_success(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);
        Item item = Item.builder()
                .seller(seller)
                .itemName("알록달록한 명품 겟찌 트위드 자켓")
                .price(1989900L)
                .quantity(43L)
                .category(Category.outer)
                .build();

        //when
        sellerRepository.save(item);
        Item result = em.find(Item.class, item.getItemId());

        //then
        assertEquals("알록달록한 명품 겟찌 트위드 자켓",result.getItemName());
        assertEquals(1989900L, result.getPrice());
        assertEquals(43L, result.getQuantity());
        assertEquals(Category.outer,result.getCategory());
        assertEquals("원숭이",result.getSeller().getCompanyName());
    }
}