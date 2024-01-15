package br.com.proccedure.unicasu.usuario;

import br.com.proccedure.unicasu.documento.DocumentoDTO;
import br.com.proccedure.unicasu.indice.VistoriasDto;
import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{usuarioId}")
    public UsuarioDTO get(@PathVariable("usuarioId") long usuarioId) {
        try {
            Usuario usuario = usuarioService.findByIdOrThrow(usuarioId);
            return UsuarioDTO.ofOne(usuario);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @GetMapping
    public List<UsuarioDTO> list() {
        List<Usuario> usuarios = usuarioService.findAll();
        return UsuarioDTO.ofList(usuarios);
    }

    @GetMapping(value = "/consumo/{mes}/{ano}/{municipio}")
    public ResponseEntity<List<UsuarioDTO>> findAllConsumos(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @PathVariable String municipio
    ) {
        List<UsuarioDTO> consumos = usuarioService.findAllConsumos(mes, ano, municipio);
        return ResponseEntity.ok().body(consumos);
    }

    @GetMapping("/filter")
    public List<UsuarioDTO> filter(@ModelAttribute UsuarioFilterParams params) {
        List<Usuario> usuarios = usuarioService.filter(params);
        return UsuarioDTO.ofList(usuarios);
    }

    @GetMapping("/filter-adm-geral")
    public List<UsuarioDTO> filterAdmGeral(@ModelAttribute UsuarioAdmGeralFilterParams params) {
        List<Usuario> usuarios = usuarioService.filterAdmGeral(params);
        return UsuarioDTO.ofList(usuarios);
    }

    @GetMapping("/ubs-ae")
    public List<UsuarioDTO> getUbsPlusAgenteEndemia() {
        List<Usuario> usuarios = usuarioService.getUbsPlusAgenteEndemia();
        return UsuarioDTO.ofList(usuarios);
    }

    @GetMapping("/ubs")
    public List<UsuarioDTO> listAllUbs(@ModelAttribute UsuarioUbsFilterParams params) {
        List<Usuario> usuarios = usuarioService.findAllUbsByCidade(params);
        return UsuarioDTO.ofList(usuarios);
    }

    @GetMapping("/ubs-adm-estadual")
    public List<UsuarioDTO> listAllUbsEstadual(@ModelAttribute UsuarioUbsAdmEstadualFilterParams params) {
        List<Usuario> usuarios = usuarioService.findAllUbsByEstado(params);
        return UsuarioDTO.ofList(usuarios);
    }

//    @PostMapping
//    public UsuarioDTO create(@RequestBody Usuario user) {
//        System.out.println(user);
//        Usuario usuario = usuarioService.create(user);
//        return UsuarioDTO.ofOne(usuario);
//    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insert(@RequestBody UsuarioDTO user) {
        UsuarioDTO newDto = UsuarioDTO.ofOne(usuarioService.create(user));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO update(@PathVariable("usuarioId") long usuarioId, @RequestBody Usuario user) {
        try {
            Usuario usuario = usuarioService.update(usuarioId, user);
            return UsuarioDTO.ofOne(usuario);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}")
    public void delete(@PathVariable("usuarioId") long usuarioId) {
        try {
            usuarioService.delete(usuarioId);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

}