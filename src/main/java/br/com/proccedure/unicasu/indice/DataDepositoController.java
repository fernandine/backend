package br.com.proccedure.unicasu.indice;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/indice")
@CrossOrigin(origins = "*")
public class DataDepositoController {

    @Autowired
    private DataDepositoService service;

    @GetMapping("/documento-filtro/{mes}/{ano}/{cidade}")
    public ResponseEntity<List<DocumentoParamsDTO>> getDocumentosEPacientesByAnoEMesECidade(
            @PathVariable Integer mes, @PathVariable Integer ano, @PathVariable String cidade) {
        List<DocumentoParamsDTO> documentos = service.getDocumentosByAnoEMes(mes, ano, cidade);

//        List<PacienteParamsDTO> pacientes = service.getPacientesByAnoEMes(mes, ano, cidade);
//        result.put("pacientes", pacientes);

        return ResponseEntity.ok().body(documentos);
    }

    @GetMapping("/paciente-filtro/{mes}/{ano}/{cidade}")
    public ResponseEntity<List<PacienteParamsDTO>> getPacientesByAnoEMesECidade(
            @RequestParam(required = false) Long ubsId,
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @PathVariable String cidade) {

        List<PacienteParamsDTO> pacientes = service.getPacientesByAnoEMes(ubsId, mes, ano, cidade);
        return ResponseEntity.ok().body(pacientes);
    }

    @GetMapping("/vistorias/{mes}/{ano}/{municipio}")
    public ResponseEntity<List<VistoriasDto>> getVistorias(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @PathVariable String municipio) {
        List<VistoriasDto> list = service.getVistorias(mes, ano,municipio);
        return ResponseEntity.ok().body(list);
    }

// BUSCA PAGINADA ###################
//    @GetMapping("/vistorias/{municipio}")
//    public ResponseEntity<Page<VistoriasDto>> getVistorias(
//            @PathVariable String municipio,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Pageable vistoriasPageable = PageRequest.of(page, size);
//        Page<VistoriasDto> vistoriasPage = service.getVistorias(municipio, vistoriasPageable);
//        return ResponseEntity.ok().body(vistoriasPage);
//    }

}
