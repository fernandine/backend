package br.com.proccedure.unicasu.ubs;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UbsDTO implements Serializable {

    private final Long id;
    private final String nome;
    private final String cep;
    private final String bairro;
    private final String logradouro;
    private final Integer numero;
    private final String cnes;
    private final String cnpj;
    private final String estado;
    private final String municipio;
    private final Boolean ativo;
    private final LocalDate dataCriacao;
    private final LocalTime horaCriacao;

    public UbsDTO(Ubs ubs) {
        this.id = ubs.getId();
        this.nome = ubs.getNome();
        this.cnes = ubs.getCnes();
        this.cnpj = ubs.getCnpj();
        this.estado = ubs.getEstado();
        this.municipio = ubs.getMunicipio();
        this.cep = ubs.getCep();
        this.bairro = ubs.getBairro();
        this.logradouro = ubs.getLogradouro();
        this.numero = ubs.getNumero();
        this.ativo = ubs.getAtivo();
        this.dataCriacao = ubs.getDataCriacao();
        this.horaCriacao = ubs.getHoraCriacao();
    }

    public static UbsDTO ofOne(Ubs ubs) {
        return new UbsDTO(ubs);
    }

    public static List<UbsDTO> ofList(List<Ubs> ubs) {
        return ubs.stream().map(UbsDTO::new).collect(Collectors.toList());
    }

}
