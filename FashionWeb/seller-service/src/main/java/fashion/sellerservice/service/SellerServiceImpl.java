package fashion.sellerservice.service;

import fashion.sellerservice.dto.SellerInfoDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.entity.Item;
import fashion.sellerservice.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    SellerRepository sellerRepository;

    @Override
    public boolean sellerJoin(SellerJoinDto sellerJoinDto) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public SellerInfoDto getSellerInfo(String id) {
        return null;
    }

    @Override
    public void memberOrderitemStatusChange(Long orderitemId) {

    }

    @Override
    public void save(Item item) {

    }

    @Override
    public List<SellerItemListDto> getSellerItems(Long sellerId) {
        return null;
    }

}
