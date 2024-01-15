package br.com.proccedure.unicasu.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import br.com.proccedure.unicasu.documento.Documento;
import br.com.proccedure.unicasu.ubs.Ubs;
import lombok.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String nome;

    @Column(name = "soma_quantidade_larvicida")
    private Integer somaQuantidadeLarvicida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipoUsuario tipoUsuario;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    private String senha;

    @Column(unique = true)
    private String cnpj;

    @Column(nullable = true)
    private String estado;

    @Column(nullable = true)
    private String municipio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private RegiaoGeografica regiaoGeografica;

    private String cep;

    private String bairro;

    private String logradouro;

    private Integer numero;

    @Column(nullable = true)
    private Boolean ativo;

    @Column(nullable = true)
    private LocalDateTime dataCriacao;

    @Column(nullable = true)
    private LocalDateTime horaCriacao;

    @Column(name = "quantidade_documentos_a_fazer")
    private Integer quantidadeDocumentosAFazer;
//    @OneToMany(mappedBy = "usuario")
//    private List<HistoricoDocumentos> historicoDocumentos = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(nullable = true)
    private Ubs ubs;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

//    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<LocalizacaoUsuario> localizacoes = new ArrayList<>();


    public Usuario() {}

    public Usuario(Long id, String nome, Integer somaQuantidadeLarvicida, TipoUsuario tipoUsuario, String email,
                   String cpf, String senha, String cnpj, String estado, String municipio,
                   RegiaoGeografica regiaoGeografica, String cep, String bairro, String logradouro, Integer numero,
                   Boolean ativo, LocalDateTime dataCriacao, LocalDateTime horaCriacao,
                   Integer quantidadeDocumentosAFazer, Ubs ubs) {
        this.id = id;
        this.nome = nome;
        this.somaQuantidadeLarvicida = somaQuantidadeLarvicida;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.cnpj = cnpj;
        this.estado = estado;
        this.municipio = municipio;
        this.regiaoGeografica = regiaoGeografica;
        this.cep = cep;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.horaCriacao = horaCriacao;
        this.quantidadeDocumentosAFazer = quantidadeDocumentosAFazer;
        this.ubs = ubs;
    }

//    public void adicionarHistoricoDocumento(HistoricoDocumentos historicoDocumento) {
//        historicoDocumentos.add(historicoDocumento);
//        historicoDocumento.setUsuario(this);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getSomaQuantidadeLarvicida() {
        return somaQuantidadeLarvicida;
    }

    public void setSomaQuantidadeLarvicida(Integer somaQuantidadeLarvicida) {
        this.somaQuantidadeLarvicida = somaQuantidadeLarvicida;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public RegiaoGeografica getRegiaoGeografica() {
        return regiaoGeografica;
    }

    public void setRegiaoGeografica(RegiaoGeografica regiaoGeografica) {
        this.regiaoGeografica = regiaoGeografica;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(LocalDateTime horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public Ubs getUbs() {
        return ubs;
    }

    public void setUbs(Ubs ubs) {
        this.ubs = ubs;
    }

    public Integer getQuantidadeDocumentosAFazer() {
        return quantidadeDocumentosAFazer;
    }

    public void setQuantidadeDocumentosAFazer(Integer quantidadeDocumentosAFazer) {
        this.quantidadeDocumentosAFazer = quantidadeDocumentosAFazer;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
