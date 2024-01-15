package br.com.proccedure.unicasu.relatorio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/relatorio")
public class RelatorioController {

  @Autowired
  private RelatorioService relatorioService;

  @GetMapping("/detalhe/{id}")
  public RelatorioDTO findById(@PathVariable("id") long relatorioId){
    return relatorioService.findById(relatorioId);
  }

  @GetMapping
  public List<RelatorioDTO> findAll(){
    return relatorioService.findAll();
  }

  @GetMapping("/data/{data}/cidade/{cidade}")
  public List<RelatorioDTO> listByDataAdmMunicipal(@PathVariable("data") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data,
                                            @PathVariable("cidade") String cidade) {
      List<Relatorio> relatorios = relatorioService.getRelatoriosByDataECidade(data, cidade);
      return RelatorioDTO.ofList(relatorios);
  }

  @GetMapping("/data/adm-nacional/{data}")
  public List<RelatorioDTO> listByDataAdmNacional(@PathVariable("data") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data) {
    List<Relatorio> relatorios = relatorioService.getRelatoriosByDataAdmNacional(data);
    return RelatorioDTO.ofList(relatorios);
  }

  @GetMapping("/data/adm-estadual/{data}/estado/{estado}")
  public List<RelatorioDTO> listByDataAdmEstadual(@PathVariable("data") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data,
                                            @PathVariable("estado") String estado) {
    List<Relatorio> relatorios = relatorioService.getRelatoriosByDataAdmEstadual(data, estado);
    return RelatorioDTO.ofList(relatorios);
  }

  @GetMapping("/estado/{uf}")
  public List<RelatorioDTO> findByAllEstado(@PathVariable("uf") String uf){
    return relatorioService.findByAllEstado(uf);
  }

  @GetMapping("/cidade/{cidade}")
  public List<RelatorioDTO> findAllByCidade(@PathVariable("cidade") String cidade){
    return relatorioService.findByAllCidade(cidade);
  }

  @PostMapping
  public RelatorioDTO create(@RequestBody RelatorioParams params){
    return relatorioService.create(params);
  }

  @PutMapping("/{id}")
  public RelatorioDTO editRelatorio(@PathVariable("id") Long relatorioId, @RequestBody Relatorio updatedRelatorio){
    return relatorioService.update(relatorioId, updatedRelatorio);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long relatorioId){
    relatorioService.deleteById(relatorioId);
  }

}
