package br.com.proccedure.unicasu.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordRequestParams {
    @NotBlank
    private String token;
    @NotBlank(message = "não deve estar em branco")
    private String newPassword;
}
