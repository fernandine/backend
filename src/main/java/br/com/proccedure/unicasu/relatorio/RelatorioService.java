package br.com.proccedure.unicasu.relatorio;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

  @Autowired
  private RelatorioRepository relatorioRepository;

  @Autowired
  private UsuarioService usuarioService;

  public Relatorio findByIdOrThrow(Long id) {
    return relatorioRepository.findById(id)
            .orElseThrow(() -> new UnicasuException("Relatório não encontrado!"));
  }

  public List<RelatorioDTO> findAll(){
    List<Relatorio> relatorios = relatorioRepository.findAll();
    return relatorios.stream()
            .map(relatorio -> {
              return new RelatorioDTO(relatorio);
            })
            .collect(Collectors.toList());
  };

  public List<RelatorioDTO> findByAllEstado(String uf){
    List<Relatorio> relatorios = relatorioRepository.findByUf(uf);
    return relatorios.stream()
            .map(relatorio -> {
              return new RelatorioDTO(relatorio);
            })
            .collect(Collectors.toList());
  };

  public List<RelatorioDTO> findByAllCidade(String cidade){
    List<Relatorio> relatorios = relatorioRepository.findByCidade(cidade);
    return relatorios.stream()
            .map(relatorio -> {
              return new RelatorioDTO(relatorio);
            })
            .collect(Collectors.toList());
  }

  public RelatorioDTO findById(Long id){
    Relatorio relatorio = findByIdOrThrow(id);
    return new RelatorioDTO(relatorio);
  }

  public List<Relatorio> getRelatoriosByDataECidade(LocalDate dataCriacao, String cidade) {
    return relatorioRepository.findByDataAdmMunicipal(dataCriacao, cidade);
  }

  public List<Relatorio> getRelatoriosByDataAdmEstadual(LocalDate dataCriacao, String estado) {
    return relatorioRepository.findByDataAdmEstadual(dataCriacao, estado);
  }

  public List<Relatorio> getRelatoriosByDataAdmNacional(LocalDate dataCriacao) {
    return relatorioRepository.findByDataAdmNacional(dataCriacao);
  }

  public RelatorioDTO create(RelatorioParams params){
    Relatorio relatorio = new Relatorio();
    Usuario usuario = usuarioService.findByIdOrThrow(params.getUsuarioId());

    relatorio.setIndiceInfestacaoPredial(params.getIndiceInfestacaoPredial());
    relatorio.setRiscoTransmissao(params.getRiscoTransmissao());
    relatorio.setIndiceBreteau(params.getIndiceBreteau());
    relatorio.setNotificados(params.getNotificados());
    relatorio.setCasosPositivos(params.getCasosPositivos());
    relatorio.setCasosSuspeitos(params.getCasosSuspeitos());
    relatorio.setInternados(params.getInternados());
    relatorio.setObitos(params.getObitos()); ;
    relatorio.setObitosSuspeitos(params.getObitosSuspeitos());
    relatorio.setCidade(params.getCidade());
    relatorio.setUf(params.getUf());
    relatorio.setUsuario(usuario);
    relatorio.setDataCriacao(LocalDate.now());

    relatorioRepository.save(relatorio);
    return new RelatorioDTO(relatorio);
  };

  public RelatorioDTO update(Long id, Relatorio updatedRelatorio){
    Relatorio relatorio = findByIdOrThrow(id);

    relatorio.setIndiceInfestacaoPredial(updatedRelatorio.getIndiceInfestacaoPredial());
    relatorio.setRiscoTransmissao(updatedRelatorio.getRiscoTransmissao());
    relatorio.setIndiceBreteau(updatedRelatorio.getIndiceBreteau());
    relatorio.setNotificados(updatedRelatorio.getNotificados());
    relatorio.setCasosPositivos(updatedRelatorio.getCasosPositivos());
    relatorio.setCasosSuspeitos(updatedRelatorio.getCasosSuspeitos());
    relatorio.setInternados(updatedRelatorio.getInternados());
    relatorio.setObitos(updatedRelatorio.getObitos()) ;
    relatorio.setObitosSuspeitos(updatedRelatorio.getObitosSuspeitos());
    relatorio.setCidade(updatedRelatorio.getCidade());
    relatorio.setUf(updatedRelatorio.getUf());

    relatorioRepository.save(relatorio);
    return new RelatorioDTO(relatorio);
  }

  public void deleteById(Long id) {
    Relatorio relatorio = findByIdOrThrow(id);
    relatorioRepository.delete(relatorio);
  }

}
