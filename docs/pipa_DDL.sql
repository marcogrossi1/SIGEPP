set foreign_key_checks = 0;

drop table IF EXISTS Administrador;
drop table IF EXISTS Aluno;
drop table IF EXISTS Aluno_has_Estagio;
drop table IF EXISTS Aluno_has_Projeto;
drop table IF EXISTS candidatura;
drop table IF EXISTS Empresa;
drop table IF EXISTS Empresa_has_Estagio;
drop table IF EXISTS Estagio;
drop table IF EXISTS Professor;
drop table IF EXISTS Professor_has_Projeto;
drop table IF EXISTS Projeto;
drop table IF EXISTS secoes;
drop table IF EXISTS Seguidores;
drop table IF EXISTS Usuario;

set foreign_key_checks = 1;


-- -----------------------------------------------------
-- Table Usuario
-- -----------------------------------------------------
CREATE TABLE Usuario (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX nome_UNIQUE (nome ASC) 
)
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
  telefone VARCHAR(20) NOT NULL,
  fotoPerfil LONGBLOB NULL,
  bannerPerfil LONGBLOB NULL,
  descricaoPerfil VARCHAR(550) DEFAULT "Sem descrição.",
  Usuario_id BIGINT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC),
  INDEX nome_INDEX (nome ASC),
  UNIQUE INDEX email_UNIQUE (email ASC),
  INDEX fk_Aluno_Usuario1_idx (Usuario_id ASC),
  CONSTRAINT fk_Aluno_Usuario1
    FOREIGN KEY (Usuario_id)
    REFERENCES Usuario (id)
)
ENGINE = InnoDB 
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table Administrador
-- -----------------------------------------------------
CREATE TABLE Administrador (
  id BIGINT NOT NULL AUTO_INCREMENT,
  cpf VARCHAR(20) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  campus VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  Usuario_id BIGINT NOT NULL,
  telefone VARCHAR(20) NOT NULL,
  fotoPerfil LONGBLOB NULL,
  bannerPerfil LONGBLOB NULL,
  descricaoPerfil VARCHAR(550) DEFAULT "Sem descrição.",
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC),
  UNIQUE INDEX email_UNIQUE (email ASC),
  INDEX fk_Administrador_Usuario_idx (usuario_id ASC),
  CONSTRAINT fk_Administrador_Usuario
    FOREIGN KEY (usuario_id)
    REFERENCES Usuario (id)
)
ENGINE = InnoDB DEFAULT CHARSET = utf8;

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
  salario VARCHAR(255) NULL,
  documentos VARCHAR(500) NULL,
  PRIMARY KEY (id),
  INDEX empresa_INDEX (empresa ASC))
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
  vagas_remuneradas INT NULL,
  valor_bolsa VARCHAR(8) NULL,
  vagas_voluntarias INT NULL,
  requisito VARCHAR(255) NULL,
  campus VARCHAR(70) NULL,
  tipo_projeto VARCHAR(9) NULL,
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
  progresso VARCHAR(255) NOT NULL,
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
  telefone VARCHAR(20) NOT NULL,
  email VARCHAR(255) NOT NULL,
  cpf VARCHAR(20) NOT NULL,
  fotoPerfil LONGBLOB NULL,
  bannerPerfil LONGBLOB NULL,
  descricaoPerfil VARCHAR(550) DEFAULT "Sem descrição.",
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
  cnpj VARCHAR(20) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  website VARCHAR(255) NOT NULL,
  area VARCHAR(255) NOT NULL,
  telefone VARCHAR(20) NOT NULL,
  email VARCHAR(255) NOT NULL,
  Usuario_id BIGINT NULL,
  PRIMARY KEY (id),
  INDEX fk_Empresa_Usuario1_idx (Usuario_id ASC),
  UNIQUE INDEX cnpj_UNIQUE (cnpj ASC),
  UNIQUE INDEX email_UNIQUE (email ASC),
  INDEX nome_INDEX (nome ASC),
  CONSTRAINT fk_Empresa_Usuario1
    FOREIGN KEY (Usuario_id)
    REFERENCES Usuario (id)	
    )
ENGINE = InnoDB default character set = utf8;


