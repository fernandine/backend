package br.com.proccedure.unicasu.relatorio;

import br.com.proccedure.unicasu.indice.RiscoTransmissao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioParams {

    private Long usuarioId;
    private IndiceInfestacaoPredial indiceInfestacaoPredial;
    private RiscoTransmissao riscoTransmissao;
    private Double indiceBreteau;
    private Integer notificados;
    private Integer casosPositivos;
    private Integer casosSuspeitos;
    private Integer internados;
    private Integer obitos;
    private Integer obitosSuspeitos;
    private String cidade;
    private String uf;

}
