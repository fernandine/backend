package br.com.proccedure.unicasu.documento;

import br.com.proccedure.unicasu.documento.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO {

    private Long id;
    private Long usuarioId;
    private String agente;
    private Integer quantidadeLarvicida;
    private TipoImovel tipoImovel;
    private Boolean acessoImovel;
    private String foto;
    private String observacao;
    private String logradouro;
    private String bairro;
    private Integer numero;
    private String cidade;
    private String uf;
    private String quadra;
    private Integer zona;
    private String cep;
    private String complemento;
    private String nomeProprietario;
    private LocalDate dataNascimentoProprietario;
    private String cpfProprietario;
    private Boolean presencaCriadouros;
    private TipoDeposito tipoDeposito;
    private String fotoCriadouro;
    private Integer quantidade;
    private Boolean presencaLarva;
    private String observacaoCriadouro;
    private LocalDateTime dataCriacao;
    private LocalDateTime horaCriacao;
    private String tagCode;

    private TipoAnalise tipoAnalise;
    private TipoIntervencao tipoIntervencao;
    private Boolean tratamentoLarvicida;
    private Boolean coleta;

    private TipoArbovirose tipoArbovirose;

    public DocumentoDTO(Documento documento) {
        this.id = documento.getId();
        this.usuarioId = documento.getUsuario().getId();
        this.tipoImovel = documento.getTipoImovel();
        this.acessoImovel = documento.getAcessoImovel();
        this.foto = documento.getFoto();
        this.observacao = documento.getObservacao();
        this.logradouro = documento.getLogradouro();
        this.bairro = documento.getBairro();
        this.numero = documento.getNumero();
        this.cidade = documento.getCidade();
        complemento = documento.getComplemento();
        tratamentoLarvicida = documento.getTratamentoLarvicida();
        coleta = documento.getColeta();
        quantidadeLarvicida = documento.getQuantidadeLarvicida();
        this.uf = documento.getUf();
        this.cep = documento.getCep();
        this.nomeProprietario = documento.getNomeProprietario();
        this.dataNascimentoProprietario = documento.getDataNascimentoProprietario();
        this.cpfProprietario = documento.getCpfProprietario();
        this.presencaCriadouros = documento.getPresencaCriadouros();
        this.tipoDeposito = documento.getTipoDeposito();
        this.fotoCriadouro = documento.getFotoCriadouro();
        this.quantidade = documento.getQuantidade();
        this.presencaLarva = documento.getPresencaLarva();
        this.observacaoCriadouro = documento.getObservacaoCriadouro();
        this.dataCriacao = documento.getDataCriacao();
        this.horaCriacao = documento.getHoraCriacao();
        this.tagCode = documento.getTagCode();
        this.tipoAnalise = documento.getTipoAnalise();
        this.tipoIntervencao = documento.getTipoIntervencao();
        this.tipoArbovirose = documento.getTipoArbovirose();
        this.quadra = documento.getQuadra();
        this.zona = documento.getZona();
    }

    public static DocumentoDTO ofOne(Documento documento) {
        return new DocumentoDTO(documento);
    }

    public static List<DocumentoDTO> ofList(List<Documento> documentos) {
        return documentos.stream().map(DocumentoDTO::new).collect(Collectors.toList());
    }
}