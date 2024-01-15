package br.com.proccedure.unicasu.paciente;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import br.com.proccedure.unicasu.ubs.Ubs;
import br.com.proccedure.unicasu.ubs.UbsService;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class

PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UbsService ubsService;

    public Paciente findByIdOrThrow(long pacienteId) {
        return pacienteRepository.findById(pacienteId)
            .orElseThrow(() -> new UnicasuException("Paciente não encontrado!"));
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public List<Paciente> filter(PacienteFilterParams params) {
        List<Paciente> pacientes = pacienteRepository.filter(params.getEstado(), params.getCidade(), params.getTipoArbovirose(), params.getNome());
        if (params.getAscendente() != null && params.getAscendente()) {
            pacientes.sort(Comparator.comparing(Paciente::getNome));
        } else if (params.getAscendente() != null && !params.getAscendente()) {
            pacientes.sort(Comparator.comparing(Paciente::getNome).reversed());
        }
        return pacientes;
    }

    public List<Paciente> filterByUsuarioUbs(Long ubsId) {
        List<Paciente> pacientes = pacienteRepository.findByUbsId(ubsId);
//        if (ascendente) {
//            pacientes.sort(Comparator.comparing(Paciente::getNome));
//        } else if (!ascendente) {
//            pacientes.sort(Comparator.comparing(Paciente::getNome).reversed());
//        }
        return pacientes;
    }

    public List<Paciente> filterByAdmEstadual(PacienteAdmEstadualFilterParams params) {
        List<Paciente> pacientes = pacienteRepository.filterAdmEstadual(params.getTipoArbovirose(), params.getNome(), params.getUbsId(), params.getEstado(), params.getCidade());
        if (params.getAscendente() != null && params.getAscendente()) {
            pacientes.sort(Comparator.comparing(Paciente::getNome));
        } else if (params.getAscendente() != null && !params.getAscendente()) {
            pacientes.sort(Comparator.comparing(Paciente::getNome).reversed());
        }
        return pacientes;
    }

    public List<Paciente> filterByCidade(PacienteFilterCidadeParams params) {
        return pacienteRepository.findAllByCidadeAndEstado(params.getCidade(), params.getEstado());
    }

    public Paciente create(PacienteParams params) {
        Paciente paciente = new Paciente();

        Ubs ubs = null;
        if (params != null && params.getUbsId() != null) {
            ubs = ubsService.findByIdOrThrow(params.getUbsId());
        }
        System.out.println("Ubs Encontrada: " + ubs);
        int codigoAleatorio = new Random().nextInt(900000) + 100000;

        paciente.setUbs(ubs);
        paciente.setNome(params.getNome());
        paciente.setDataNascimento(params.getDataNascimento());
        paciente.setCpf(params.getCpf());
        paciente.setCartaoSus(params.getCartaoSus());
        paciente.setLogradouro(params.getLogradouro());
        paciente.setBairro(params.getBairro());
        paciente.setCidade(params.getCidade());
        paciente.setCep(params.getCep());
        paciente.setImportado(params.getImportado());
        paciente.setObito(params.getObito());
        paciente.setObitoSuspeito(params.getObitoSuspeito());
        paciente.setInternado(params.getInternado());

        paciente.setEstado(params.getEstado());
        paciente.setCodigo(codigoAleatorio);
        paciente.setNumero(params.getNumero());
        paciente.setTipoArbovirose(params.getTipoArbovirose());
        paciente.setTipoAnalise(params.getTipoAnalise());
        paciente.setAnalisePaciente(params.getAnalisePaciente());
        paciente.setObservacao(params.getObservacao());
        paciente.setDataCriacao(LocalDateTime.now());
        paciente.setHoraCriacao(LocalDateTime.now());

        paciente = pacienteRepository.save(paciente);

        return paciente;
    }

    public Paciente update(long pacienteId, PacienteParams params) {
        Paciente paciente = findByIdOrThrow(pacienteId);
        Ubs ubs = ubsService.findByIdOrThrow(params.getUbsId());

        paciente.setUbs(ubs);
        paciente.setNome(params.getNome());
        paciente.setDataNascimento(params.getDataNascimento());
        paciente.setCpf(params.getCpf());
        paciente.setCartaoSus(params.getCartaoSus());
        paciente.setLogradouro(params.getLogradouro());
        paciente.setBairro(params.getBairro());
        paciente.setCidade(params.getCidade());
        paciente.setCep(params.getCep());
        paciente.setEstado(params.getEstado());
        paciente.setNumero(params.getNumero());
        if(params.getObito() !=null) {
            paciente.setObito(params.getObito());
        }
        paciente.setObitoSuspeito(params.getObitoSuspeito());
        paciente.setInternado(params.getInternado());
        paciente.setTipoArbovirose(params.getTipoArbovirose());
        paciente.setTipoAnalise(params.getTipoAnalise());
        paciente.setAnalisePaciente(params.getAnalisePaciente());
        paciente.setObservacao(params.getObservacao());
        paciente.setImportado(params.getImportado());

        return pacienteRepository.save(paciente);
    }

    public void delete(long pacienteId) {
        Paciente paciente = findByIdOrThrow(pacienteId);
        pacienteRepository.delete(paciente);
    }

}
