package fashion.sellerservice.service;

import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerInfoDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.entity.*;
import fashion.sellerservice.exception.JoinException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class SellerServiceTest {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    EntityManager em;

    @DisplayName("회원가입_이미 있는 아이디")
    @Test
    public void join_id_not_unique(){
        //given
        Seller seller = Seller.builder()
                .id("haha")
                .passwordEncrypt("1234")
                .companyName("풍수지리")
                .build();
        em.persist(seller);
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("haha");
        sellerJoinDto.setCompanyName("풍수지리");
        sellerJoinDto.setPasswordEncrypt("1234");

        //when, then
        assertThrows(JoinException.class, () -> {
            sellerService.joinSeller(sellerJoinDto);
        });
    }

    @DisplayName("회원가입_성공")
    @Test
    public void join_success(){
        //given
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("haha");
        sellerJoinDto.setCompanyName("풍수지리");
        sellerJoinDto.setPasswordEncrypt("1234");

        //when
        boolean result = sellerService.joinSeller(sellerJoinDto);
        Seller seller = em.createQuery("select s from Seller s where s.id=:id", Seller.class)
                .setParameter("id","haha").getResultList().get(0);

        //then
        assertEquals(true,result);
        assertEquals("haha",seller.getId());
        assertEquals("풍수지리",seller.getCompanyName());
    }

    @DisplayName("로그인_id 존재 X_실패")
    @Test
    public void sellerIdnotExist(){
        //given

        //when
        //then
        assertThrows(AuthenticationServiceException.class,
                () -> {
                    sellerService.loadUserByUsername("lucky");
                });
    }

    @DisplayName("로그인_성공")
    @Test
    public void sellerIdExist(){
        //given
        Seller seller = Seller.builder()
                .id("lucky")
                .companyName("블랙토끼회사")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        UserDetails userDetails = sellerService.loadUserByUsername("lucky");

        //then
        assertEquals("lucky",userDetails.getUsername());
        assertEquals("1234",userDetails.getPassword());
    }

    @DisplayName("로그인 성공 후 정보")
    @Test
    public void sellerInfo(){
        //given
        Seller seller = Seller.builder()
                .id("lucky")
                .companyName("블랙토끼회사")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        SellerInfoDto result = sellerService.getSellerInfo(seller.getSellerId());

        //then
        assertEquals("lucky",result.getId());
        assertEquals("블랙토끼회사",result.getCompanyName());
    }

    @DisplayName("고객 주문 확인_성공")
    @Test
    public void changeMemberOrderitemStatus_success(){
        //given
        Seller seller = Seller.builder()
                .id("lucky")
                .companyName("블랙토끼회사")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);
        Item item = Item.builder()
                .seller(seller)
                .itemName("컵이 그려진 후드티")
                .price(1212121L)
                .quantity(100L)
                .category(Category.top)
                .build();
        em.persist(item);
        Member member = Member.builder()
                .nickname("gaga")
                .id("member")
                .password("1234")
                .build();
        em.persist(member);
        Orderitem orderitem = Orderitem.createOrderitem(item,10L);
        List<Orderitem> orderitems = new ArrayList<>();
        orderitems.add(orderitem);
        Orders orders = Orders.createOrder(member, orderitems);
        em.persist(orders);

        //when
        sellerService.changeMemberOrderitemStatus(orderitem.getOrderitemId());

        //then
        assertEquals(OrderStatus.READY,orderitem.getOrderStatus());
    }

    @DisplayName("상품 등록_성공")
    @Test
    public void itemResister_success(){
        //given
        ItemRegisterDto itemRegisterDto = new ItemRegisterDto();
        itemRegisterDto.setItemName("컵이 그려진 후드티");
        itemRegisterDto.setPrice(1212121L);
        itemRegisterDto.setQuantity(100L);
        itemRegisterDto.setCategory(Category.top);
        Seller seller = Seller.builder()
                .id("구르미")
                .companyName("하늘하늘")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        sellerService.saveItem(seller.getSellerId(),itemRegisterDto);
        Item item = em.createQuery("select i from Item i where i.itemName=:itemName", Item.class)
                .setParameter("itemName","컵이 그려진 후드티").getResultList().get(0);

        //then
        assertEquals("컵이 그려진 후드티",item.getItemName());
        assertEquals(1212121L,item.getPrice());
        assertEquals("구르미",item.getSeller().getId());

    }


    @DisplayName("판매자가 판매하는 상품 목록_0개")
    @Test
    public void sellerItemList_empty(){
        //given
        Seller seller = Seller.builder()
                .id("구르미")
                .companyName("하늘하늘")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);

        //when
        List<SellerItemListDto> result = sellerService.getSellerItems(seller.getSellerId());

        //then
        assertEquals(new ArrayList<>(), result);
        assertEquals(0, result.size());
    }


    @DisplayName("판매자가 판매하는 상품 목록")
    @Test
    public void sellerItemList(){
        //given
        Seller seller = Seller.builder()
                .id("구르미")
                .companyName("하늘하늘")
                .passwordEncrypt("1234")
                .build();
        em.persist(seller);
        Item item1 = Item.builder()
                .itemName("컵이 그려진 후드티")
                .price(1212121L)
                .quantity(100L)
                .category(Category.top)
                .seller(seller)
                .build();
        Item item2 = Item.builder()
                .itemName("소녀가 그려진 양말")
                .price(1990L)
                .quantity(1212L)
                .category(Category.shoes)
                .seller(seller)
                .build();
        em.persist(item1);
        em.persist(item2);


        //when
        List<SellerItemListDto> result = sellerService.getSellerItems(seller.getSellerId());

        //then
        assertEquals(Category.top, result.get(0).getCategory());
        assertEquals(Category.shoes, result.get(1).getCategory());
        assertEquals("컵이 그려진 후드티", result.get(0).getItemName());
        assertEquals("소녀가 그려진 양말", result.get(1).getItemName());
        assertEquals(1212121L, result.get(0).getPrice());
        assertEquals(1990L, result.get(1).getPrice());
        assertEquals(100L, result.get(0).getQuantity());
        assertEquals(1212L, result.get(1).getQuantity());

    }

}