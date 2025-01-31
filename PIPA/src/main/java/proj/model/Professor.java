package proj.model;

import java.util.ArrayList;

public class Professor {

    private long id;
    private String cpf;
    private String nome;
    private String departamento;
    private String email;
    private String telefone;
    private byte[] fotoPerfil;
    private byte[] bannerPerfil;
    private String descricaoPerfil;
    private long usuario_id;
    
    private ArrayList<Projeto> listaProjeto = new ArrayList<Projeto>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public byte[] getBannerPerfil() {
        return bannerPerfil;
    }

    public void setBannerPerfil(byte[] bannerPerfil) {
        this.bannerPerfil = bannerPerfil;
    }

    public String getDescricaoPerfil() {
        return descricaoPerfil;
    }

    public void setDescricaoPerfil(String descricaoPerfil) {
        this.descricaoPerfil = descricaoPerfil;
    }

    public long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public ArrayList<Projeto> getListaProjeto() {
        return listaProjeto;
    }

    public void setListaProjeto(ArrayList<Projeto> listaProjeto) {
        this.listaProjeto = listaProjeto;
    }

    @Override
    public String toString() {
        return "Professor [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", departamento=" + departamento
                + ", email=" + email + ", telefone=" + telefone + ", usuario_id=" + usuario_id + "]";
    }
}