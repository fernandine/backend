package br.com.proccedure.unicasu.auth.security.services;

import br.com.proccedure.unicasu.ubs.Ubs;
import br.com.proccedure.unicasu.ubs.UbsDTO;
import br.com.proccedure.unicasu.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

  private Long id;

  private String cpf;

  private String cnpj;

  private String email;

  private String cidade;

  private String estado;

  private String username;

  private UbsDTO ubs;

  @JsonIgnore
  private String password;

  private GrantedAuthority authority;

  public UserDetailsImpl(Long id, String nome, String cpf, String cnpj, String email, String cidade, String estado, String password, Ubs ubs,
      GrantedAuthority authority) {
    this.id = id;
    this.username = nome;
    this.cpf = cpf;
    this.cnpj = cnpj;
    this.email = email;
    this.cidade = cidade;
    this.estado = estado;
    this.password = password;
    this.authority = authority;
    this.ubs = ubs != null ? UbsDTO.ofOne(ubs) : null;
  }

  public static UserDetailsImpl build(Usuario usuario) {
    GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getTipoUsuario().toString());

    return new UserDetailsImpl(
        usuario.getId(),
        usuario.getNome(),
        usuario.getCpf(),
        usuario.getCnpj(),
        usuario.getEmail(),
        usuario.getMunicipio(),
        usuario.getEstado(),
        usuario.getSenha(),
        usuario.getUbs(),
        authority);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(authority);
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getCidade() {
    return cidade;
  }

  public String getEstado() {
    return estado;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getCpf() {
    return cpf;
  }

  public String getCnpj() {
    return cnpj;
  }

  public UbsDTO getUbs() {
    return ubs;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

}
