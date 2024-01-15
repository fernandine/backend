//package br.com.proccedure.unicasu.GEO;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/localizacoes")
//public class LocalizacaoUsuarioController {
//
//    @Autowired
//    private LocalizacaoUsuarioService service;
//
//    @PostMapping
//    public ResponseEntity<LocalizacaoUsuarioDTO> insert(@RequestBody LocalizacaoUsuarioDTO dto) {
//        LocalizacaoUsuarioDTO newDto = service.insert(dto);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newDto.getId()).toUri();
//        return ResponseEntity.created(uri).body(newDto);
//    }
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<LocalizacaoUsuarioDTO> findById(@PathVariable Long id) {
//        LocalizacaoUsuarioDTO dto = service.findById(id);
//        return ResponseEntity.ok().body(dto);
//    }
//}
