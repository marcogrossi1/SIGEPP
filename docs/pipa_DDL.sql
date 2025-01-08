
-- -----------------------------------------------------
-- Table Aluno
-- -----------------------------------------------------
CREATE TABLE Aluno (
  id BIGINT NOT NULL AUTO_INCREMENT,
  cpf VARCHAR(20) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  curso VARCHAR(255) NOT NULL,
  campus VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  periodo VARCHAR(255) NOT NULL,
  
  
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC) ,
  INDEX nome_INDEX (nome ASC) ,
  UNIQUE INDEX email_UNIQUE (email ASC) )
ENGINE = InnoDB default character set = utf8;

-- -----------------------------------------------------
-- Table Estagio
-- -----------------------------------------------------
CREATE TABLE Estagio (
  id BIGINT NOT NULL AUTO_INCREMENT,
  empresa VARCHAR(255) NOT NULL,
  descricao VARCHAR(500) NULL,
  carga_horaria INT NULL,
  vagas INT NULL,
  requisito VARCHAR(255) NULL,
  
  PRIMARY KEY (id),
  INDEX empresa_INDEX (empresa ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projeto
-- -----------------------------------------------------
CREATE TABLE Projeto (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  responsavel VARCHAR(255) NOT NULL,
  descricao VARCHAR(500) NULL,
  carga_horaria INT NULL,
  vagas INT NULL,
  requisito VARCHAR(255) NULL,
  
  PRIMARY KEY (id),
  UNIQUE INDEX nome_UNIQUE (nome ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Aluno_has_Projeto
-- -----------------------------------------------------
CREATE TABLE Aluno_has_Projeto (
  aluno_id BIGINT NOT NULL,
  projeto_id BIGINT NOT NULL,
  
  PRIMARY KEY (Aluno_id, Projeto_id),
  INDEX fk_Aluno_has_Projeto_Projeto1_idx (Projeto_id ASC) ,
  INDEX fk_Aluno_has_Projeto_Aluno_idx (Aluno_id ASC) ,
  CONSTRAINT fk_Aluno_has_Projeto_Aluno
    FOREIGN KEY (Aluno_id)
    REFERENCES Aluno (id),
  CONSTRAINT fk_Aluno_has_Projeto_Projeto1
    FOREIGN KEY (Projeto_id)
    REFERENCES Projeto (id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Aluno_has_Estagio
-- -----------------------------------------------------
CREATE TABLE Aluno_has_Estagio (
  aluno_id BIGINT NOT NULL,
  estagio_id BIGINT NOT NULL,
  
  PRIMARY KEY (Aluno_id, Estagio_id),
  INDEX fk_Aluno_has_Estagio_Estagio1_idx (Estagio_id ASC) ,
  INDEX fk_Aluno_has_Estagio_Aluno1_idx (Aluno_id ASC) ,
  CONSTRAINT fk_Aluno_has_Estagio_Aluno1
    FOREIGN KEY (Aluno_id)
    REFERENCES  Aluno (id),
  CONSTRAINT fk_Aluno_has_Estagio_Estagio1
    FOREIGN KEY (Estagio_id)
    REFERENCES Estagio (id))
ENGINE = InnoDB;
