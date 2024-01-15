package br.com.proccedure.unicasu.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ForgotPasswordRequestParams {

    @NotBlank
    private String email;

}
