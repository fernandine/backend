package br.com.proccedure.unicasu.paciente;

import br.com.proccedure.unicasu.paciente.enums.AnalisePaciente;
import br.com.proccedure.unicasu.paciente.enums.TipoAnalise;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import br.com.proccedure.unicasu.ubs.Ubs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

@Entity
@Table(name = "paciente")
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubs_id", nullable = true)
    private Ubs ubs;

    @Column(nullable = true)
    private String nome;

    private Boolean obito;

    private Boolean obitoSuspeito;

    private Boolean internado;

    @Column(name = "data_nascimento", nullable = true)
    private LocalDate dataNascimento;

    @Column(nullable = true)
    private String cpf;

    @Column(name = "cartao_sus", nullable = false)
    private String cartaoSus;

    @Column(nullable = true)
    private String logradouro;

    @Column(nullable = true)
    private String bairro;

    @Column(nullable = true)
    private String cidade;

    @Column(nullable = true)
    private String cep;

    @Column(nullable = true)
    private String estado;

    @Column(nullable = true)
    private Integer codigo;

    @Column(nullable = true)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_arbovirose")
    private TipoArbovirose tipoArbovirose;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_analise", nullable = true)
    private TipoAnalise tipoAnalise;

    @Enumerated(EnumType.STRING)
    @Column(name = "analise_paciente", nullable = true)
    private AnalisePaciente analisePaciente;

    @Column(nullable = true)
    private String observacao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "hora_criacao")
    private LocalDateTime horaCriacao;

    @Column(name = "importado")
    private Boolean importado;

    public Paciente() { }

}
