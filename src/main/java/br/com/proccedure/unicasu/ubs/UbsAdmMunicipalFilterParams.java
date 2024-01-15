package br.com.proccedure.unicasu.ubs;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UbsAdmMunicipalFilterParams {

    @Nullable
    private String nome;

    @Nullable
    private String cnes;
  
    private String estado;

    private String cidade;

}
