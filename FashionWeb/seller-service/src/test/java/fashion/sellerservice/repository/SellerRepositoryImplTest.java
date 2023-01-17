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

    @DisplayName("주문 상품 정보 가져오기_성공")
    //@Test
    @RepeatedTest(100)
    public void orderitem_info_success(){
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
        em.persist(item);
        Member member = Member.builder()
                .id("member1")
                .nickname("칸쵸님")
                .password("1234")
                .build();
        em.persist(member);
        Orderitem orderitem = Orderitem.createOrderitem(item, 4L);
        List<Orderitem> orderitems = new ArrayList(){{ add(orderitem); }};
        Orders order = Orders.createOrder(member, orderitems);
        em.persist(order);


        //when
        Orderitem result = sellerRepository.findByOrderitemId(orderitem.getOrderitemId());

        //then
        assertEquals("알록달록한 명품 겟찌 트위드 자켓", result.getItem().getItemName());
        assertEquals("원숭이", result.getItem().getSeller().getCompanyName());
        assertEquals(39, result.getItem().getQuantity());
        assertEquals("칸쵸님", result.getOrder().getMember().getNickname());
        assertEquals(4L, result.getOrderQuantity());

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

    @DisplayName("판매자가 판매하는 상품 목록_없음")
    @Test
    public void seller_items_empty(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        List<Item> result = sellerRepository.findBySellerId(seller.getSellerId());

        //then
        assertEquals(0,result.size());
    }

    @DisplayName("판매자가 판매하는 상품 목록")
    @Test
    public void seller_items(){
        //given
        Seller seller = Seller.builder()
                .id("seller1")
                .companyName("원숭이")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);
        Item item1 = Item.builder()
                .seller(seller)
                .itemName("알록달록한 명품 겟찌 트위드 자켓")
                .price(1989900L)
                .quantity(43L)
                .category(Category.outer)
                .build();
        em.persist(item1);
        Item item2 = Item.builder()
                .seller(seller)
                .itemName("구름같이 가벼운 스니커즈")
                .price(78900L)
                .quantity(12L)
                .category(Category.shoes)
                .build();
        em.persist(item2);

        //when
        List<Item> result = sellerRepository.findBySellerId(seller.getSellerId());

        //then
        assertEquals(2,result.size());
        assertEquals("알록달록한 명품 겟찌 트위드 자켓",result.get(0).getItemName());
        assertEquals("구름같이 가벼운 스니커즈",result.get(1).getItemName());
        assertEquals(1989900L, result.get(0).getPrice());
        assertEquals(78900, result.get(1).getPrice());
        assertEquals(43L, result.get(0).getQuantity());
        assertEquals(12, result.get(1).getQuantity());
        assertEquals(Category.outer,result.get(0).getCategory());
        assertEquals(Category.shoes,result.get(1).getCategory());
        assertEquals("원숭이",result.get(0).getSeller().getCompanyName());
        assertEquals("원숭이",result.get(1).getSeller().getCompanyName());
    }
}