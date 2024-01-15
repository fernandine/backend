package br.com.proccedure.unicasu.documento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoPage {

    private List<DocumentoDTO> documentos;
    private long totalElements;
    private int totalPages;
}
