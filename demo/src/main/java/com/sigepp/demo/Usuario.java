package com.sigepp.demo;

/*
 * É preciso adicionar um método de encriptação de senhas e emails.
 * Assim, ao setar esses dados, nem nós teremos acesso aos dados oficiais sem que os descriptografemos.
 * ToDo: Método de Encriptação. (talvez valha criar uma classe par isso)
 */
public class Usuario {
    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    protected String getEmail () {
        return email;
    }

    protected String getSenha() {
        return senha;
    }

    // private String encriptacao () {}
}
