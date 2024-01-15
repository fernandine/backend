package br.com.proccedure.unicasu.usuario;

import lombok.Data;

@Data
public class UsuarioAutenticado {

    private Long id;
    private String nome;
    private TipoUsuario tipoUsuario;
    private String email;
    private String cpf;
    private String newToken;

    public UsuarioAutenticado(Usuario usuario, String newToken) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.newToken = newToken;
    }

}
