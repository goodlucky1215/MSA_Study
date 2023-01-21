package fashion.sellerservice.service;

import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerInfoDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    /*
    //회원가입
    boolean joinSeller(SellerJoinDto sellerJoinDto){
        boolean extistIdTF = sellerRepository.existsById(sellerJoinDto.getId());
        if(extistIdTF) throw new JoinException("해당 아이디가 이미 존재합니다.");
        Seller seller = mapper.map(sellerJoinDto, Seller.class);
        seller.changePasswordEncrypt(bCryptPasswordEncoder.encode(seller.getPasswordEncrypt()));
        sellerRepository.save(seller);
        return true;
    }

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

     */
}