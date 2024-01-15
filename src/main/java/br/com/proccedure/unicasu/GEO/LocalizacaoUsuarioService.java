//package br.com.proccedure.unicasu.GEO;
//
//import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
//import br.com.proccedure.unicasu.usuario.Usuario;
//import br.com.proccedure.unicasu.usuario.UsuarioRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//public class LocalizacaoUsuarioService {
//
//    @Autowired
//    private LocalizacaoUsuarioRepository repository;
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Transactional(readOnly = true)
//    public LocalizacaoUsuarioDTO findById(Long id) {
//        Optional<LocalizacaoUsuario> obj = repository.findById(id);
//        LocalizacaoUsuario entity = obj.orElseThrow(() -> new UnicasuException("Entity not found"));
//        return modelMapper.map(entity, LocalizacaoUsuarioDTO.class);
//    }
//
//    @Transactional
//    public LocalizacaoUsuarioDTO insert(LocalizacaoUsuarioDTO dto) {
//        LocalizacaoUsuario entity = new LocalizacaoUsuario();
//        copyDtoToEntity(dto, entity);
//
//        entity = repository.save(entity);
//        return modelMapper.map(entity, LocalizacaoUsuarioDTO.class);
//    }
//
//    private void copyDtoToEntity(LocalizacaoUsuarioDTO dto, LocalizacaoUsuario entity) {
//
//        entity.setLatitude(dto.getLatitude());
//        entity.setLongitude(dto.getLongitude());
//        entity.setDataHoraRegistro(dto.getDataHoraRegistro());
//
//        if (dto.getUsuarioId() != null) {
//            Usuario user = usuarioRepository.getReferenceById(dto.getUsuarioId());
//            entity.setUsuario(user);
//        }
//    }
//}
