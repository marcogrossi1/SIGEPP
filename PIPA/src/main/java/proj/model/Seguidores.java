package proj.model;

import java.util.List;

public class Seguidores {
    
    private int numeroSeguidores;
    private List<Aluno> seguidores;
    
    public Seguidores(int n, List<Aluno> s){
        
        numeroSeguidores = n;
        seguidores = s;
    }

    public int getNumeroSeguidores() {
        return numeroSeguidores;
    }

    public List<Aluno> getSeguidores() {
        return seguidores;
    }

    public void setNumeroSeguidores(int numeroSeguidores) {
        this.numeroSeguidores = numeroSeguidores;
    }

    public void setSeguidores(List<Aluno> seguidores) {
        this.seguidores = seguidores;
    }
    
    //adiciona um novo seguidor a lista
    public void appendSeguidor(Aluno a){
        this.seguidores.add(a);
    }
}