-- -----------------------------------------------------
-- Table Empresa_has_Estagio
-- -----------------------------------------------------
CREATE TABLE Empresa_has_Estagio (
  empresa_id BIGINT NOT NULL,
  estagio_id BIGINT NOT NULL,
  
  PRIMARY KEY (empresa_id, estagio_id),
  INDEX fk_Empresa_has_Estagio_Estagio1_idx (estagio_id ASC),
  INDEX fk_Empresa_has_Estagio_Empresa1_idx (empresa_id ASC),
  CONSTRAINT fk_Empresa_has_Estagio_Empresa1
    FOREIGN KEY (empresa_id)
    REFERENCES Empresa (id),
  CONSTRAINT fk_Empresa_has_Estagio_Estagio1
    FOREIGN KEY (estagio_id)
    REFERENCES Estagio (id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table candidatura
-- -----------------------------------------------------
CREATE TABLE candidatura (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidato_id BIGINT NOT NULL,
    oportunidade_id BIGINT NOT NULL,
    mensagem TEXT NOT NULL,
    data_aplicacao TIMESTAMP NOT NULL,
    status ENUM('EM_ANDAMENTO', 'VALIDADA', 'INVALIDADA') NOT NULL DEFAULT 'EM_ANDAMENTO',
    FOREIGN KEY (candidato_id) REFERENCES aluno(id),
    FOREIGN KEY (oportunidade_id) REFERENCES projeto(id)
);

-- -----------------------------------------------------
-- Table Professor_has_Projeto
-- -----------------------------------------------------
CREATE TABLE
  Professor_has_Projeto (
    professor_id BIGINT NOT NULL,
    projeto_id BIGINT NOT NULL,
    PRIMARY KEY (professor_id, projeto_id),
    INDEX fk_Professor_has_Projeto_Projeto1_idx (projeto_id ASC),
    INDEX fk_Professor_has_Projeto_Prodessor1_idx (professor_id ASC),
    CONSTRAINT fk_Professor_has_Projeto_Professor FOREIGN KEY (professor_id) REFERENCES Professor (id),
    CONSTRAINT fk_Professor_has_Projeto_Projeto1 FOREIGN KEY (projeto_id) REFERENCES Projeto (id)
  ) ENGINE = InnoDB default character set = utf8;
  
  
  
  -- -----------------------------------------------------
-- Table Seguidores
-- -----------------------------------------------------
CREATE TABLE Seguidores (
    seguindo_id BIGINT NOT NULL,
    seguidor_id BIGINT NOT NULL,
    INDEX fk_Seguidores_Seguindo1_idx (seguindo_id ASC) ,
  	INDEX fk_Seguidores_Seguidor1_idx (seguidor_id ASC) ,
  CONSTRAINT fk_Seguidores_Seguindo1
    FOREIGN KEY (seguindo_id)
    REFERENCES Usuario (id)
    ,
  CONSTRAINT fk_Seguidores_Seguidor1
    FOREIGN KEY (seguidor_id)
    REFERENCES Usuario (id)
    )
ENGINE = InnoDB default character set = utf8;

-- -----------------------------------------------------
-- Table SEÇÕES COLOCAR conteudoVideo, e outras coisas necesariasssssssssssssssssssssssssss
-- -----------------------------------------------------

CREATE TABLE secoes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Usuario_id BIGINT NULL,
    titulo VARCHAR(255) DEFAULT "Sem título",
    tipo VARCHAR(255) NOT NULL,
    conteudoTexto LONGTEXT,
    comprimentoConteudoTexto INT NULL,
    alturaConteudoTexto INT NULL,
    leftConteudoTexto INT NULL,
    topConteudoTexto INT NULL,
    conteudoImagem LONGBLOB NULL,
    ordem INT,
  	FOREIGN KEY (Usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

  -- -----------------------------------------------------
-- Table TOPICOS
-- -----------------------------------------------------

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    secao_id BIGINT NOT NULL,
    conteudoTexto LONGTEXT,
    conteudoImagem LONGBLOB NULL,
    conteudoArquivo LONGBLOB NULL,
    comprimentoConteudoTexto INT NULL,
    alturaConteudoTexto INT NULL,
    estado BOOLEAN NOT NULL DEFAULT false,
    data TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (secao_id) REFERENCES secoes(id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table Novidade
-- -----------------------------------------------------
CREATE TABLE Novidade (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    isEstagio BOOLEAN NOT NULL,
    link VARCHAR(55) NOT NULL,
    dataPublicacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;  -- Corrigido o fechamento

-- -----------------------------------------------------
-- Table Curso
-- -----------------------------------------------------
CREATE TABLE Curso (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table Projeto_has_Curso
-- -----------------------------------------------------
CREATE TABLE Projeto_has_Curso (
  projeto_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  PRIMARY KEY (projeto_id, curso_id),
  FOREIGN KEY (projeto_id) REFERENCES Projeto(id) ON DELETE CASCADE,
  FOREIGN KEY (curso_id) REFERENCES Curso(id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE Avaliacao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    comentario TEXT NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (perfil_id) REFERENCES Usuario(id)
);