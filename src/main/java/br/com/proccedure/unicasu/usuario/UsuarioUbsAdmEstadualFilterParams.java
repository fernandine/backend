package br.com.proccedure.unicasu.usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UsuarioUbsAdmEstadualFilterParams {

    private TipoUsuario tipoUsuario;

    private String estado;

}
