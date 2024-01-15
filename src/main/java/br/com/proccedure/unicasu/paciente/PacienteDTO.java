package br.com.proccedure.unicasu.paciente;

import br.com.proccedure.unicasu.paciente.enums.AnalisePaciente;
import br.com.proccedure.unicasu.paciente.enums.TipoAnalise;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import br.com.proccedure.unicasu.ubs.UbsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PacienteDTO {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String cartaoSus;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private Integer codigo;
    private Integer numero;
    private TipoArbovirose tipoArbovirose;
    private TipoAnalise tipoAnalise;
    private AnalisePaciente analisePaciente;
    private String observacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime horaCriacao;
    private Boolean obito;
    private Boolean obitoSuspeito;
    private Boolean internado;
    private UbsDTO ubs;
    private Boolean importado;


    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.ubs = paciente.getUbs() != null ? UbsDTO.ofOne(paciente.getUbs()) : null;
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.cpf = paciente.getCpf();
        this.cartaoSus = paciente.getCartaoSus();
        this.logradouro = paciente.getLogradouro();
        this.bairro = paciente.getBairro();
        this.cidade = paciente.getCidade();
        this.cep = paciente.getCep();
        this.estado = paciente.getEstado();
        this.codigo = paciente.getCodigo();
        this.numero = paciente.getNumero();
        this.tipoArbovirose = paciente.getTipoArbovirose();
        this.tipoAnalise = paciente.getTipoAnalise();
        this.analisePaciente = paciente.getAnalisePaciente();
        this.observacao = paciente.getObservacao();
        this.dataCriacao = paciente.getDataCriacao();
        this.horaCriacao = paciente.getHoraCriacao();
        this.obito = paciente.getObito();
        this.obitoSuspeito = paciente.getObitoSuspeito();
        this.internado = paciente.getInternado();
        importado = paciente.getImportado();
    }

    public static PacienteDTO ofOne(Paciente paciente) {
        return new PacienteDTO(paciente);
    }

    public static List<PacienteDTO> ofList(List<Paciente> pacientes) {
        return pacientes.stream().map(PacienteDTO::new).collect(Collectors.toList());
    }

}
