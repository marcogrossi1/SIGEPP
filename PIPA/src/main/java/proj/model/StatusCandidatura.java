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

    public static StatusCandidatura fromDescricao(String descricao) {
        for (StatusCandidatura status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao) || status.name().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        System.err.println("Status inválido recebido: " + descricao);
        throw new IllegalArgumentException("Status inválido: " + descricao);
    }


}
