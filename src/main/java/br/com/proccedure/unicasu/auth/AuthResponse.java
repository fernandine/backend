package br.com.proccedure.unicasu.auth;

import br.com.proccedure.unicasu.ubs.Ubs;
import br.com.proccedure.unicasu.ubs.UbsDTO;

public class AuthResponse {

    private Long id;
    private String nome;
    private String cpf;
    private String cnpj;
    private String email;
    private String cidade;
    private String estado;
    private String tipoUsuario;
    private UbsDTO ubs;
    private String accessToken;
    private String type = "Bearer";

    public AuthResponse(String accessToken, Long id, String nome, String cpf, String cnpj, String email, String cidade, String estado, String tipoUsuario, UbsDTO ubs) {
      if(tipoUsuario == "ROLE_UBS"){
        this.cnpj = cnpj;
        System.out.println("CNPJ");
      }else{
        this.cpf = cpf;
        System.out.println("CPF");
      }
      this.id = id;
      this.nome = nome;
      this.email = email;
      this.cidade = cidade;
      this.estado = estado;
      this.tipoUsuario = tipoUsuario;
      this.accessToken = accessToken;
      this.ubs = ubs ;
    }

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }

    public String getTokenType() {
      return type;
    }

    public void setTokenType(String tokenType) {
      this.type = tokenType;
    }

    public void setTiposUsuario(String tipoUsuario) {
      this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }
    
    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getCidade() {
      return cidade;
    }

    public void setCidade(String cidade) {
      this.cidade = cidade;
    }

    public String getEstado() {
      return estado;
    }

    public void setEstado(String estado) {
      this.estado = estado;
    }

    public String getCnpj() {
      return cnpj;
    }

    public void setCnpj(String cnpj) {
      this.cnpj = cnpj;
    }

    public String getCpf() {
      return cpf;
    }

    public void setCpf(String cpf) {
      this.cpf = cpf;
    }

    public UbsDTO getUbs() {
      return ubs;
    }

    public void setUbs(Ubs ubs) {
      this.ubs = ubs != null ? UbsDTO.ofOne(ubs) : null;
    }

    public String getTipoUsuario() {
      return tipoUsuario;
    }

}
