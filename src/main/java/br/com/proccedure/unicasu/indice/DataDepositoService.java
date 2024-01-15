package br.com.proccedure.unicasu.indice;

import br.com.proccedure.unicasu.documento.Documento;
import br.com.proccedure.unicasu.documento.DocumentoDTO;
import br.com.proccedure.unicasu.documento.DocumentoParams;
import br.com.proccedure.unicasu.documento.DocumentoRepository;
import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import br.com.proccedure.unicasu.paciente.enums.AnalisePaciente;
import br.com.proccedure.unicasu.paciente.Paciente;
import br.com.proccedure.unicasu.paciente.PacienteRepository;
import br.com.proccedure.unicasu.ubs.UbsDTO;
import br.com.proccedure.unicasu.usuario.TipoUsuario;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataDepositoService {

    private static final Logger logger = LoggerFactory.getLogger(DataDepositoService.class);

    @Autowired
    private DocumentoRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<VistoriasDto> getVistorias(Integer mes, Integer ano, String municipio) {
        List<Usuario> usuarios = usuarioRepository.findByMunicipioAndTipoUsuario(municipio, TipoUsuario.ROLE_AGENTE_ENDEMIAS);

        return usuarios.stream()
                .map(usuario -> {
                    LocalDateTime dataCriacao = usuario.getDataCriacao();
                    if (dataCriacao != null && dataCriacao.getMonthValue() == mes && dataCriacao.getYear() == ano) {
                        return new VistoriasDto(usuario, calcularVistoriasPendentes(usuario));
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    private Integer calcularVistoriasPendentes(Usuario usuario) {
        List<Documento> documentos = usuario.getDocumentos();
        Integer vistoriasAFazer = usuario.getQuantidadeDocumentosAFazer();

        long vistoriasRealizadas = documentos.stream().filter(this::foiRealizada).count();

        return Math.toIntExact((vistoriasAFazer != null) ? vistoriasAFazer - vistoriasRealizadas : 0);
    }

    private boolean foiRealizada(Documento documento) {
        return documento.getId() != null;
    }


    @Transactional(readOnly = true)
    public List<DocumentoParamsDTO> getDocumentosByAnoEMes(Integer mes, Integer ano, String cidade) {
        List<Documento> documentos = repository.findByCidade(cidade);

        Map<String, DocumentoParamsDTO> documentosPorQuadra = new HashMap<>();


        Map<Pair<Integer, String>, DocumentoParamsDTO> documentosPorZonaQadra = new HashMap<>();

        for (Documento documento : documentos) {


            LocalDateTime dataCriacao = documento.getDataCriacao();
            if (dataCriacao != null) {
                Integer mesCriacao = dataCriacao.getMonthValue();
                Integer anoCriacao = dataCriacao.getYear();

                if (anoCriacao.equals(ano) && mesCriacao.equals(mes)) {
                    //montar o key (pair) com zona e quadra
                    Pair<Integer, String> zonaQuadra = Pair.of(documento.getZona(), documento.getQuadra());


                    //popular o value (dto) utilizando metodo statico com case para incrementar as qtds.
                    DocumentoParamsDTO dto = documentosPorZonaQadra.get(zonaQuadra);
                    if (dto == null) {
                        dto = new DocumentoParamsDTO();
                    }
                    dto.setId(documento.getId());
                    dto.setZona(documento.getZona());
                    dto.setQuadra(documento.getQuadra());
                    DocumentoParamsDTO.populaTipoDeposito(dto, documento.getTipoDeposito());
                    DocumentoParamsDTO.incrementQtdTSPL(dto, documento.getPresencaLarva());
                    DocumentoParamsDTO.incrementQtdTISA(dto, documento.getAcessoImovel());
                    DocumentoParamsDTO.incrementQtdTVZ(dto);
                    DocumentoParamsDTO.incrementQtdTCPL(dto, documento.getPresencaLarva());
                    DocumentoParamsDTO.incrementQtdTerrenoBaldio(dto, documento.getTipoImovel());
                    DocumentoParamsDTO.incrementQtdOutroTpImovel(dto, documento.getTipoImovel());
                    double indiceInfestacao = (Double.valueOf(dto.getQtdTcpl()) / Double.valueOf(dto.getQtdTvz()));
                    dto.setIfp(indiceInfestacao * 100);
                    dto.setRisco(DocumentoParamsDTO.calcularRiscoSetorial(documento, dto));
                    dto.setBreteau(DocumentoParamsDTO.calculateBreteau(documento, dto));
                    dto.setDataCriacao(documento.getDataCriacao());

                    documentosPorZonaQadra.put(zonaQuadra, dto);
                }

            }
        }
        return new ArrayList<>(documentosPorZonaQadra.values());
    }

    @Transactional(readOnly = true)
    public List<PacienteParamsDTO> getPacientesByAnoEMes(Long ubsId, Integer mes, Integer ano, String cidade) {
        List<Paciente> pacientes = pacienteRepository.findByCidade(cidade);
        Map<Long, PacienteParamsDTO> casosPorUbs = new HashMap<>();

        for (Paciente paciente : pacientes) {
            LocalDateTime dataCriacao = paciente.getDataCriacao();

            if (dataCriacao != null) {
                Integer mesCriacao = dataCriacao.getMonthValue();
                Integer anoCriacao = dataCriacao.getYear();

                boolean filterByUbs = ubsId == null || paciente.getUbs().getId().equals(ubsId);

                if (filterByUbs && anoCriacao.equals(ano) && mesCriacao.equals(mes)) {
                    PacienteParamsDTO paramsDTO = casosPorUbs.computeIfAbsent(paciente.getUbs().getId(), k -> new PacienteParamsDTO());

                    paramsDTO.setUbsId(paciente.getUbs().getId());
                    paramsDTO.setUbsNome(paciente.getUbs().getNome());

                    incrementPaciente(paramsDTO, paciente);
                } else {
                    logger.warn("Data de Criação do Paciente é nula para paciente: {}", paciente);
                }
            }
        }

        return new ArrayList<>(casosPorUbs.values());
    }

    private void incrementPaciente(PacienteParamsDTO paramsDTO, Paciente paciente) {
        if (!paciente.getObito()) {
            PacienteParamsDTO.incrementSomaObitos(paramsDTO);
        }
        if (!paciente.getInternado()) {
            PacienteParamsDTO.incrementInternados(paramsDTO);
        }
        if (!paciente.getObitoSuspeito()) {
            PacienteParamsDTO.incrementSomaObitosSuspeitos(paramsDTO);
        }
        if (paciente.getAnalisePaciente() == AnalisePaciente.POSITIVO) {
            PacienteParamsDTO.incrementSomaCasosPositivos(paramsDTO);
        }
        if (paciente.getAnalisePaciente() == AnalisePaciente.EM_ANALISE) {
            PacienteParamsDTO.incrementSomaCasosSuspeitos(paramsDTO);
        }
        if (paciente.getImportado() != null && paciente.getImportado()) {
            PacienteParamsDTO.incrementSomaCasosEstrangeiros(paramsDTO);
        }
        if (paciente.getTipoArbovirose() != null) {
            paramsDTO.incrementTipoArboviroseCount(paciente.getTipoArbovirose());
        }
        PacienteParamsDTO.incrementNotificados(paramsDTO);
    }
}

//            public DocumentoParamsDTO(Documento entity) {
//            id = entity.getId();
//            larvasPorDeposito = new HashMap<>();
//            larvasPorDeposito.put(entity.getTipoDeposito(), entity.getQuantidade());
//            visitasPorQuadra = new HashMap<>();
//            somaLarvasPorDeposito = new HashMap<>();
//            zona = entity.getZona();
//            zonaUnica = new HashMap<>();
//            quadra = entity.getQuadra();
//            larvae = entity.getPresencaLarva();
//            deposits = new ArrayList<>();
//            deposits.add(entity.getTipoDeposito());
//            dataCriacao = entity.getDataCriacao();
//            quantity = entity.getQuantidade();
//            propertyAccess = entity.getAcessoImovel();
//
//            tspl = entity.getPresencaLarva() ? 1 : 0;
//
//            tcpl = entity.getPresencaLarva() ? 1 : 0;
//            tvz = 1;
//            breteau = 0.0;
//            ifp = 0.0;
//            somaQuadra = 1;
//        }


//            LocalDateTime dataCriacao = documento.getDataCriacao();
//            if (dataCriacao != null) {
//                Integer mesCriacao = dataCriacao.getMonthValue();
//                Integer anoCriacao = dataCriacao.getYear();
//
//                if (anoCriacao.equals(ano) && mesCriacao.equals(mes)) {
//                    String chave = documento.getZona() + "-" + documento.getQuadra();
//                    DocumentoParamsDTO paramsDTO = documentosPorQuadra.get(chave);
//                    documentosPorQuadra.put(chave, paramsDTO);
//
//                    paramsDTO.setQuantity(paramsDTO.getQuantity() + documento.getQuantidade());
//
//                    if (documento.getPresencaLarva()) {
//                        paramsDTO.incrementTCPL();
//                        paramsDTO.calculateIFP();
//                    } else {
//                        paramsDTO.incrementTSPL();
//                    }
//
//                    if (!documento.getAcessoImovel()) {
//                        paramsDTO.incrementTISA();
//                    }
//
////                      paramsDTO.incrementQuadra();
//                    paramsDTO. adicionarVisitaQuadra(documento.getQuadra());
//                    paramsDTO.calculateBreteau();
////                    paramsDTO.incrementTVZ();
//
//                    paramsDTO.setRisco(paramsDTO.calcularRiscoSetorial());
//
//                    paramsDTO.getLarvasPorDeposito().merge(documento.getTipoDeposito(), documento.getQuantidade(), Integer::sum);
//                    paramsDTO.adicionarSomaLarvasPorDeposito(documento.getTipoDeposito(), documento.getQuantidade());
//
//                }
//            } else {
//                logger.warn("Data de Criação do Documento é nula para documento: {}", documento);
//            }
//        }
//
//        return new ArrayList<>(documentosPorQuadra.values());
//    }


//        @Transactional(readOnly = true)
//        public List<PacienteParamsDTO> getPacientesByAnoEMes (Integer mes, Integer ano, String cidade){
//            List<Paciente> pacientes = pacienteRepository.findByCidade(cidade);
//            Map<Integer, PacienteParamsDTO> casosPorUbs = new HashMap<>();
//
//            List<PacienteParamsDTO> pacientesFiltrados = new ArrayList<>();
//
//            for (Paciente paciente : pacientes) {
//                LocalDateTime dataCriacao = paciente.getDataCriacao();
//
//                if (dataCriacao != null) {
//
//                    Integer mesCriacao = dataCriacao.getMonthValue();
//                    Integer anoCriacao = dataCriacao.getYear();
//
//                    if (anoCriacao.equals(ano) && mesCriacao.equals(mes)) {
//                        PacienteParamsDTO dto = new PacienteParamsDTO(paciente);
//                        pacientesFiltrados.add(dto);
//                        int ubs = paciente.getUbs().getNumero();
//                        PacienteParamsDTO paramsDTO = casosPorUbs.get(ubs);
//
//                        if (paramsDTO != null) {
//                            if (!paciente.getObito()) {
//                                paramsDTO.incrementObito();
//                            }
//                            if (!paciente.getInternado()) {
//                                paramsDTO.incrementInternado();
//                            }
//                            if (!paciente.getObitoSuspeito()) {
//                                paramsDTO.incrementObitoSuspeito();
//                            }
//                            if (paciente.getAnalisePaciente() == AnalisePaciente.POSITIVO) {
//                                paramsDTO.incrementPacientePositivo();
//                            }
//                            paramsDTO.incremetNotificados();
//                            paramsDTO.setRisco(paramsDTO.calcularRiscoPaciente());
//                        } else {
//                            paramsDTO = new PacienteParamsDTO(paciente);
//                            casosPorUbs.put(ubs, paramsDTO);
//                        }
//                    } else {
//                        logger.warn("Data de Criação do Paciente é nula para paciente: {}", paciente);
//                    }
//                }
//            }
//            return new ArrayList<>(casosPorUbs.values());
//        }

