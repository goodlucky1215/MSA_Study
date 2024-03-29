package com.example.userservice.service;


import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    UserRepository userRepository; //어딘가에 우리가 클래스로 만들어둬야  @Autowired로 빈생성이 가능
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Environment env;
    //RestTemplate restTemplate;

    OrderServiceClient orderServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null)
            throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            ,BCryptPasswordEncoder bCryptPasswordEncoder
            ,Environment env
            //,RestTemplate restTemplate
            ,OrderServiceClient orderServiceClient){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        //this.restTemplate = restTemplate;
        this.orderServiceClient = orderServiceClient;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity,UserDto.class);
        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException("not found~~~~~~~~~~~~~~~");

        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);

//        List<ResponseOrder> orders = new ArrayList<>();
//        userDto.setOrders(orders);
        /* 첫번째 방법 : rest template를 사용 */
//        String orderUrl = String.format(env.getProperty("order_service.url"),userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET
//                , null, new ParameterizedTypeReference<List<ResponseOrder>>() {
//        });
//        List<ResponseOrder> orderList = orderListResponse.getBody();

        /* 두번째 방법 : feign client 사용 */
        /* feign exception handling -> 즉 오류가 날 때 처리하기 */
        //List<ResponseOrder> orderList = null;
        //try{
        //    orderList = orderServiceClient.getOrders(userId);
        //}catch (FeignException ex){
        //    log.error(ex.getMessage());
        //}

        /* 세번째 방법 : ErrorDecoder */
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);

        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null)
            return "없는 회원입니다.";
        userRepository.delete(userEntity);
        return "삭제되었습니다.";
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity==null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
        return userDto;
    }


}
