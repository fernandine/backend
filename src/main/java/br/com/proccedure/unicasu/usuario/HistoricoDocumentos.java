//package br.com.proccedure.unicasu.usuario;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//@Getter
//@Setter
//@Data
//public class HistoricoDocumentos {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "usuario_id")
//    private Usuario usuario;
//    @Column(name = "quantidade_documentos_a_fazer")
//    private Integer quantidadeDocumentosAFazer;
//
//    private Integer mes;
//
//    private Integer ano;
//}
