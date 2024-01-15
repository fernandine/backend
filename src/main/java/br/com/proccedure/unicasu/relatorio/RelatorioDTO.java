package br.com.proccedure.unicasu.relatorio;

import br.com.proccedure.unicasu.indice.RiscoTransmissao;
import br.com.proccedure.unicasu.usuario.UsuarioDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class RelatorioDTO {

  private final Long id;
  private IndiceInfestacaoPredial indiceInfestacaoPredial;
  private final RiscoTransmissao riscoTransmissao;
  private final Double indiceBreteau;
  private final Integer notificados;
  private final Integer casosPositivos;
  private final Integer casosSuspeitos;
  private final Integer internados;
  private final Integer obitos;
  private final Integer obitosSuspeitos;
  private final String cidade;
  private final String uf;
  private final UsuarioDTO usuario;
  private final LocalDate dataCriacao;

  public RelatorioDTO(Relatorio relatorio) {
    this.id = relatorio.getId();
    this.indiceInfestacaoPredial = relatorio.getIndiceInfestacaoPredial();
    this.riscoTransmissao = relatorio.getRiscoTransmissao();
    this.indiceBreteau = relatorio.getIndiceBreteau();
    this.notificados = relatorio.getNotificados();
    this.casosPositivos = relatorio.getCasosPositivos();
    this.casosSuspeitos = relatorio.getCasosSuspeitos();
    this.internados = relatorio.getInternados();
    this.obitos = relatorio.getObitos();
    this.obitosSuspeitos = relatorio.getObitosSuspeitos();
    this.cidade = relatorio.getCidade();
    this.uf = relatorio.getUf();
    this.usuario = UsuarioDTO.ofOne(relatorio.getUsuario());
    this.dataCriacao = relatorio.getDataCriacao();
  }

  public static List<RelatorioDTO> ofList(List<Relatorio> relatorios) {
    return relatorios.stream().map(RelatorioDTO::new).collect(Collectors.toList());
  }

}
