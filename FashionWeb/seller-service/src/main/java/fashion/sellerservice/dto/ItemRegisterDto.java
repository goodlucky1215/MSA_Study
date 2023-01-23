package fashion.sellerservice.dto;

import fashion.sellerservice.entity.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
@EqualsAndHashCode
public class ItemRegisterDto {

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min =4, max = 50, message = "상품명은 4자 이상 50자 이하여야 합니다.")
    private String itemName;

    @NotNull(message = "빈 값이 아니어야합니다.")
    @Max(value = 999999999, message = "9,999,999,999원 이하만 가능합니다.")
    @Min(value = 100, message = "100원 이상만 가능합니다.")
    private Long price;

    @NotNull(message = "빈 값이 아니어야합니다.")
    @Max(value = 999, message = "999개 이하만 가능합니다.")
    @Min(value = 1, message = "1개 이상만 가능합니다.")
    private Long quantity;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

}
