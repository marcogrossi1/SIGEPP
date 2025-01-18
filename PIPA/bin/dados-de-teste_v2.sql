set foreign_key_checks = 0;

truncate usuario;
truncate professor;
truncate empresa;
truncate aluno;
truncate estagio;
truncate projeto;
truncate aluno_has_estagio;
truncate aluno_has_projeto;


set foreign_key_checks = 1;



INSERT INTO usuario (id, nome, senha, role, descricao, banner_url, foto_perfil_url) 
VALUES 
(1, "00000000001", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(2, "00000000002", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(3, "00000000003", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(4, "00000000004", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(5, "00000000005", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(6, "00000000006", sha2('123', 512), "Aluno", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(7, "00000000007", sha2('123', 512), "Professor", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(8, "10000000000001", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(9, "10000000000002", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(10, "10000000000003", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(11, "10000000000004", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(12, "10000000000005", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(13, "10000000000006", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(14, "10000000000007", sha2('123', 512), "Empresa", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(15, "20000000000001", sha2('123', 512), "Administrador", "Sem descrição.", "../img/banner.png", "../img/foto-perfil-padrao.png"),
(16, "20000000000002",  sha2('123', 512), "Administrador", 'Sem descrição.', '../img/banner.png', '../img/foto-perfil-padrao.png');


INSERT INTO professor (id, nome, Usuario_id) 
VALUES 
(1,"Cristiano",7);

INSERT INTO empresa (id, nome, Usuario_id) 
VALUES 
(1,"Vivo",8);


INSERT INTO aluno (id, cpf, nome, curso, campus, email, periodo, usuario_id) 
VALUES 
(1,"00000000001","Thiago Figueiredo","Eletrônica","Nova Gameleira","thiago@gmail.com","serie 1", 1),
(2,"00000000002","Caio Figueiredo","Informática","Nova Gameleira","caio@gmail.com","serie 2", 2),
(3,"00000000003","Matheus Silva","Mecânica","Nova Suiça","matheus@gmail.com","serie 3", 3),
(4,"00000000004","Gabriel Tavares","Informática","Nova Gameleira","gabriel@gmail.com","serie 2", 4),
(5,"00000000005","Marco Grossi","Edificações","Nova Gameleira","marco@gmail.com","serie 2", 5),
(6,"00000000006","Nicolas Chagas","Eletrônica","Araxá","nicolas@gmail.com","serie 2", 6);

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito) 
VALUES 
(1,"Cemig","Vagas Setor Manutenção",80,3,"Cursando Eletrotécnica, serie 3"),
(2,"Copasa","Vagas Setor de TI",80,2,"Cursando Informática, serie 3"),
(3,"Santa Clara","Vagas Setor de Engenharia",80,10,"Cursando Engenharia Mecânica, periodo 3");


INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES 
(1,"Pipa","Cristiano Amaral Maffort", "Trabalho em sistema de coordenação de oportunidades acadêmicas.",80,10,"Informática serie 2"),
(2,"Ficando Rico","Lucia Próspera","Projeto em matemática financeira.",50,2,"Ser aluno do Cefet.");		
			
INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES 
(1,1),
(2,1),
(2,2);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES 
(1,1),
(3,1),
(4,2);
