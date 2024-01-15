package br.com.proccedure.unicasu.paciente;

import java.time.LocalDate;

import br.com.proccedure.unicasu.paciente.enums.AnalisePaciente;
import br.com.proccedure.unicasu.paciente.enums.TipoAnalise;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteParams {

    private Long id;
    private Long ubsId;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String cartaoSus;
    private String logradouro;
    private Boolean obito;
    private Boolean obitoSuspeito;
    private Boolean internado;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private Integer numero;
    private TipoArbovirose tipoArbovirose;
    private TipoAnalise tipoAnalise;
    private AnalisePaciente analisePaciente;
    private String observacao;
    private Boolean importado;
}
