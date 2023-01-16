package fashion.sellerservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("S","success"),
    FAIL("F","fail"),
    ERROR("E","error");

    private String code;
    private String message;
}
