INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (1,"00000000001","Thiago Figueiredo","Eletrônica","Nova Gameleira","thiago@gmail.com","123","serie 1");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (2,"00000000002","Caio Figueiredo","Informática","Nova Gameleira","caio@gmail.com","123","serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (3,"00000000003","Matheus Silva","Mecânica","Nova Suiça","matheus@gmail.com","123","serie 3");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (4,"00000000004","Gabriel Tavares","Informática","Nova Gameleira","gabriel@gmail.com","123","serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (5,"00000000005","Marco Grossi","Edificações","Nova Gameleira","marco@gmail.com","123","serie 2");

INSERT INTO aluno (id, cpf, nome, curso, campus, email, senha, periodo) 
VALUES (6,"00000000006","Nicolas Chagas","Eletrônica","Araxá","nicolas@gmail.com","123","serie 2");


INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito) 
VALUES (1,"Cemig","Vagas Setor Manutenção",80,3,"Cursando Eletrotécnica, serie 3");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito) 
VALUES (2,"Copasa","Vagas Setor de TI",80,2,"Cursando Informática, serie 3");

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito) 
VALUES (3,"Santa Clara","Vagas Setor de Engenharia",80,10,"Cursando Engenharia Mecânica, periodo 3");


INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES (1,"Pipa","Cristiano Amaral Maffort",
        "Trabalho em sistema de coordenação de oportunidades acadêmicas.",80,10,"Informática serie 2");
		

INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES (2,"Ficando Rico","Lucia Próspera",
        "Projeto em matemática financeira.",50,2,"Ser aluno do Cefet.");		
		
		

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (1,1);

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (2,1);

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES (2,2);



INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (1,1);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (3,1);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES (4,2);