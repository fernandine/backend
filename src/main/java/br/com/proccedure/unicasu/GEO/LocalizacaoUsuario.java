//package br.com.proccedure.unicasu.GEO;
//
//import br.com.proccedure.unicasu.usuario.Usuario;
//import lombok.*;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "localizacao_usuario")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(of = "id")
//public class LocalizacaoUsuario implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "usuario_id")
//    private Usuario usuario;
//
//    @Column(nullable = false)
//    private Double latitude;
//
//    @Column(nullable = false)
//    private Double longitude;
//
//    @Column(nullable = false)
//    private LocalDateTime dataHoraRegistro;
//
//}

