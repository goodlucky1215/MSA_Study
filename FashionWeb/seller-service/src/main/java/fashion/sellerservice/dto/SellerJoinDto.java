package fashion.sellerservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode
public class SellerJoinDto {

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 2,max =10,message="상호명은 2자 이상 10자 이하여야 합니다.")
    private String companyName;

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min =4, max = 20, message = "패스워드는 4자 이상 20자 이하여야 합니다")
    private String passwordEncrypt;

}
