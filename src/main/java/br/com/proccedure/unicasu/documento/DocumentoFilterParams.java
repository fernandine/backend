package br.com.proccedure.unicasu.documento;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Getter
@Setter
@Data
public class DocumentoFilterParams {

    @Nullable
    private String nomeProprietario;

}
