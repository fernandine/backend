package br.com.proccedure.unicasu.documento;

import java.io.Serializable;

import br.com.proccedure.unicasu.documento.enums.TipoDeposito;
import br.com.proccedure.unicasu.documento.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoParams implements Serializable {

    private Long id;
    private Long userId;
    private TipoImovel propertyType;
    private Boolean propertyAccess;
    private MultipartFile mainImage;
    private MultipartFile propertyImage;
    private String comments;
    private String road;
    private String neighborhood;
    private Integer number;
    private String city;
    private String quadra;
    private Integer zona;
    private String uf;
    private String cep;
    private String name;
    private String birthDate;
    private String cpf;
    private Boolean spawn;
    private TipoDeposito deposits;
    private Integer quantity;
    private Boolean larvae;
    private String depositsInfo;
    private String tagCode;

    private String complemento;
    private Boolean tratamentoLarvicida;
    private Boolean coleta;
    private Integer quantidadeLarvicida;

}
