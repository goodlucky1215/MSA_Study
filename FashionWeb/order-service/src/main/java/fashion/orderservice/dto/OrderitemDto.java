package fashion.orderservice.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OrderitemDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Long orderQuantity;

}
