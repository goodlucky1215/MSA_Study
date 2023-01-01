package fashion.orderservice.dto;



import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    @NotNull
    private Long pkId;

    private List<OrderitemDto> orderItems = new ArrayList<>();

}
