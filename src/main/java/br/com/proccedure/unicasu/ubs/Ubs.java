package br.com.proccedure.unicasu.ubs;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Entity
@Table(name = "ubs")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ubs implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)  // <-- pode armazenar valor null
    private String cnpj;

    @Column(nullable = true)
    private String nome;

    @Column(nullable = true)
    private String estado;

    @Column(nullable = true)
    private String municipio;

    private String cep;

    private String bairro;

    private String logradouro;

    private Integer numero;

    @Column(nullable = true)
    private String cnes;

    @Column(nullable = true)
    private Boolean ativo;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "hora_criacao")
    private LocalTime horaCriacao;

    public Ubs() {}


}
