package fashion.userservice.service;

import fashion.userservice.entity.Member;
import fashion.userservice.dto.MemberInfoDto;
import fashion.userservice.dto.MemberJoinDto;
import fashion.userservice.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@Mock을 사용_단위테스트를 의미_스프링 컨테이너에 등록하지 않고 가짜 객체 생성
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ModelMapper mapper;


    @DisplayName("id_회원가입_실패_이미존재하는아이디")
    @Test
    public void id_join_fail(){
        //given
        MemberJoinDto userJoinDto1 = new MemberJoinDto();
        userJoinDto1.setId("id1");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        when(memberRepository.existsById("id1")).thenReturn(true);


        //when
        boolean result = userService.userJoin(userJoinDto1);

        //then
        assertEquals(false,result);
    }

    @DisplayName("id_회원가입_성공")
    @Test
    public void id_join_success(){
        //given
        MemberJoinDto userJoinDto1 = new MemberJoinDto();
        userJoinDto1.setId("id1");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("노오란개굴")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(memberRepository.existsById("id1")).thenReturn(false);
        when(mapper.map(any(),any())).thenReturn(memberEntity);


        //when
        boolean result = userService.userJoin(userJoinDto1);

        //then
        assertEquals(true,result);
    }


    @DisplayName("id_로그인_실패_id가 존재하지 않는다")
    @Test
    public void id_login_fail(){
        //given
        when(memberRepository.findById("id1")).thenReturn(null);


        //when
        UserDetails result = userService.loadUserByUsername("id1");

        //then
        assertEquals(null,result);
    }

    @DisplayName("id_로그인_성공")
    @Test
    public void id_login_success(){
        //given
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(memberRepository.findById("id1")).thenReturn(memberEntity);


        //when
        UserDetails result = userService.loadUserByUsername("id1");

        //then
        assertEquals(new org.springframework.security.core.userdetails.User(memberEntity.getId(),memberEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>()),result);
    }

    @DisplayName("닉네임_변경_성공")
    @Test
    public void nickNameChange_success(){
        //given
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(memberRepository.findByPkId(1L)).thenReturn(memberEntity);
        MemberInfoDto memberInfoDto = new MemberInfoDto(1L,"헤라",null);
        when(mapper.map(any(),any())).thenReturn(memberInfoDto);

        //when
        MemberInfoDto returnMemberInfoDto = userService.userNicknameChange(memberInfoDto);

        //then
        assertEquals("헤라", returnMemberInfoDto.getNickname());
        assertEquals(1L, returnMemberInfoDto.getPkId());
    }

    @DisplayName("사용자정보_가져오기_성공")
    @Test
    public void getUserInfo_success(){
        //given
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(memberRepository.findById("id1")).thenReturn(memberEntity);
        MemberInfoDto memberInfoDto = new MemberInfoDto(1L,"아프로디테",null);
        when(mapper.map(any(),any())).thenReturn(memberInfoDto);

        //whem
        MemberInfoDto returnMemberInfoDto = userService.getUserInfo("id1");

        //then
        assertEquals("아프로디테", returnMemberInfoDto.getNickname());
        assertEquals(1L, returnMemberInfoDto.getPkId());
    }
}