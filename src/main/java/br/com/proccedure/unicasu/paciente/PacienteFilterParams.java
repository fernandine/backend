package br.com.proccedure.unicasu.paciente;

import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PacienteFilterParams {

    @Nullable
    private String nome;

    @Nullable
    private String estado;

    @Nullable
    private String cidade;

    @Nullable
    private TipoArbovirose tipoArbovirose;

    @Nullable
    private Boolean ascendente;

}
