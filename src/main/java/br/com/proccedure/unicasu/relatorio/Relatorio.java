package br.com.proccedure.unicasu.relatorio;

import br.com.proccedure.unicasu.indice.RiscoTransmissao;
import br.com.proccedure.unicasu.usuario.Usuario;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relatorio")
@Data
@NoArgsConstructor
public class Relatorio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name="indice_infestacao_predial", nullable = false)
  private IndiceInfestacaoPredial indiceInfestacaoPredial;

  @Enumerated(EnumType.STRING)
  @Column(name="risco_transmissao", nullable = false)
  private RiscoTransmissao riscoTransmissao;

  @Column(name="indice_breteau", nullable = false)
  private Double indiceBreteau;

  @Column(nullable = false)
  private Integer notificados;

  @Column(name="casos_positivos", nullable = false)
  private Integer casosPositivos;

  @Column(name="casos_suspeitos", nullable = false)
  private Integer casosSuspeitos;

  @Column(nullable = false)
  private Integer internados;

  @Column(nullable = false)
  private Integer obitos;

  @Column(name="obitos_suspeitos", nullable = false)
  private Integer obitosSuspeitos;

  @Column(nullable = false)
  private String cidade;

  @Column(nullable = false)
  private String uf;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Column(name="data_criacao")
  private LocalDate dataCriacao;

}
