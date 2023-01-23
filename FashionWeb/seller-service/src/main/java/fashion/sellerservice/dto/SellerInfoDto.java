package fashion.sellerservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@EqualsAndHashCode
public class SellerInfoDto {

    private Long sellerId;

    private String id;

    private String companyName;
}
