-- -----------------------------------------------------
-- aluno
-- -----------------------------------------------------
INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (1,"00000000001","Thiago Figueiredo","Eletrônica","Nova Gameleira","thiago@gmail.com",sha2('123', 512),"serie 1");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (2,"00000000002","Caio Figueiredo","Informática","Nova Gameleira","caio@gmail.com",sha2('123', 512),"serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (3,"00000000003","Matheus Silva","Mecânica","Nova Suiça","matheus@gmail.com",sha2('123', 512),"serie 3");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (4,"00000000004","Gabriel Tavares","Informática","Nova Gameleira","gabriel@gmail.com",sha2('123', 512),"serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (5,"00000000005","Marco Grossi","Edificações","Nova Gameleira","marco@gmail.com",sha2('123', 512),"serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (6,"00000000006","Nicolas Chagas","Eletrônica","Araxá","nicolas@gmail.com",sha2('123', 512),"serie 2");

-- -----------------------------------------------------
-- empresa
-- -----------------------------------------------------
INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (1,"00000000000001","Cemig","Rua da Bahia","www.cemig.com.br","Energia","0800 000 0001","contato@cemig.br");

INSERT INTO empresa 
(id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (2,"00000000000002","Petrobras","Avenida Chile","www.petrobras.com.br","Petróleo","0800 000 0002","contato@petrobras.com.br");

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (3,"00000000000003","Vale","Praia de Botafogo","www.vale.com","Mineração","0800 000 0003","contato@vale.com");

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (4,"00000000000004","Embraer","Avenida Brigadeiro Faria Lima","www.embraer.com.br","Aeronáutica","0800 000 0004","contato@embraer.com.br");

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (5,"00000000000005","Copasa","Avenida do Contorno","www.copasa.com.br","Saneamento","0800 000 0005","contato@copasa.com.br");

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email)
VALUES (6,"00000000000006","Santa Clara","Rua das Flores","www.santaclara.com.br","Alimentos","0800 000 0006","contato@santaclara.com.br");

-- -----------------------------------------------------
INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (1,"Cemig","Vagas Setor Manutenção",80,3,"Cursando Eletrotécnica, serie 3", "1500");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (2,"Copasa","Vagas Setor de TI",80,2,"Cursando Informática, serie 3", "1200");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (3,"Santa Clara","Vagas Setor de Engenharia",80,10,"Cursando Engenharia Mecânica, periodo 3", "1300");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (4,"Petrobras","Vagas Setor de Pesquisa",80,5,"Cursando Engenharia Química, serie 3", "1400");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (5,"Vale","Vagas Setor de Logística",80,4,"Cursando Engenharia de Produção, serie 3", "A combinar");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (6,"Embraer","Vagas Setor de Aeronáutica",80,6,"Cursando Engenharia Aeronáutica, serie 3", "1600");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario)
VALUES (7,"Embraer","Vagas Setor de Desenvolvimento",80,5,"Cursando Engenharia Aeronáutica, serie 4", "1550");
-- -----------------------------------------------------
-- projeto
-- -----------------------------------------------------
INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES (1,"Pipa","Cristiano Amaral Maffort",
        "Trabalho em sistema de coordenação de oportunidades acadêmicas.",80,10,"Informática serie 2");

INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES (2,"Ficando Rico","Lucia Próspera",
        "Projeto em matemática financeira.",50,2,"Ser aluno do Cefet.");			

-- -----------------------------------------------------
-- aluno_has_estagio
-- -----------------------------------------------------
INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (1,1);

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (3,3);

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (2,2);

-- -----------------------------------------------------
-- aluno_has_projeto
-- -----------------------------------------------------
INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (1,1);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (3,1);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (4,2);

-- -----------------------------------------------------
-- empresa_has_estagio
-- -----------------------------------------------------
INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(1,1);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(4,2);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(6,3);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(2,4);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(3,5);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(4,6);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id)
VALUES(4,7);