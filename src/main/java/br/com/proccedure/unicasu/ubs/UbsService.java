package br.com.proccedure.unicasu.ubs;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbsService {

  @Autowired
  private UbsRepository ubsRepository;

  public Ubs findByIdOrThrow(long ubsId) {
    return ubsRepository.findById(ubsId)
            .orElseThrow(() -> new UnicasuException("UBS não encontrada!"));
  }

  public List<Ubs> findAll() {
    return ubsRepository.findAll();
  }

  public Ubs filterUnique(UbsFilterParams params) {
    Ubs ubs = ubsRepository.filterUnique(params.getNome(), params.getCnes(), params.getCidade(), params.getEstado());
    return ubs;
  }

  public List<Ubs> filter(UbsFilterParams params) {
    List<Ubs> ubss = ubsRepository.filter(params.getNome(), params.getCnes(), params.getCidade(), params.getEstado());
    return ubss;
  }

  public List<Ubs> findAllUbsByCidade(UbsAdmMunicipalFilterParams params) {
    return ubsRepository.filter(params.getNome(), params.getCnes(), params.getCidade(), params.getEstado());
  }

  public List<Ubs> findAllUbsByEstado(UbsAdmEstadualFilterParams params) {
    return ubsRepository.filter(params.getNome(), params.getCnes(), params.getCidade(), params.getEstado());
  }


  public Ubs create(Ubs newUbs) {
    Ubs ubs = new Ubs();

    ubs.setBairro(newUbs.getBairro());
    ubs.setCep(newUbs.getCep());
    ubs.setLogradouro(newUbs.getLogradouro());
    ubs.setNumero(newUbs.getNumero());
    ubs.setCnes(newUbs.getCnes());
    ubs.setCnpj(newUbs.getCnpj());
    ubs.setNome(newUbs.getNome());
    ubs.setEstado(newUbs.getEstado());
    ubs.setMunicipio(newUbs.getMunicipio());
    ubs.setAtivo(newUbs.getAtivo());
    ubs.setDataCriacao(LocalDate.now());
    ubs.setHoraCriacao(LocalTime.now());

    ubs = ubsRepository.save(ubs);

    return ubs;
  }

  public Ubs update(long ubsId, Ubs newUbs) {
    Ubs ubs = findByIdOrThrow(ubsId);

    ubs.setBairro(newUbs.getBairro());
    ubs.setCep(newUbs.getCep());
    ubs.setLogradouro(newUbs.getLogradouro());
    ubs.setNumero(newUbs.getNumero());
    ubs.setCnes(newUbs.getCnes());
    ubs.setCnpj(newUbs.getCnpj());
    ubs.setNome(newUbs.getNome());
    ubs.setEstado(newUbs.getEstado());
    ubs.setMunicipio(newUbs.getMunicipio());
    ubs.setAtivo(newUbs.getAtivo());

    return ubsRepository.save(ubs);
  }

  public void delete(long ubsId) {
    Ubs ubs = findByIdOrThrow(ubsId);
    ubsRepository.delete(ubs);
  }

}
