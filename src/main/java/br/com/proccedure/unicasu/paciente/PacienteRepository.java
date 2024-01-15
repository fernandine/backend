package br.com.proccedure.unicasu.paciente;

import java.util.List;

import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findAll();

    @Query("SELECT p FROM Paciente p WHERE (:estado IS NULL OR LOWER(p.estado) = LOWER(:estado)) " +
    "AND (:cidade IS NULL OR LOWER(p.cidade) LIKE CONCAT('%', LOWER(:cidade), '%')) " +
    "AND (:tipoArbovirose IS NULL OR p.tipoArbovirose = :tipoArbovirose) " +
    "AND (:nome IS NULL OR LOWER(p.nome) LIKE CONCAT('%', LOWER(:nome), '%'))")
    List<Paciente> filter(@Param("estado") String estado, @Param("cidade") String cidade,
                          @Param("tipoArbovirose") TipoArbovirose tipoArbovirose, @Param("nome") String nome);

    @Query("SELECT p FROM Paciente p WHERE (:tipoArbovirose IS NULL OR p.tipoArbovirose = :tipoArbovirose) " +
        "AND (:nome IS NULL OR LOWER(p.nome) LIKE CONCAT('%', LOWER(:nome), '%')) " +
        "AND (:ubsId IS NULL OR p.ubs.id = :ubsId) " +
        "AND (:estado IS NULL OR LOWER(p.estado) = LOWER(:estado)) " +
        "AND (:cidade IS NULL OR LOWER(p.cidade) LIKE CONCAT('%', LOWER(:cidade), '%'))")
    List<Paciente> filterByUbs(@Param("tipoArbovirose") TipoArbovirose tipoArbovirose, @Param("nome") String nome, 
                    @Param("ubsId") Long ubsId, @Param("cidade") String cidade, @Param("estado") String estado);

    @Query("SELECT p FROM Paciente p WHERE (:tipoArbovirose IS NULL OR p.tipoArbovirose = :tipoArbovirose) " +
        "AND (:nome IS NULL OR LOWER(p.nome) LIKE CONCAT('%', LOWER(:nome), '%')) " +
        "AND (:ubsId IS NULL OR p.ubs.id = :ubsId) " +
        "AND (:estado IS NULL OR p.estado = :estado) " +
        "AND (:cidade IS NULL OR p.cidade = :cidade)")
    List<Paciente> filterAdmEstadual(@Param("tipoArbovirose") TipoArbovirose tipoArbovirose,
                        @Param("nome") String nome,
                        @Param("ubsId") Long ubsId,
                        @Param("estado") String estado,
                        @Param("cidade") String cidade);

    List<Paciente> findAllByCidadeAndEstado(String cidade, String estado);

    List<Paciente> findByUbsId(Long ubsId);

    List<Paciente> findByCidade(String cidade);
}