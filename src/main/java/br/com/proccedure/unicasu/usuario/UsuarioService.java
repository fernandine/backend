package br.com.proccedure.unicasu.usuario;

import br.com.proccedure.unicasu.documento.Documento;
import br.com.proccedure.unicasu.documento.DocumentoRepository;
import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.proccedure.unicasu.ubs.Ubs;
import br.com.proccedure.unicasu.ubs.UbsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
@Autowired
private DocumentoRepository documentoRepository;
  @Autowired
  private UbsRepository ubsRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public Usuario findByIdOrThrow(long usuarioId) {
    return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UnicasuException("Usuário não encontrado!"));
  }

  public List<Usuario> findAll() {
    return usuarioRepository.findAll();
  }

  public List<Usuario> filter(UsuarioFilterParams params) {
    List<TipoUsuario> tiposUsuario = Arrays.asList(TipoUsuario.ROLE_PROFISSIONAL_UBS, TipoUsuario.ROLE_AGENTE_ENDEMIAS, TipoUsuario.ROLE_ADMINISTRADOR_MUNICIPAL);
    List<Usuario> usuarios = usuarioRepository.filterUbsPlusAgenteEndemia(tiposUsuario, params.getNome(),params.getCidade());
    return usuarios;
  }

  public List<Usuario> filterAdmGeral(UsuarioAdmGeralFilterParams params) {
    List<Usuario> usuarios = usuarioRepository.filterAdmGeral(params.getNome(), params.getEstado(), params.getCidade());
    return usuarios;
  }

  public List<Usuario> findAllUbsByCidade(UsuarioUbsFilterParams params) {
    params.setTipoUsuario(TipoUsuario.ROLE_UBS);
    return usuarioRepository.findAllUbsByCidade(params.getTipoUsuario(), params.getCidade());
  }

  public List<Usuario> findAllUbsByEstado(UsuarioUbsAdmEstadualFilterParams params) {
    params.setTipoUsuario(TipoUsuario.ROLE_UBS);
    return usuarioRepository.findAllUbsByEstado(params.getTipoUsuario(), params.getEstado());
  }

  public List<Usuario> getUbsPlusAgenteEndemia() {
    List<TipoUsuario> tiposUsuario = Arrays.asList(TipoUsuario.ROLE_UBS, TipoUsuario.ROLE_PROFISSIONAL_UBS, TipoUsuario.ROLE_AGENTE_ENDEMIAS);
    return usuarioRepository.getUbsPlusAgenteEndemia(tiposUsuario);
  }

  @Transactional
  public Usuario create(UsuarioDTO user) {
    Usuario usuario = new Usuario();

    usuario.setTipoUsuario(user.getTipoUsuario());

    if (user.getTipoUsuario() == TipoUsuario.ROLE_UBS) {
      usuario.setCnpj(user.getCnpj());
      usuario.setBairro(user.getBairro());
      usuario.setCep(user.getCep());
      usuario.setLogradouro(user.getLogradouro());
      usuario.setNumero(user.getNumero());
    } else {
      usuario.setCpf(user.getCpf());
    }

    System.out.println("o que tem de ubs = " + usuario.getUbs());
    if(user.getUbs() != null ) {
      Ubs ubs = ubsRepository.getReferenceById(user.getUbs());
      usuario.setUbs(ubs);
    }

    usuario.setNome(user.getNome());
    usuario.setEmail(user.getEmail());

    usuario.setSenha(passwordEncoder.encode(user.getSenha()));

    usuario.setEstado(user.getEstado());
    usuario.setMunicipio(user.getMunicipio());
    usuario.setRegiaoGeografica(user.getRegiaoGeografica());
    usuario.setAtivo(user.getAtivo());
    usuario.setDataCriacao(LocalDateTime.now());
    usuario.setHoraCriacao(LocalDateTime.now());
    usuario.setQuantidadeDocumentosAFazer(user.getQuantidadeDocumentosAFazer());
    usuario = usuarioRepository.saveAndFlush(usuario);

    return usuario;
  }

  public Usuario update(long usuarioId, Usuario user) {
    Usuario usuario = findByIdOrThrow(usuarioId);

    usuario.setTipoUsuario(user.getTipoUsuario());

    if (user.getTipoUsuario() == TipoUsuario.ROLE_UBS) {
      usuario.setCnpj(user.getCnpj());
      usuario.setBairro(user.getBairro());
      usuario.setCep(user.getCep());
      usuario.setLogradouro(user.getLogradouro());
      usuario.setNumero(user.getNumero());
    } else {
      usuario.setCpf(user.getCpf());
    }

    usuario.setUbs(user.getUbs());
    usuario.setNome(user.getNome());
    usuario.setEmail(user.getEmail());
    usuario.setSenha(usuario.getSenha());
    usuario.setEstado(user.getEstado());
    usuario.setMunicipio(user.getMunicipio());
    usuario.setQuantidadeDocumentosAFazer(user.getQuantidadeDocumentosAFazer());
    usuario.setRegiaoGeografica(user.getRegiaoGeografica());
    usuario.setAtivo(user.getAtivo());
    usuario.setDataCriacao(LocalDateTime.now());
    usuario.setHoraCriacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

    return usuarioRepository.save(usuario);
  }

  public void delete(long usuarioId) {
    Usuario usuario = findByIdOrThrow(usuarioId);
    usuarioRepository.delete(usuario);
  }

//@Transactional(readOnly = true)
//  public List<UsuarioDTO> findAllConsumos(Integer mes, Integer ano, String municipio) {
//    List<Usuario> usuarios = usuarioRepository.findByMunicipioAndTipoUsuario(municipio, TipoUsuario.ROLE_AGENTE_ENDEMIAS);
//    List<UsuarioDTO> dtos = new ArrayList<>();
//
//    for (Usuario usuario : usuarios) {
//      List<Documento> documentos = documentoRepository.findByUsuarioAndMesAndAno(usuario, mes, ano);
//
//      if (!documentos.isEmpty()) {
//        UsuarioDTO dto = new UsuarioDTO();
//        dto.setNome(usuario.getNome());
//        dto.setSomaQuantidadeLarvicida(usuario.getSomaQuantidadeLarvicida());
//
//        dtos.add(dto);
//      }
//    }
//
//    return dtos;
//  }

  @Transactional(readOnly = true)
  public List<UsuarioDTO> findAllConsumos(Integer mes, Integer ano, String municipio) {
    List<Usuario> usuarios = usuarioRepository.findByMunicipioAndTipoUsuario(municipio, TipoUsuario.ROLE_AGENTE_ENDEMIAS);
    List<UsuarioDTO> dtos = new ArrayList<>();

    for (Usuario usuario : usuarios) {
      Integer quantidadeDocumentos = getQuantidadeDocumentos(usuario, mes, ano);

      if (quantidadeDocumentos != null) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setSomaQuantidadeLarvicida(usuario.getSomaQuantidadeLarvicida());
        dto.setQuantidadeDocumentosAFazer(quantidadeDocumentos);

        dtos.add(dto);
      }
    }

    return dtos;
  }

  private Integer getQuantidadeDocumentos(Usuario usuario, Integer mes, Integer ano) {
    List<Documento> documentos = documentoRepository.findByUsuarioAndMesAndAno(usuario, mes, ano);

    if (!documentos.isEmpty()) {
      return usuario.getQuantidadeDocumentosAFazer();
    }

    return null;
  }



  }

