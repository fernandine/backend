package br.com.proccedure.unicasu.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nome;
    private TipoUsuario tipoUsuario;
    private String email;
    private String cpf;
    private String cnpj;
    private String cep;
    private String bairro;
    private String logradouro;
    private Integer numero;
    private String estado;
    private String municipio;
    private RegiaoGeografica regiaoGeografica;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime horaCriacao;
    private String senha;
    private Integer somaQuantidadeLarvicida;
    private Long ubs;
    private Integer quantidadeDocumentosAFazer;

    public UsuarioDTO(Usuario usuario) {
        if(usuario.getUbs() != null) {
            this.ubs = usuario.getUbs().getId();
        }
        id = usuario.getId();
        nome = usuario.getNome();
        tipoUsuario = usuario.getTipoUsuario();
        email = usuario.getEmail();
        cnpj = usuario.getCnpj();
        cpf = usuario.getCpf();
        somaQuantidadeLarvicida = usuario.getSomaQuantidadeLarvicida();
        estado = usuario.getEstado();
        municipio = usuario.getMunicipio();
        regiaoGeografica = usuario.getRegiaoGeografica();
        cep = usuario.getCep();
        bairro = usuario.getBairro();
        logradouro = usuario.getLogradouro();
        numero = usuario.getNumero();
        ativo = usuario.getAtivo();
        dataCriacao = usuario.getDataCriacao();
        horaCriacao = usuario.getHoraCriacao();
        senha = usuario.getSenha();
        quantidadeDocumentosAFazer = usuario.getQuantidadeDocumentosAFazer();
        //this.ubs = usuario.getUbs() != null ? UbsDTO.ofOne(usuario.getUbs()) : null;
    }

    public static UsuarioDTO ofOne(Usuario usuario) {
        return new UsuarioDTO(usuario);
    }

    public static List<UsuarioDTO> ofList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

}
