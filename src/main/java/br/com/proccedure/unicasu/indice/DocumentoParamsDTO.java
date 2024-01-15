package br.com.proccedure.unicasu.indice;

import br.com.proccedure.unicasu.documento.Documento;
import br.com.proccedure.unicasu.documento.enums.TipoDeposito;
import br.com.proccedure.unicasu.documento.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoParamsDTO implements Serializable {

    private Long id;
    private Integer zona;
    private String quadra;
    private int qtdA1;
    private int qtdA2;
    private int qtdB;
    private int qtdC;
    private int qtdD1;
    private int qtdD2;
    private int qtdE;
    private int qtdOutroTpDeposito;
    private int qtdTspl;
    private int qtdTisa;
    private int qtdTvz;
    private int qtdTcpl;
    private int qtdTerrenoBaldio;
    private int qtdOutrTpImovel;
    private Double breteau;
    private Double ifp;
    private RiscoTransmissao risco;
    private LocalDateTime dataCriacao;

    //    private Map<RiscoTransmissao, Integer> zonaUnica = new HashMap<>();
//    private Map<String, Integer> visitasPorQuadra = new HashMap<>();
//    private Map<TipoDeposito, Integer> somaLarvasPorDeposito = new HashMap<>();
//    private Boolean larvae;
//    private Integer quantity;
//    private List<TipoDeposito> deposits;
//    private Boolean propertyAccess;

    //    private Map<TipoDeposito, Integer> larvasPorDeposito = new HashMap<>();


//    private int somaQuadra;
//    private int tspl;
//    private int tisa;
//    private int tvz;
//    private int tcpl;
//    private Double breteau;
//    private Double ifp;

    //    public void adicionarSomaLarvasPorDeposito(TipoDeposito tipoDeposito, int quantidade) {
//        somaLarvasPorDeposito.merge(tipoDeposito, quantidade, Integer::sum);
//    }
//    public void adicionarVisitaQuadra(String quadra) {
//        visitasPorQuadra.merge(quadra, 1, Integer::sum);
//    }
//
//
//
//
//    public void incrementQuadra() {
//        this.somaQuadra++;
//    }
    public static double calculateBreteau(Documento doc, DocumentoParamsDTO dto) {
        if (doc.getPresencaLarva()) {
            return doc.getAcessoImovel() ? ((double) dto.getQtdTvz()) / dto.getQtdTcpl() : 0.0;
        } else {
            return 0.0;
        }
    }
    //    public void calculateIFP() {
//        if (larvae) {
//            ifp = ((double) tcpl / tvz) * 100;
//        } else {
//            ifp = 0.0;
//        }
//    }
//
    public static RiscoTransmissao calcularRiscoSetorial(Documento doc, DocumentoParamsDTO dto) {
        if (doc.getQuantidade() > 0) {
            double casesPerPatient = (double) dto.getQtdTcpl() / doc.getQuantidade();

            if (casesPerPatient < 30.0) {
                return RiscoTransmissao.BAIXO;
            } else if (casesPerPatient >= 1.0 && casesPerPatient <= 70.0) {
                return RiscoTransmissao.MEDIO;
            } else {
                return RiscoTransmissao.ALTO;
            }
        } else {
            return RiscoTransmissao.BAIXO;
        }
    }

    public static void populaTipoDeposito(DocumentoParamsDTO dto, TipoDeposito tpDeposito ){
        switch (tpDeposito){
            case A1:
                dto.setQtdA1(dto.getQtdA1()+1);
                break;
            case A2:
                dto.setQtdA2(dto.getQtdA2()+1);
                break;
            case B:
                dto.setQtdB(dto.getQtdB()+1);
                break;
            case C:
                dto.setQtdC(dto.getQtdC()+1);
                break;
            case D1:
                dto.setQtdD1(dto.getQtdD1()+1);
                break;
            case D2:
                dto.setQtdD2(dto.getQtdD2()+1);
                break;
            case E:
                dto.setQtdE(dto.getQtdE()+1);
                break;
            case OUTRO:
                dto.setQtdOutroTpDeposito(dto.getQtdOutroTpDeposito()+1);
                break;
        }
    }

    public static void incrementQtdTSPL(DocumentoParamsDTO dto, Boolean presencaLarva) {
        if (!presencaLarva) {
            dto.setQtdTspl(dto.getQtdTspl() + 1);
        }
    }

    public static void incrementQtdTISA(DocumentoParamsDTO dto, Boolean acessoImovel) {
        if(!acessoImovel) {
            dto.setQtdTisa(dto.getQtdTisa() + 1);
        }
    }

    public static void incrementQtdTVZ(DocumentoParamsDTO dto) {
        dto.setQtdTvz(dto.getQtdTvz()+1);
    }

    public static void incrementQtdTCPL(DocumentoParamsDTO dto, Boolean presencaLarva) {
        if (presencaLarva) {
            dto.setQtdTcpl(dto.getQtdTcpl() + 1);
        }
    }

    public static void incrementQtdTerrenoBaldio(DocumentoParamsDTO dto, TipoImovel tpImovel) {
        if (tpImovel.equals(TipoImovel.TERRENO_BALDIO)) {
            dto.setQtdTerrenoBaldio(dto.getQtdTerrenoBaldio() + 1);
        }
    }

    public static void incrementQtdOutroTpImovel(DocumentoParamsDTO dto, TipoImovel tpImovel) {
        if (!tpImovel.equals(TipoImovel.TERRENO_BALDIO)) {
            dto.setQtdOutrTpImovel(dto.getQtdOutrTpImovel() + 1);
        }
    }
}