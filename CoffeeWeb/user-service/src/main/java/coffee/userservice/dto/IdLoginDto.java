package coffee.userservice.dto;

import com.sun.istack.NotNull;

public class IdLoginDto {

    @NotNull
    //@Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotNull
    //@Size(min = 3,message="패스워드는 3자 이상이어야합니다.")
    private String password;

    public String getId() {
        return id;
    }
}
