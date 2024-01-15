package br.com.proccedure.unicasu.documento;

import br.com.proccedure.unicasu.documento.enums.TipoDeposito;
import br.com.proccedure.unicasu.documento.enums.TipoImovel;
import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

//    @GetMapping("/{documentoId}")
//    public DocumentoDTO get(@PathVariable("documentoId") long documentoId) {
//        try {
//            Documento documento = documentoService.findByIdOrThrow(documentoId);
//            return DocumentoDTO.ofOne(documento);
//        } catch (UnicasuException ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
//        }
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DocumentoDTO> findById(@PathVariable Long id) {
        DocumentoDTO dto = documentoService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/agente/{id}")
    public ResponseEntity<DocumentoDTO> findByAgente(@PathVariable Long id) {
        DocumentoDTO dto = documentoService.findByAgente(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/painel/{documentoId}")
    public DocumentoDTO update(@PathVariable("documentoId") long documentoId, @RequestBody DocumentoUpdatePainelParams params) {
        try {
            Documento documento = documentoService.handleUpdateOnPainel(documentoId, params);
            return DocumentoDTO.ofOne(documento);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @GetMapping("/{mes}/{ano}/{cidade}")
    public DocumentoPage findAll(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @PathVariable String cidade,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "1000") int pageSize) {
        return documentoService.findAll(mes, ano, cidade, pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public List<DocumentoDTO> filter(@ModelAttribute DocumentoFilterParams params) {
        List<Documento> documentos = documentoService.filter(params);
        return DocumentoDTO.ofList(documentos);
    }

    @GetMapping("/filter-adm-municipal")
    public List<DocumentoDTO> filterAdmMunicipal(@ModelAttribute DocumentoAdmMunicipalFilterParams params) {
        List<Documento> documentos = documentoService.filterAdmMunicipal(params);
        return DocumentoDTO.ofList(documentos);
    }

    @GetMapping("/filter-adm-estadual")
    public List<DocumentoDTO> filterAdmEstadual(@ModelAttribute DocumentoAdmEstadualFilterParams params) {
        List<Documento> documentos = documentoService.filterAdmEstadual(params);
        return DocumentoDTO.ofList(documentos);
    }

    @GetMapping("/usuario/{id}")
    public List<DocumentoDTO> listByUsuario(@PathVariable("id") Long usuarioId) {
        List<Documento> documentos = documentoService.findByUsuarioId(usuarioId);
        return DocumentoDTO.ofList(documentos);
    }

    @PreAuthorize("hasRole('ROLE_AGENTE_ENDEMIAS')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<DocumentoDTO> create(@ModelAttribute DocumentoParams params,
                                               @RequestPart(value = "propertyImage", required = false) MultipartFile fotoDocumento,
                                               @Nullable @RequestPart(value = "mainImage", required = false) MultipartFile fotoCriadouro) throws IOException {

        DocumentoDTO newDto = DocumentoDTO.ofOne(documentoService.create(params, fotoDocumento, fotoCriadouro));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{documentoId}")
    public DocumentoDTO update(@PathVariable("documentoId") long documentoId, @RequestParam TipoImovel propertyType,
                               @RequestParam Long userId,
                               @RequestParam Boolean propertyAccess,
                               @RequestParam String comments,
                               @RequestParam String road,
                               @RequestParam String neighborhood,
                               @RequestParam Integer number,
                               @RequestParam String city,
                               @RequestParam String uf,
                               @RequestParam String cep,
                               @RequestParam String name,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
                               @RequestParam String cpf,
                               @RequestParam Boolean spawn,
                               @RequestParam TipoDeposito deposits,
                               @RequestParam Integer quantity,
                               @RequestParam Boolean larvae,
                               @RequestParam String depositsInfo,
                               @RequestParam String tagCode,
                               @RequestParam(value = "propertyImage", required = false) MultipartFile fotoDocumento,
                               @RequestParam(value = "mainImage", required = false) MultipartFile fotoCriadouro) {
        try {
            DocumentoParams params = new DocumentoParams(
            );

            Documento documento = documentoService.update(documentoId, params);
            return DocumentoDTO.ofOne(documento);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @DeleteMapping("/{documentoId}")
    public void delete(@PathVariable("documentoId") long documentoId) {
        try {
            documentoService.delete(documentoId);
        } catch (UnicasuException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        }
    }


    // GET ALL RELATORIO PER ESTADO
    @GetMapping("/estado/{uf}")
    public List<DocumentoDTO> findByAllEstado(@PathVariable("uf") String uf) {
        return documentoService.findByAllEstado(uf);
    }


    // GET ALL RELATORIO PER CIDADE
    @GetMapping("/cidade/{cidade}")
    public List<DocumentoDTO> findAllByCidade(@PathVariable("cidade") String cidade) {
        return documentoService.findByAllCidade(cidade);
    }

}



