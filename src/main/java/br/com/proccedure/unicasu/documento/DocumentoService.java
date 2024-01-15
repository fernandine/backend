package br.com.proccedure.unicasu.documento;


import br.com.proccedure.unicasu.documento.enums.TipoAnalise;
import br.com.proccedure.unicasu.documento.enums.TipoIntervencao;
import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import br.com.proccedure.unicasu.infra.storage.AwsS3Service;
import br.com.proccedure.unicasu.usuario.TipoUsuario;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioRepository;
import br.com.proccedure.unicasu.usuario.UsuarioService;
import com.amazonaws.AmazonClientException;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AwsS3Service s3Service;

    private static final String BUCKET_ADDRESS = "https://unica-sistema.s3.us-east-2.amazonaws.com";

//    @Transactional(readOnly = true)
//    public List<DocumentoDTO> findAll() {
//        List<Documento> list = documentoRepository.findAll();
//        return list.stream().map(DocumentoDTO::new).collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public DocumentoDTO findById(Long id) {
        Optional<Documento> obj = documentoRepository.findById(id);
        Documento entity = obj.orElseThrow(() -> new UnicasuException("Entity not found"));
        return modelMapper.map(entity, DocumentoDTO.class);
    }

    @Transactional(readOnly = true)
    public DocumentoDTO findByAgente(Long id) {
        Optional<Documento> obj = documentoRepository.findById(id);
        Documento entity = obj.orElseThrow(() -> new UnicasuException("Entity not found"));
        Usuario usuario = entity.getUsuario();

        if (usuario != null && usuario.getTipoUsuario() == TipoUsuario.ROLE_AGENTE_ENDEMIAS) {
            DocumentoDTO dto = modelMapper.map(entity, DocumentoDTO.class);
            dto.setAgente(usuario.getNome());
            return dto;
        } else {
            throw new UnicasuException("O usuário é inválido ou não é um agente de endemias");
        }
    }

    public Documento findByIdOrThrow(long documentoId) {
        return documentoRepository.getReferenceById(documentoId);
    }

    @Transactional(readOnly = true)
    public DocumentoPage findAll(
            Integer mes,
            Integer ano,
            String cidade,
            @PositiveOrZero int pageNumber, @Positive @Max(1000) int pageSize) {
        Page<Documento> page = documentoRepository.findAll(mes, ano, cidade, PageRequest.of(pageNumber, pageSize));

        List<DocumentoDTO> dtos = new ArrayList<>();
        for (Documento documento : page.getContent()) {
            Usuario usuario = documento.getUsuario();

            if (usuario.getTipoUsuario() == TipoUsuario.ROLE_AGENTE_ENDEMIAS) {
                DocumentoDTO dto = modelMapper.map(documento, DocumentoDTO.class);
                dto.setAgente(usuario.getNome());
                dtos.add(dto);
            }
        }

        return new DocumentoPage(dtos, page.getTotalElements(), page.getTotalPages());
    }

    public List<Documento> filter(DocumentoFilterParams params) {
        return documentoRepository.filter(params.getNomeProprietario());
    }

    public List<Documento> filterAdmMunicipal(DocumentoAdmMunicipalFilterParams params) {
        return documentoRepository.filterAdmMunicipal(params.getNomeProprietario(), params.getCidade(), params.getEstado());
    }

    public List<Documento> filterAdmEstadual(DocumentoAdmEstadualFilterParams params) {
        return documentoRepository.filterAdmEstadual(params.getNomeProprietario(), params.getCidade(), params.getEstado());
    }

    @Transactional(readOnly = true)
    public List<Documento> findByUsuarioId(Long usuarioId) {
        return documentoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Documento create(DocumentoParams params, MultipartFile fotoDocumento, MultipartFile fotoCriadouro) throws IOException {
        Documento documento = new Documento();

        String randomDocumentoFilename = RandomStringUtils.randomAlphanumeric(16);
        String randomFotoCriadouroFilename = RandomStringUtils.randomAlphanumeric(16);

        String fileFotoCriadouroUrl = "";
        String fileDocumentoUrl = "";


        if (fotoCriadouro != null) {
            fileFotoCriadouroUrl = String.format(BUCKET_ADDRESS + "/%s", randomFotoCriadouroFilename + "." + StringUtils.getFilenameExtension(fotoCriadouro.getOriginalFilename()));
            uploadFileWithRandomName(randomFotoCriadouroFilename, fotoCriadouro);
        }
        if (fotoDocumento != null) {
            fileDocumentoUrl = String.format(BUCKET_ADDRESS + "/%s", randomDocumentoFilename + "." + StringUtils.getFilenameExtension(fotoDocumento.getOriginalFilename()));
            uploadFileWithRandomName(randomDocumentoFilename, fotoDocumento);
        }

        documento.setFoto(fotoDocumento != null ? fileDocumentoUrl : null);
        documento.setFotoCriadouro(fotoCriadouro != null ? fileFotoCriadouroUrl : null);

        documento.setPresencaCriadouros(params.getSpawn());

        documento.setTipoImovel(params.getPropertyType());
        documento.setAcessoImovel(params.getPropertyAccess());
        documento.setPresencaLarva(params.getLarvae());
        documento.setTipoDeposito(params.getDeposits());
        documento.setObservacao(params.getComments());
        documento.setLogradouro(params.getRoad());
        documento.setBairro(params.getNeighborhood());
        documento.setObservacaoCriadouro(params.getDepositsInfo());
        documento.setNumero(params.getNumber());
        documento.setCidade(params.getCity());
        documento.setUf(params.getUf());
        documento.setCep(params.getCep());
        documento.setQuantidade(params.getQuantity());
        documento.setQuadra(params.getQuadra());
        documento.setZona(params.getZona());

        if (params.getUserId() != null) {
            Usuario user = usuarioRepository.getReferenceById(params.getUserId());
            documento.setUsuario(user);
        }

        String tagCode = String.format("%d-%02d%02d%04d%02d%02d",
                params.getId(),
                LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(),
                LocalTime.now().getHour(),
                LocalTime.now().getMinute()
        );
        documento.setTagCode(tagCode);

        documento.setComplemento(params.getComplemento());
        documento.setQuantidadeLarvicida(params.getQuantidadeLarvicida());
        documento.setTratamentoLarvicida(params.getTratamentoLarvicida());
        documento.setColeta(params.getColeta());

        documento.setNomeProprietario(params.getName());

        if (params.getBirthDate() != null && !params.getBirthDate().equals("null")) {
            documento.setDataNascimentoProprietario(LocalDate.parse(params.getBirthDate()));
        }
        documento.setDataCriacao(LocalDateTime.now());
        documento.setHoraCriacao(LocalDateTime.now());
        documento.setTipoIntervencao(TipoIntervencao.PENDENTE);
        documento.setTipoAnalise(TipoAnalise.PENDENTE);

        Usuario usuario = documento.getUsuario();
        if (usuario != null && usuario.getTipoUsuario() == TipoUsuario.ROLE_AGENTE_ENDEMIAS) {
            Integer somaQuantidadeLarvicida = usuario.getSomaQuantidadeLarvicida();
            if (somaQuantidadeLarvicida == null) {
                somaQuantidadeLarvicida = 0;
            }
            if (documento.getQuantidadeLarvicida() != null) {
                usuario.setSomaQuantidadeLarvicida(somaQuantidadeLarvicida + documento.getQuantidadeLarvicida());
            }
        }
        documento = documentoRepository.save(documento);

        return documento;
    }

    public Documento update(long documentoId, DocumentoParams params) {
        Documento documento = findByIdOrThrow(documentoId);
        documento.setFotoCriadouro(String.valueOf(params.getPropertyImage()));
        documento.setFoto(String.valueOf(params.getPropertyImage()));
        documento.setTipoImovel(params.getPropertyType());
        documento.setAcessoImovel(params.getPropertyAccess());
        documento.setObservacao(params.getComments());
        documento.setLogradouro(params.getRoad());
        documento.setBairro(params.getNeighborhood());
        documento.setNumero(params.getNumber());
        documento.setCidade(params.getCity());
        documento.setUf(params.getUf());
        documento.setCep(params.getCep());
        documento.setQuadra(params.getQuadra());
        documento.setZona(params.getZona());
        documento.setNomeProprietario(params.getName());
        documento.setDataNascimentoProprietario(LocalDate.parse(params.getBirthDate()));
        documento.setCpfProprietario(params.getCpf());
        documento.setPresencaCriadouros(params.getSpawn());
        documento.setTipoDeposito(params.getDeposits());
        documento.setFotoCriadouro(String.valueOf(params.getMainImage()));
        documento.setQuantidade(params.getQuantity());
        documento.setPresencaLarva(params.getLarvae());
        documento.setObservacaoCriadouro(params.getDepositsInfo());
        documento.setComplemento(params.getComplemento());
        documento.setQuantidadeLarvicida(params.getQuantidadeLarvicida());
        documento.setTratamentoLarvicida(params.getTratamentoLarvicida());
        documento.setColeta(params.getColeta());
        return documentoRepository.save(documento);
    }

    public Documento handleUpdateOnPainel(long documentoId, DocumentoUpdatePainelParams params) {
        Documento documento = findByIdOrThrow(documentoId);

        documento.setNomeProprietario(params.getNome());
        documento.setDataNascimentoProprietario(params.getDataNascimento());
        documento.setCpfProprietario(params.getCpf());
        documento.setTipoIntervencao(params.getIntervencao());
        documento.setTipoAnalise(params.getAnaliseLaboratorio());
        documento.setTipoArbovirose(params.getTipoArbovirose());
        return documentoRepository.save(documento);
    }

    public void uploadFileWithRandomName(String randomName, MultipartFile file) {
        try {
            s3Service.uploadFile(randomName + "." + StringUtils.getFilenameExtension(file.getOriginalFilename()), file);
        } catch (AmazonClientException aws) {
            throw new UnicasuException("Ocorreu um erro no serviço S3 da AWS!");
        }
    }

    public void delete(long documentoId) {
        Documento documento = findByIdOrThrow(documentoId);
        documentoRepository.delete(documento);
    }

    // FIND ALL BY ESTADO
    public List<DocumentoDTO> findByAllEstado(String uf) {
        List<Documento> documentos = documentoRepository.findByUf(uf);
        return documentos.stream()
                .map(documento -> {
                    return new DocumentoDTO(documento);
                })
                .collect(Collectors.toList());
    }

    // FIND ALL BY CIDADE
    public List<DocumentoDTO> findByAllCidade(String cidade) {
        List<Documento> documentos = documentoRepository.findByCidade(cidade);
        return documentos.stream()
                .map(documento -> {
                    return new DocumentoDTO(documento);
                })
                .collect(Collectors.toList());
    }
}

