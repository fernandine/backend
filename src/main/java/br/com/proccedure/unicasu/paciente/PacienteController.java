package br.com.proccedure.unicasu.paciente;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{pacienteId}")
    public PacienteDTO get(@PathVariable("pacienteId") long pacienteId) {
        try {
            Paciente paciente = pacienteService.findByIdOrThrow(pacienteId);
            return PacienteDTO.ofOne(paciente);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @GetMapping
    public List<PacienteDTO> list() {
        List<Paciente> pacientes = pacienteService.findAll();
        return PacienteDTO.ofList(pacientes);
    }

    @GetMapping("/filter")
    public List<PacienteDTO> filter(@ModelAttribute PacienteFilterParams params) {
        List<Paciente> pacientes = pacienteService.filter(params);
        return PacienteDTO.ofList(pacientes);
    }

    @GetMapping("/filter/ubs")
    public List<PacienteDTO> filterUsuarioUbs(@RequestParam Long ubsId) {
        List<Paciente> pacientes = pacienteService.filterByUsuarioUbs(ubsId);
        return PacienteDTO.ofList(pacientes);
    }

    @GetMapping("/filter/cidade")
    public List<PacienteDTO> filterByCidade(@ModelAttribute PacienteFilterCidadeParams params) {
        List<Paciente> pacientes = pacienteService.filterByCidade(params);
        return PacienteDTO.ofList(pacientes);
    }

    @GetMapping("/filter-adm-estadual")
    public List<PacienteDTO> filterAdmEstadual(@ModelAttribute PacienteAdmEstadualFilterParams params) {
        List<Paciente> pacientes = pacienteService.filterByAdmEstadual(params);
        return PacienteDTO.ofList(pacientes);
    }

    @PostMapping
    public PacienteDTO create(@RequestBody PacienteParams params) {
        Paciente paciente = pacienteService.create(params);
        return PacienteDTO.ofOne(paciente);
    }

    @PutMapping("/{pacienteId}")
    public PacienteDTO update(@PathVariable("pacienteId") long pacienteId, @RequestBody PacienteParams params) {
        try {
            Paciente paciente = pacienteService.update(pacienteId, params);
            return PacienteDTO.ofOne(paciente);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @DeleteMapping("/{pacienteId}")
    public void delete(@PathVariable("pacienteId") long pacienteId) {
        try {
            pacienteService.delete(pacienteId);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

}