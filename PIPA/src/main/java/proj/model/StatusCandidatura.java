package proj.model;

public enum StatusCandidatura {
    EM_ANDAMENTO("Em Andamento"),
    VALIDADA("Validada"),
    INVALIDADA("Invalidada");

    private final String descricao;

    StatusCandidatura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}