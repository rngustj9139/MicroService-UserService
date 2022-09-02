package koo.MicroServiceUserService.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email can not be null")
    @Size(min = 2, message = "Email can not be less then two characters")
    @Email
    private String email;

    @NotNull(message = "Name can not be null")
    @Size(min = 2, message = "Name can not be less then two characters")
    private String name;

    @NotNull(message = "Password can not be null")
    @Size(min = 8, message = "Password can not be less then two characters")
    private String pwd;

}
