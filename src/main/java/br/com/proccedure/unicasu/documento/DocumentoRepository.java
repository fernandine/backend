package br.com.proccedure.unicasu.documento;

import br.com.proccedure.unicasu.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findAll();

    List<Documento> findByUsuarioId(Long usuarioId);

    @Query("SELECT d FROM Documento d WHERE (:nomeProprietario IS NULL OR LOWER(d.nomeProprietario) LIKE CONCAT('%', LOWER(:nomeProprietario), '%'))")
    List<Documento> filter(@Param("nomeProprietario") String nomeProprietario);

    @Query(value = "SELECT * FROM documento d WHERE (:nomeProprietario IS NULL OR LOWER(d.nome_proprietario) LIKE CONCAT('%', LOWER(:nomeProprietario), '%')) AND (:cidade IS NULL OR LOWER(d.cidade) LIKE CONCAT('%', LOWER(:cidade), '%')) AND (:estado IS NULL OR LOWER(d.uf) LIKE CONCAT('%', LOWER(:estado), '%'))", nativeQuery = true)
    List<Documento> filterAdmMunicipal(@Param("nomeProprietario") String nomeProprietario,
                                       @Param("cidade") String cidade, @Param("estado") String estado);

    @Query(value = "SELECT * FROM documento d WHERE (:nomeProprietario IS NULL OR LOWER(d.nome_proprietario) LIKE CONCAT('%', LOWER(:nomeProprietario), '%')) AND (:cidade IS NULL OR LOWER(d.cidade) LIKE CONCAT('%', LOWER(:cidade), '%')) AND (:estado IS NULL OR LOWER(d.uf) LIKE CONCAT('%', LOWER(:estado), '%'))", nativeQuery = true)
    List<Documento> filterAdmEstadual(@Param("nomeProprietario") String nomeProprietario,
                                      @Param("cidade") String cidade, @Param("estado") String estado);

    List<Documento> findByUf(String uf);

    List<Documento> findByCidade(String cidade);

    @Query("SELECT d FROM Documento d WHERE (:mes IS NULL OR MONTH(d.dataCriacao) = :mes) AND (:ano IS NULL OR YEAR(d.dataCriacao) = :ano) AND (:cidade IS NULL OR LOWER(d.cidade) LIKE CONCAT('%', LOWER(:cidade), '%'))")
    Page<Documento> findAll(Integer mes, Integer ano, String cidade, Pageable pageable);

    @Query("SELECT d FROM Documento d WHERE d.usuario = :usuario AND MONTH(d.dataCriacao) = :mes AND YEAR(d.dataCriacao) = :ano")
    List<Documento> findByUsuarioAndMesAndAno(@Param("usuario") Usuario usuario, @Param("mes") Integer mes, @Param("ano") Integer ano);
}



