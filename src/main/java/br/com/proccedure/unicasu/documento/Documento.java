package br.com.proccedure.unicasu.documento;

import br.com.proccedure.unicasu.documento.enums.*;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import br.com.proccedure.unicasu.usuario.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "documento")
@Data
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /*
     * Dados do Imóvel
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoImovel tipoImovel;

    private Boolean acessoImovel;

    @Column(nullable = true)
    private String foto;
    @Column(nullable = true)
    private String fotoCriadouro;

    private String observacao;

    /*
     * Endereço
     */
    private String logradouro;

    private String bairro;

    private Integer numero;

    private String cidade;

    private String uf;

    private String quadra;

    private Integer zona;

    private String cep;

    private String complemento;

    @Column(name = "tratamento_larvicida")
    private Boolean tratamentoLarvicida;

    /*
     * Dados do Proprietário
     */
    @Column(name = "nome_proprietario")
    private String nomeProprietario;
    @Column(name = "data_nascimento_proprietario")
    private LocalDate dataNascimentoProprietario;
    @Column(name = "cpf_proprietario")
    private String cpfProprietario;

    /*
     * Presença de Criadouros
     */
    private Boolean presencaCriadouros;

    private Boolean coleta;

    @Column(name = "quantidade_larvicida")
    private Integer quantidadeLarvicida;

    @Enumerated(EnumType.STRING)
    private TipoDeposito tipoDeposito;

    private Integer quantidade;

    private Boolean presencaLarva;
    private String observacaoCriadouro;
    private LocalDateTime dataCriacao;

    private LocalDateTime horaCriacao;

    private String tagCode;

    //@Enumerated(EnumType.STRING)
    @Column(name = "tipo_analise")
    private TipoAnalise tipoAnalise;

    //@Enumerated(EnumType.STRING)
    @Column(name = "tipo_intervencao")
    private TipoIntervencao tipoIntervencao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_arbovirose")
    private TipoArbovirose tipoArbovirose;
    public Documento() { }

}