package fashion.sellerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fashion.sellerservice.common.ErrorResult;
import fashion.sellerservice.common.ResultCode;
import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.entity.Category;
import fashion.sellerservice.exception.JoinException;
import fashion.sellerservice.service.SellerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SellerController.class)
class SellerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;


    @MockBean
    SellerService sellerService;

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/seller-service/hello"))
                .andDo(print());
    }

    @DisplayName("회원가입_실패_상호명길이짧음")
    @Test
    public void join_CompanyNameIsShort_fail() throws Exception {
        //given
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("핑크공주");
        sellerJoinDto.setCompanyName("캔");
        sellerJoinDto.setPasswordEncrypt("1234");

        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/joinSeller")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sellerJoinDto)));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("code",is(ResultCode.ERROR.getCode())))
                .andExpect(jsonPath("message",is("상호명은 2자 이상 10자 이하여야 합니다.")));
    }

    @DisplayName("회원가입_실패_아이디존재")
    @Test
    public void join_IdExist_fail() throws Exception {
        //given
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("핑크공주");
        sellerJoinDto.setCompanyName("분홍생캔슬");
        sellerJoinDto.setPasswordEncrypt("1234");
        given(sellerService.joinSeller(sellerJoinDto)).willThrow(new JoinException("해당 아이디가 이미 존재합니다."));

        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/joinSeller")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(new ObjectMapper().writeValueAsString(sellerJoinDto)));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("code",is(ResultCode.ERROR.getCode())))
                .andExpect(jsonPath("message",is("해당 아이디가 이미 존재합니다.")));
    }

    @DisplayName("회원가입_성공")
    @Test
    public void join_IdNotExist_success() throws Exception {
        //given
        SellerJoinDto sellerJoinDto = new SellerJoinDto();
        sellerJoinDto.setId("핑크공주");
        sellerJoinDto.setCompanyName("분홍생캔슬");
        sellerJoinDto.setPasswordEncrypt("1234");
        given(sellerService.joinSeller(sellerJoinDto)).willReturn(true);

        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/joinSeller")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sellerJoinDto)));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("code",is(ResultCode.SUCCESS.getCode())))
                .andExpect(jsonPath("message",is(ResultCode.SUCCESS.getMessage())));
    }

    @DisplayName("고객 주문 확인 변경")
    @Test
    public void memberOrderitemStatus() throws Exception {
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/memberOrderitemStatus")
                                                    .header("pkId",11L)
        );

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("data",is("고객 주문 확인 변경")))
                .andExpect(jsonPath("code",is(ResultCode.SUCCESS.getCode())))
                .andExpect(jsonPath("message",is(ResultCode.SUCCESS.getMessage())));
        then(sellerService).should().changeMemberOrderitemStatus(11L);

    }

    @DisplayName("상품 등록_상품명길이짧음")
    @Test
    public void saveItem_itemNameIsShort() throws Exception {
        //given
        ItemRegisterDto itemRegisterDto = new ItemRegisterDto();
        itemRegisterDto.setItemName("신발");
        itemRegisterDto.setPrice(123222L);
        itemRegisterDto.setQuantity(222L);
        itemRegisterDto.setCategory(Category.shoes);

        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/saveItem")
                .header("pkId",11L)
                .content(new ObjectMapper().writeValueAsString(itemRegisterDto))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("code",is(ResultCode.ERROR.getCode())))
                .andExpect(jsonPath("message",is("상품명은 4자 이상 50자 이하여야 합니다.")));

    }

    @DisplayName("상품 등록 완료")
    @Test
    public void saveItem() throws Exception {
        //given
        ItemRegisterDto itemRegisterDto = new ItemRegisterDto();
        itemRegisterDto.setItemName("인형달린 신발");
        itemRegisterDto.setPrice(123222L);
        itemRegisterDto.setQuantity(222L);
        itemRegisterDto.setCategory(Category.shoes);

        //when
        ResultActions resultActions = mockMvc.perform(post("/seller-service/saveItem")
                .header("pkId",11L)
                .content(new ObjectMapper().writeValueAsString(itemRegisterDto))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("data",is("상품 등록 완료")))
                .andExpect(jsonPath("code",is(ResultCode.SUCCESS.getCode())))
                .andExpect(jsonPath("message",is(ResultCode.SUCCESS.getMessage())));
        then(sellerService).should().saveItem(11L,itemRegisterDto);

    }

    @DisplayName("판매자 상품 목록_0")
    @Test
    public void sellerItems_empty() throws Exception {
        //given
        when(sellerService.getSellerItems(11L)).thenReturn(new ArrayList<>());

        //when
        ResultActions resultActions = mockMvc.perform(get("/seller-service/sellerItems")
                .header("pkId",11L)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("data",is(new ArrayList<>())))
                .andExpect(jsonPath("code",is(ResultCode.SUCCESS.getCode())))
                .andExpect(jsonPath("message",is(ResultCode.SUCCESS.getMessage())));
        then(sellerService).should().getSellerItems(11L);

    }

    @DisplayName("판매자 상품 목록_success")
    @Test
    public void sellerItems_success() throws Exception {
        //given
        List<SellerItemListDto> sellerItemListDtos = new ArrayList<>();
        SellerItemListDto sellerItemListDto1 = new SellerItemListDto();
        sellerItemListDto1.setItemId(111L);
        sellerItemListDto1.setItemName("여우 티셔츠");
        sellerItemListDto1.setPrice(121212L);
        sellerItemListDto1.setQuantity(11L);
        SellerItemListDto sellerItemListDto2 = new SellerItemListDto();
        sellerItemListDto2.setItemId(112L);
        sellerItemListDto2.setItemName("너구리 바지");
        sellerItemListDto2.setPrice(98893L);
        sellerItemListDto2.setQuantity(231L);
        sellerItemListDtos.add(sellerItemListDto1);
        sellerItemListDtos.add(sellerItemListDto2);
        when(sellerService.getSellerItems(11L)).thenReturn(sellerItemListDtos);

        //when
        ResultActions resultActions = mockMvc.perform(get("/seller-service/sellerItems")
                .header("pkId",11L)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        String expectItemId = "$..data[?(@.itemId == '%s')]";
        String expectByItemName    = "$..data[?(@.itemName == '%s')]";
        resultActions.andDo(print())
                .andExpect(jsonPath(expectByItemName,"여우 티셔츠").exists())
                .andExpect(jsonPath(expectByItemName,"너구리 바지").exists())
                .andExpect(jsonPath(expectItemId,111L).exists())
                .andExpect(jsonPath(expectItemId,112).exists())
                .andExpect(jsonPath("code",is(ResultCode.SUCCESS.getCode())))
                .andExpect(jsonPath("message",is(ResultCode.SUCCESS.getMessage())));
        then(sellerService).should().getSellerItems(11L);

    }

}