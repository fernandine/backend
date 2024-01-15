package br.com.proccedure.unicasu.paciente;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PacienteFilterCidadeParams {

    private String cidade;

    private String estado;

}
