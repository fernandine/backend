package br.com.proccedure.unicasu.usuario;

import org.springframework.lang.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UsuarioAdmGeralFilterParams {

    @Nullable
    private String nome;

    @Nullable
    private String estado;

    @Nullable
    private String cidade;

}
