package br.com.proccedure.unicasu.ubs;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UbsRepository extends JpaRepository<Ubs, Long> {

    List<Ubs> findAll();

    Optional<Ubs> findByNome(String nome);

    Optional<Ubs> findByCnes(String cnes);

    Boolean existsByNome(String nome);

    Boolean existsByCnes(String cnes);

    @Query("SELECT u FROM Ubs u WHERE (:nome IS NULL OR LOWER(u.nome) LIKE CONCAT('%', LOWER(:nome), '%')) AND " +
    "(:cnes IS NULL OR LOWER(u.cnes) LIKE CONCAT('%', LOWER(:cnes), '%')) AND " + 
    "(:cidade IS NULL OR LOWER(u.municipio) LIKE CONCAT('%', LOWER(:cidade), '%')) AND " + 
    "(:estado IS NULL OR LOWER(u.estado) LIKE CONCAT('%', LOWER(:estado), '%'))")
    List<Ubs> filter(@Param("nome") String nome, @Param("cnes") String cnes,
                @Param("cidade") String cidade, @Param("estado") String estado);

    @Query("SELECT u FROM Ubs u WHERE (:nome IS NULL OR LOWER(u.nome) LIKE CONCAT('%', LOWER(:nome), '%')) AND " +
    "(:cnes IS NULL OR LOWER(u.cnes) LIKE CONCAT('%', LOWER(:cnes), '%')) AND " + 
    "(:cidade IS NULL OR LOWER(u.municipio) LIKE CONCAT('%', LOWER(:cidade), '%')) AND " + 
    "(:estado IS NULL OR LOWER(u.estado) LIKE CONCAT('%', LOWER(:estado), '%'))")
    Ubs filterUnique(@Param("nome") String nome, @Param("cnes") String cnes,
                @Param("cidade") String cidade, @Param("estado") String estado);

}