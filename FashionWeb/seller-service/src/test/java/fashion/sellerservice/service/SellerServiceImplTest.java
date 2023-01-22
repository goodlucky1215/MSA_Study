package fashion.sellerservice.service;

import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerInfoDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.OrderStatus;
import fashion.sellerservice.entity.Orderitem;
import fashion.sellerservice.entity.Seller;
import fashion.sellerservice.exception.JoinException;
import fashion.sellerservice.repository.ItemRepository;
import fashion.sellerservice.repository.SellerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ModelMapper mapper;

    @DisplayName("회원가입_이미 있는 아이디")
    @Test
    public void join_id_not_unique(){
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("바나나");
        sellerJoinDto.setCompanyName("곰도링회사");
        sellerJoinDto.setPasswordEncrypt("1234");
        when(sellerRepository.existsById(sellerJoinDto.getId())).thenReturn(true);
        assertThrows(JoinException.class, () -> {
            sellerService.joinSeller(sellerJoinDto);
        });
    }

    @DisplayName("회원가입_이미 있는 상호명")
    @Test
    public void join_companyName_not_unique(){
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("바나나");
        sellerJoinDto.setCompanyName("곰도링회사");
        sellerJoinDto.setPasswordEncrypt("1234");
        when(sellerRepository.existsById(sellerJoinDto.getId())).thenReturn(true);
        assertThrows(JoinException.class, () -> {
            sellerService.joinSeller(sellerJoinDto);
        });
    }

    @DisplayName("회원가입_성공")
    @Test
    public void join_success(){
        //given
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("바나나");
        sellerJoinDto.setCompanyName("곰도링회사");
        sellerJoinDto.setPasswordEncrypt("1234");
        when(sellerRepository.existsById(sellerJoinDto.getId())).thenReturn(false);
        Seller seller = Seller.builder()
                .id("바나나")
                .companyName("곰도링회사")
                .passwordEncrypt("1234")
                .build();
        when(mapper.map(any(),any())).thenReturn(seller);
        when(bCryptPasswordEncoder.encode(seller.getPasswordEncrypt())).thenReturn("1234");

        //when
        boolean result = sellerService.joinSeller(sellerJoinDto);

        //then
        assertEquals(true,result);
    }


    @DisplayName("로그인_id 존재 X_실패")
    @Test
    public void sellerIdnotExist(){
        //given
        when(sellerRepository.findById("lucky")).thenReturn(null);

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
        when(sellerRepository.findById("lucky")).thenReturn(seller);

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
        SellerInfoDto sellerInfoDto = new SellerInfoDto();
        sellerInfoDto.setId("lucky");
        sellerInfoDto.setCompanyName("블랙토끼회사");
        Seller seller = Seller.builder()
                .id("lucky")
                .companyName("블랙토끼회사")
                .passwordEncrypt("1234")
                .build();
        when(sellerRepository.findBySellerId(1L)).thenReturn(seller);
        when(mapper.map(any(),any())).thenReturn(sellerInfoDto);

        //when
        SellerInfoDto result = sellerService.getSellerInfo(1L);

        //then
        assertEquals("lucky",result.getId());
        assertEquals("블랙토끼회사",result.getCompanyName());
    }

    @DisplayName("고객 주문 확인_성공")
    @Test
    public void changeMemberOrderitemStatus_success(){
        //given
        Item item = Item.builder()
                .itemName("컵이 그려진 후드티")
                .price(1212121L)
                .quantity(100L)
                .build();
        Orderitem orderitem = Orderitem.createOrderitem(item,10L);
        when(itemRepository.findByOrderitemId(22L)).thenReturn(orderitem);

        //when
        sellerService.changeMemberOrderitemStatus(22L);

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
        Seller seller = Seller.builder()
                .id("구르미")
                .companyName("하늘하늘")
                .passwordEncrypt("1234")
                .build();
        Item item = Item.builder()
                .itemName("컵이 그려진 후드티")
                .price(1212121L)
                .quantity(100L)
                .build();
        when(mapper.map(any(),any())).thenReturn(item);
        when(sellerRepository.findBySellerId(14L)).thenReturn(seller);

        //when
        sellerService.saveItem(14L,itemRegisterDto);

        //then

    }

    @DisplayName("판매자가 판매하는 상품 목록_0개")
    @Test
    public void sellerItemList_empty(){
        //given
        when(itemRepository.findBySellerId(14L)).thenReturn(null);

        //when
        List<SellerItemListDto> result = sellerService.getSellerItems(14L);

        //then
        assertEquals(null, result);

    }

    @DisplayName("판매자가 판매하는 상품 목록")
    @Test
    public void sellerItemList(){
        //given
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder()
                .itemName("컵이 그려진 후드티")
                .price(1212121L)
                .quantity(100L)
                .build();
        Item item2 = Item.builder()
                .itemName("소녀가 그려진 양말")
                .price(1990L)
                .quantity(1212L)
                .build();
        items.add(item1);
        items.add(item2);
        SellerItemListDto sellerItemListDto1 = new SellerItemListDto();
        sellerItemListDto1.setItemName("컵이 그려진 후드티");
        sellerItemListDto1.setPrice(1212121L);
        sellerItemListDto1.setQuantity(100L);
        SellerItemListDto sellerItemListDto2 = new SellerItemListDto();
        sellerItemListDto2.setItemName("소녀가 그려진 양말");
        sellerItemListDto2.setPrice(1990L);
        sellerItemListDto2.setQuantity(1212L);

        when(itemRepository.findBySellerId(14L)).thenReturn(items);
        when(mapper.map(eq(items.get(0)),eq(SellerItemListDto.class))).thenReturn(sellerItemListDto1);
        when(mapper.map(eq(items.get(1)),eq(SellerItemListDto.class))).thenReturn(sellerItemListDto2);

        //when
        List<SellerItemListDto> result = sellerService.getSellerItems(14L);

        //then
        assertEquals("컵이 그려진 후드티", result.get(0).getItemName());
        assertEquals("소녀가 그려진 양말", result.get(1).getItemName());
        assertEquals(1212121L, result.get(0).getPrice());
        assertEquals(1990L, result.get(1).getPrice());
        assertEquals(100L, result.get(0).getQuantity());
        assertEquals(1212L, result.get(1).getQuantity());

    }

    /*

    //고객 주문 목록
    List<Map> getMemberOrderList(Long sellerId);

     */
}