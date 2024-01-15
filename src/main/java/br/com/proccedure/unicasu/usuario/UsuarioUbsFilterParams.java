package br.com.proccedure.unicasu.usuario;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UsuarioUbsFilterParams {
    
    @Nullable
    private TipoUsuario tipoUsuario;

    private String cidade;

}
