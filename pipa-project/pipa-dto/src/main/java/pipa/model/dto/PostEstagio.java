package pipa.model.dto;

public class PostEstagio {
    private String nome;
    private String descricao;
    private String descricaoPreview;
    private String endereco;
    private int salFloor;
    private int salRoof;
    
    
    public String getNome(){return nome;}
    public String getDescricao(){return descricao;}
    public String getDescricaoPreview(){return descricaoPreview;}
    public String getEndereco(){return endereco;}
    public int[] getSalarioRange(){
        int[] arr = new int[2];
        arr[0] = salFloor;
        arr[1] = salRoof;
        return arr;
    }
    public void setNome(String n){nome = n;}
    public void setDescricao(String d){descricao = d;}
    public void setDescricaoPreview(String dp){descricaoPreview = dp;}
    public void setEndereco(String e){endereco = e;}
    public void setSalarioRange(int f, int r){
        salFloor = f;
        salRoof = r;
    }
    public void setSalarioRange(int sal){
        salFloor = salRoof = sal;
    }
}
