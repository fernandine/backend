package br.com.proccedure.unicasu.documento;

import java.time.LocalDate;

import br.com.proccedure.unicasu.documento.enums.TipoAnalise;
import br.com.proccedure.unicasu.documento.enums.TipoIntervencao;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoUpdatePainelParams {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private TipoIntervencao intervencao;
    private TipoAnalise analiseLaboratorio;
    private TipoArbovirose tipoArbovirose;

}
