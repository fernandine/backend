package br.com.proccedure.unicasu.ubs;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ubs")
public class UbsController {

    @Autowired
    private UbsService ubsService;

    @GetMapping("/{ubsId}")
    public UbsDTO get(@PathVariable("ubsId") long ubsId) {
        try {
            Ubs ubs = ubsService.findByIdOrThrow(ubsId);
            return UbsDTO.ofOne(ubs);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @GetMapping
    public List<UbsDTO> list() {
        List<Ubs> ubss = ubsService.findAll();
        return UbsDTO.ofList(ubss);
    }

    @GetMapping("/getByFilter")
    public UbsDTO filterUnique(@ModelAttribute UbsFilterParams params) {
        Ubs ubs = ubsService.filterUnique(params);
        return UbsDTO.ofOne(ubs);
    }

    @GetMapping("/filter")
    public List<UbsDTO> filter(@ModelAttribute UbsFilterParams params) {
        List<Ubs> ubss = ubsService.filter(params);
        return UbsDTO.ofList(ubss);
    }

    @GetMapping("/listByMunicipio")
    public List<UbsDTO> listAllUbs(@ModelAttribute UbsAdmMunicipalFilterParams params) {
        List<Ubs> ubss = ubsService.findAllUbsByCidade(params);
        return UbsDTO.ofList(ubss);
    }

    @GetMapping("/listByEstado")
    public List<UbsDTO> listAllUbsEstadual(@ModelAttribute UbsAdmEstadualFilterParams params) {
        List<Ubs> ubss = ubsService.findAllUbsByEstado(params);
        return UbsDTO.ofList(ubss);
    }

    @PostMapping
    public UbsDTO create(@RequestBody Ubs user) {
        Ubs ubs = ubsService.create(user);
        return UbsDTO.ofOne(ubs);
    }

    @PutMapping("/{ubsId}")
    public UbsDTO update(@PathVariable("ubsId") long ubsId, @RequestBody Ubs user) {
        try {
            Ubs ubs = ubsService.update(ubsId, user);
            return UbsDTO.ofOne(ubs);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @DeleteMapping("/{ubsId}")
    public void delete(@PathVariable("ubsId") long ubsId) {
        try {
            ubsService.delete(ubsId);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

}