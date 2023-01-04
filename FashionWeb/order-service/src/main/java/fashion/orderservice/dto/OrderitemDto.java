package fashion.orderservice.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderitemDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Long orderQuantity;

}
