package br.com.proccedure.unicasu.relatorio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

  List<Relatorio> findByUf(String uf);

  List<Relatorio> findByCidade(String cidade);

  @Query("SELECT r FROM Relatorio r WHERE r.dataCriacao >= :dataCriacao AND r.cidade = :cidade")
  List<Relatorio> findByDataAdmMunicipal(@Param("dataCriacao") LocalDate dataCriacao,
                                                              @Param("cidade") String cidade);
  
  @Query("SELECT r FROM Relatorio r WHERE r.dataCriacao >= :dataCriacao")
  List<Relatorio> findByDataAdmNacional(@Param("dataCriacao") LocalDate dataCriacao);

  @Query("SELECT r FROM Relatorio r WHERE r.dataCriacao >= :dataCriacao AND r.uf = :estado")
  List<Relatorio> findByDataAdmEstadual(@Param("dataCriacao") LocalDate dataCriacao, @Param("estado") String estado);

}
