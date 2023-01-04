package fashion.userservice.service;

import fashion.userservice.entity.Member;
import fashion.userservice.dto.MemberInfoDto;
import fashion.userservice.dto.MemberJoinDto;
import fashion.userservice.exception.JoinException;
import fashion.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    final private MemberRepository memberRepository;

    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    final private ModelMapper mapper;

    @Override
    public boolean userJoin(MemberJoinDto memberJoinDto) {
        boolean extistIdTF = memberRepository.existsById(memberJoinDto.getId());
        if(extistIdTF) throw new JoinException("해당 아이디가 이미 존재합니다.");
        Member memberEntity = mapper.map(memberJoinDto, Member.class);
        memberEntity.changePasswordEncrypt(bCryptPasswordEncoder.encode(memberEntity.getPassword()));
        memberRepository.save(memberEntity);
        return true;
    }

    @Override
    public MemberInfoDto getUserInfo(String userId) {
        Member memberInfoEntity = memberRepository.findById(userId);
        MemberInfoDto returnMemberInfoDto = mapper.map(memberInfoEntity, MemberInfoDto.class);
        return returnMemberInfoDto;
    }

    @Override
    public MemberInfoDto getUserInfo(Long pkId) {
        Member memberInfoEntity = memberRepository.findByPkId(pkId);
        MemberInfoDto returnMemberInfoDto = mapper.map(memberInfoEntity, MemberInfoDto.class);
        return returnMemberInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member memberInfoEntity = memberRepository.findById(id);
        if(memberInfoEntity==null) throw new AuthenticationServiceException("회원 정보를 확인해주세요..");
        return new org.springframework.security.core.userdetails.User(memberInfoEntity.getId(), memberInfoEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public MemberInfoDto userNicknameChange(MemberInfoDto memberInfoDto,Long pkId) {
        Member memberInfoEntity = memberRepository.findByPkId(pkId);
        memberInfoEntity.changeNickname(memberInfoDto.getNickname());
        MemberInfoDto returnMemberInfoDto = mapper.map(memberInfoEntity, MemberInfoDto.class);
        return returnMemberInfoDto;
    }

}
