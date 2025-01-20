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