package proj.model;

import java.util.List;

public class Seguidores {
    
    private int numeroSeguidores;
    private List<Usuario> seguidores;
    
    public Seguidores(List<Usuario> s){
        
        seguidores = s;
        numeroSeguidores = s.size();
    }

    public int getNumeroSeguidores() {
        return numeroSeguidores;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setNumeroSeguidores(int numeroSeguidores) {
        this.numeroSeguidores = numeroSeguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }
    
    //adiciona um novo seguidor a lista
    public void appendSeguidor(Usuario a){
        this.seguidores.add(a);
    }
}
