package br.com.proccedure.unicasu.documento;

import org.springframework.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@Data
public class DocumentoAdmMunicipalFilterParams {

    @Nullable
    private String nomeProprietario;

    private String cidade;

    private String estado;

}
