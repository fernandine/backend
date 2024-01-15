package br.com.proccedure.unicasu.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findAll();

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario IN :tiposUsuario")
    List<Usuario> getUbsPlusAgenteEndemia(@Param("tiposUsuario") List<TipoUsuario> tiposUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario IN :tiposUsuario AND (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))  AND (:cidade IS NULL OR LOWER(u.municipio) LIKE CONCAT('%', LOWER(:cidade), '%'))")
    List<Usuario> filterUbsPlusAgenteEndemia(@Param("tiposUsuario") List<TipoUsuario> tiposUsuario, @Param("nome") String nome,  @Param("cidade") String cidade);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario AND (:cidade IS NULL OR LOWER(u.municipio) LIKE CONCAT('%', LOWER(:cidade), '%'))")
    List<Usuario> findAllUbsByCidade(@Param("tipoUsuario") TipoUsuario tipoUsuario, @Param("cidade") String cidade);

    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario AND (:estado IS NULL OR LOWER(u.estado) LIKE CONCAT('%', LOWER(:estado), '%'))")
    List<Usuario> findAllUbsByEstado(@Param("tipoUsuario") TipoUsuario tipoUsuario, @Param("estado") String estado);

    @Query("SELECT u FROM Usuario u WHERE (:nome IS NULL OR LOWER(u.nome) LIKE CONCAT('%', LOWER(:nome), '%')) AND (:cidade IS NULL OR LOWER(u.municipio) LIKE CONCAT('%', LOWER(:cidade), '%')) AND (:estado IS NULL OR LOWER(u.estado) = LOWER(:estado))")
    List<Usuario> filterAdmGeral(@Param("nome") String nome, @Param("estado") String estado, @Param("cidade") String cidade);

    List<Usuario> findByMunicipioAndTipoUsuario(String municipio, TipoUsuario tipoUsuario);

}