package pipa.model.dto.util;

import java.util.LinkedList;
import java.util.List;
import pipa.model.dto.PostEstagio;

public class PostEstagioHelper {
    public static List<String> validarCampos(PostEstagio post){
        List<String> erros = new LinkedList<>();
        if(post == null){
            erros.add("post não pode ser null.");
        }
        else{
            if(post.getNome() == null || post.getNome().trim() == ""){
                erros.add("Insira um nome válido.");
            }
            if(post.getEndereco() == null || post.getEndereco().trim() == ""){
                erros.add("Insira um endereço válido.");
            }
            if(post.getDescricao() == null || post.getDescricao().trim() == ""){
                erros.add("Insira um nome válido.");
            }
            if(post.getDescricaoPreview() == null || post.getDescricaoPreview().trim() == ""){
                erros.add("Insira um endereço válido.");
            }
        }
        
        return erros;
    }
}