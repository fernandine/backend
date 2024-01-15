package br.com.proccedure.unicasu.indice;

import br.com.proccedure.unicasu.documento.Documento;
import br.com.proccedure.unicasu.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VistoriasDto implements Serializable {

    private Long id;
    private String agenteNome;
    private Integer vistoriaRealizada;
    private Integer vistoriaPendente;
    private Integer vistoriaAFazer;
    private LocalDateTime dataCriacao;

    public VistoriasDto(Usuario usuario, Integer vistoriasPendentes) {
        id = usuario.getId();
        agenteNome = usuario.getNome();
        vistoriaRealizada = Math.toIntExact(usuario.getDocumentos().stream().filter(this::foiRealizada).count());
        vistoriaPendente = vistoriasPendentes;
        vistoriaAFazer = (usuario.getQuantidadeDocumentosAFazer() != null) ? usuario.getQuantidadeDocumentosAFazer() : 0;
        dataCriacao = usuario.getDataCriacao();

    }
    private boolean foiRealizada(Documento documento) {
        return documento.getId() != null;
    }
}

