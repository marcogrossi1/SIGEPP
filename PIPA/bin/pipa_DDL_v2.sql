-- -----------------------------------------------------
-- Table Usuario
-- -----------------------------------------------------
CREATE TABLE Usuario (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX nome_UNIQUE (nome ASC) )
ENGINE = InnoDB default character set = utf8;


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
  periodo VARCHAR(255) NOT NULL,
  Usuario_id BIGINT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC) ,
  INDEX nome_INDEX (nome ASC) ,
  UNIQUE INDEX email_UNIQUE (email ASC) ,
  INDEX fk_Aluno_Usuario1_idx (Usuario_id ASC) ,
  CONSTRAINT fk_Aluno_Usuario1
    FOREIGN KEY (Usuario_id)
    REFERENCES Usuario (id)
    )
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
ENGINE = InnoDB default character set = utf8;


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
ENGINE = InnoDB default character set = utf8;


-- -----------------------------------------------------
-- Table Aluno_has_Projeto
-- -----------------------------------------------------
CREATE TABLE Aluno_has_Projeto (
  aluno_id BIGINT NOT NULL,
  projeto_id BIGINT NOT NULL,
  PRIMARY KEY (aluno_id, projeto_id),
  INDEX fk_Aluno_has_Projeto_Projeto1_idx (projeto_id ASC) ,
  INDEX fk_Aluno_has_Projeto_Aluno_idx (aluno_id ASC) ,
  CONSTRAINT fk_Aluno_has_Projeto_Aluno
    FOREIGN KEY (aluno_id)
    REFERENCES Aluno (id)
    ,
  CONSTRAINT fk_Aluno_has_Projeto_Projeto1
    FOREIGN KEY (projeto_id)
    REFERENCES Projeto (id)
    )
ENGINE = InnoDB default character set = utf8;


-- -----------------------------------------------------
-- Table Aluno_has_Estagio
-- -----------------------------------------------------
CREATE TABLE Aluno_has_Estagio (
  aluno_id BIGINT NOT NULL,
  estagio_id BIGINT NOT NULL,
  PRIMARY KEY (aluno_id, estagio_id),
  INDEX fk_Aluno_has_Estagio_Estagio1_idx (estagio_id ASC) ,
  INDEX fk_Aluno_has_Estagio_Aluno1_idx (aluno_id ASC) ,
  CONSTRAINT fk_Aluno_has_Estagio_Aluno1
    FOREIGN KEY (aluno_id)
    REFERENCES Aluno (id)
    ,
  CONSTRAINT fk_Aluno_has_Estagio_Estagio1
    FOREIGN KEY (estagio_id)
    REFERENCES Estagio (id)
    )
ENGINE = InnoDB default character set = utf8;

-- -----------------------------------------------------
-- Table Professor
-- -----------------------------------------------------
CREATE TABLE Professor (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  Usuario_id BIGINT NULL,
  PRIMARY KEY (id),
  INDEX fk_Professor_Usuario1_idx (Usuario_id ASC) ,
  CONSTRAINT fk_Professor_Usuario1
    FOREIGN KEY (Usuario_id)
    REFERENCES Usuario (id)
    )
ENGINE = InnoDB default character set = utf8;


-- -----------------------------------------------------
-- Table Empresa
-- -----------------------------------------------------
CREATE TABLE Empresa (
  id BIGINT NOT NULL  AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  Usuario_id BIGINT NULL,
  PRIMARY KEY (id),
  INDEX fk_Empresa_Usuario1_idx (Usuario_id ASC) ,
  CONSTRAINT fk_Empresa_Usuario1
    FOREIGN KEY (Usuario_id)
    REFERENCES Usuario (id)
    )
ENGINE = InnoDB default character set = utf8;
