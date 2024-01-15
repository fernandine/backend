package br.com.proccedure.unicasu.mail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewPasswordDto {

    @NotBlank(message = "Campo obrigatório")
    private String token;
    @NotBlank(message = "Campo obrigatório")
    //@Pattern(regexp = "/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$/")
    @Size(min = 6, message = "Deve ter no mínimo 6 caracteres")
    private String password;

    public NewPasswordDto() {}

    public NewPasswordDto(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
