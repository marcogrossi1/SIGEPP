-- -----------------------------------------------------
-- Table EventoProjeto
-- -----------------------------------------------------
CREATE TABLE EventoProjeto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem VARCHAR(255),
    dateCriacao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    dateExpiracao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status ENUM('ATIVO', 'ARQUIVADO', 'RASCUNHO') NOT NULL DEFAULT 'RASCUNHO',
    projeto_id BIGINT NOT NULL,
    imagem LONGBLOB NULL,
    PRIMARY KEY(id),
    INDEX fk_Projeto_Id_idx (projeto_id ASC),
    CONSTRAINT fk_Projeto_Id
        FOREIGN KEY (projeto_id)
        REFERENCES Projeto (id)
);