package proj.model;

import java.util.ArrayList;
import java.util.List;

public class Seguidores {
    
    private int numeroSeguidores;
    private List<Usuario> seguidores;

    //cria uma instância vazia
    public  Seguidores(){
        
        seguidores = new ArrayList<>();
        numeroSeguidores = 0;
    }
    
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

        setNumeroSeguidores(getNumeroSeguidores() + 1);
    }
}
