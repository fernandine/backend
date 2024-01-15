package br.com.proccedure.unicasu.indice;

import br.com.proccedure.unicasu.paciente.Paciente;
import br.com.proccedure.unicasu.paciente.enums.TipoArbovirose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteParamsDTO implements Serializable {

    private int notificados;
    private int somaCasosPositivos;
    private int somaCasosEstrangeiros;
    private int somaCasosSuspeitos;
    private int internados;
    private int somaObitos;
    private int somaObitosSuspeitos;
    private Long ubsId;
    private String ubsNome;

    private Map<TipoArbovirose, Integer> tipoArboviroseCount  = new HashMap<>();


    public static void incrementNotificados(PacienteParamsDTO dto) {
        dto.setNotificados(dto.getNotificados() + 1);
    }

    public static void incrementSomaCasosPositivos(PacienteParamsDTO dto) {
        dto.setSomaCasosPositivos(dto.getSomaCasosPositivos() + 1);
    }

    public static void incrementSomaCasosEstrangeiros(PacienteParamsDTO dto) {
        dto.setSomaCasosEstrangeiros(dto.getSomaCasosEstrangeiros() + 1);
    }

    public static void incrementSomaCasosSuspeitos(PacienteParamsDTO dto) {
        dto.setSomaCasosSuspeitos(dto.getSomaCasosSuspeitos() + 1);
    }

    public static void incrementInternados(PacienteParamsDTO dto) {
        dto.setInternados(dto.getInternados() + 1);
    }

    public static void incrementSomaObitos(PacienteParamsDTO dto) {
        dto.setSomaObitos(dto.getSomaObitos() + 1);
    }

    public static void incrementSomaObitosSuspeitos(PacienteParamsDTO dto) {
        dto.setSomaObitosSuspeitos(dto.getSomaObitosSuspeitos() + 1);
    }

    public void incrementTipoArboviroseCount(TipoArbovirose tipoArbovirose) {
        if (tipoArboviroseCount == null) {
            tipoArboviroseCount = new HashMap<>();
        }
        tipoArboviroseCount.put(tipoArbovirose, tipoArboviroseCount.getOrDefault(tipoArbovirose, 0) + 1);
    }

}
