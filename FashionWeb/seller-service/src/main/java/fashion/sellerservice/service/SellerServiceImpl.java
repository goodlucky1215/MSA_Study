package fashion.sellerservice.service;

import fashion.sellerservice.dto.*;
import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Orderitem;
import fashion.sellerservice.entity.Seller;
import fashion.sellerservice.exception.JoinException;
import fashion.sellerservice.repository.ItemRepository;
import fashion.sellerservice.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService{

    final private SellerRepository sellerRepository;

    final private ItemRepository itemRepository;

    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    final private ModelMapper mapper;

    @Override
    public boolean joinSeller(SellerJoinDto sellerJoinDto) {
        boolean extistIdTF = sellerRepository.existsById(sellerJoinDto.getId());
        if(extistIdTF) throw new JoinException("해당 아이디가 이미 존재합니다.");
        Seller seller = mapper.map(sellerJoinDto, Seller.class);
        seller.changePasswordEncrypt(bCryptPasswordEncoder.encode(seller.getPasswordEncrypt()));
        sellerRepository.save(seller);
        return true;
    }

    @Override
    public SellerInfoDto getSellerInfo(String id) {
        Seller sellerInfoEntity = sellerRepository.findById(id);
        return mapper.map(sellerInfoEntity,SellerInfoDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Seller sellerInfoEntity = sellerRepository.findById(id);
        if(sellerInfoEntity==null) throw new AuthenticationServiceException("회원 정보를 확인해주세요..");
        return new User(sellerInfoEntity.getId(), sellerInfoEntity.getPasswordEncrypt(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public SellerInfoDto getSellerInfo(Long id) {
        Seller sellerInfoEntity = sellerRepository.findBySellerId(id);
        return mapper.map(sellerInfoEntity,SellerInfoDto.class);
    }

    @Override
    public List<OrderDetailsDto> checkOrderDetails(Long sellerId) {
        return itemRepository.checkOrderDetails(sellerId);
    }

    @Override
    public void changeMemberOrderitemStatus(Long orderitemId) {
        Orderitem orderitem = itemRepository.findByOrderitemId(orderitemId);
        orderitem.readyOrderStatus();
    }

    @Override
    public void saveItem(Long sellerId, ItemRegisterDto itemRegisterDto) {
        Item item = mapper.map(itemRegisterDto, Item.class);
        item.setSeller(sellerRepository.findBySellerId(sellerId));
        itemRepository.save(item);
    }

    @Override
    public List<SellerItemListDto> getSellerItems(Long sellerId) {
        List<Item> items = itemRepository.findBySellerId(sellerId);
        return items.stream().map(item ->
            mapper.map(item,SellerItemListDto.class)
        ).collect(Collectors.toList());
    }

}
