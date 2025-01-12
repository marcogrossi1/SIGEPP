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



INSERT INTO usuario (id, nome, senha, role) 
VALUES 
(1,"00000000001",sha2('123', 512),"Aluno"),
(2,"00000000002",sha2('123', 512),"Aluno"),
(3,"00000000003",sha2('123', 512),"Aluno"),
(4,"00000000004",sha2('123', 512),"Aluno"),
(5,"00000000005",sha2('123', 512),"Aluno"),
(6,"00000000006",sha2('123', 512),"Aluno"),
(7,"00000000007",sha2('123', 512),"Professor"),
(8,"10000000000001",sha2('123', 512),"Empresa"),
(9,"10000000000002",sha2('123', 512),"Empresa"),
(10,"10000000000003",sha2('123', 512),"Empresa"),
(11,"10000000000004",sha2('123', 512),"Empresa"),
(12,"10000000000005",sha2('123', 512),"Empresa"),
(13,"10000000000006",sha2('123', 512),"Empresa"),
(14,"10000000000007",sha2('123', 512),"Empresa");
(15,"20000000000001",sha2('123', 512),"Administrador");
(16,"20000000000002",sha2('123', 512),"Administrador");

INSERT INTO professor (id, nome, Usuario_id) 
VALUES 
(1,"Cristiano",7);

INSERT INTO administrador (id, cpf, nome, campus, email, Usuario_id)
VALUES
(1, "20000000000001", "Marco Mestre", "Nova Gameleira", "marco@gmail.com", 15);

INSERT INTO administrador (id, cpf, nome, campus, email, Usuario_id)
VALUES
(2, "20000000000002", "Buzz Fraco", "Nova Gameleira", "buzz@gmail.com", 16);

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email, Usuario_id) 
VALUES 
(1, "10000000000001", "Vivo", "Rua Viva, Centro, Betim - MG", "www.vivo.com", "telefonia", "99999-8888", "vivo@gmail.com", 8),
(2, "10000000000002", "Cemig", "Rua da Bahia", "www.cemig.com.br", "Energia", "0800 000 0001", "contato@cemig.br", 9),
(3,"10000000000003","Petrobras","Avenida Chile","www.petrobras.com.br","Petróleo","0800 000 0002","contato@petrobras.com.br", 10),
(4,"10000000000004","Vale","Praia de Botafogo","www.vale.com","Mineração","0800 000 0003","contato@vale.com", 11),
(5,"10000000000005","Embraer","Avenida Brigadeiro Faria Lima","www.embraer.com.br","Aeronáutica","0800 000 0004","contato@embraer.com.br", 12),
(6,"10000000000006","Copasa","Avenida do Contorno","www.copasa.com.br","Saneamento","0800 000 0005","contato@copasa.com.br", 13),
(7,"10000000000007","Santa Clara","Rua das Flores","www.santaclara.com.br","Alimentos","0800 000 0006","contato@santaclara.com.br", 14);


INSERT INTO aluno (id, cpf, nome, curso, campus, email, periodo, usuario_id) 
VALUES 
(1,"00000000001","Thiago Figueiredo","Eletrônica","Nova Gameleira","thiago@gmail.com","serie 1", 1),
(2,"00000000002","Caio Figueiredo","Informática","Nova Gameleira","caio@gmail.com","serie 2", 2),
(3,"00000000003","Matheus Silva","Mecânica","Nova Suiça","matheus@gmail.com","serie 3", 3),
(4,"00000000004","Gabriel Tavares","Informática","Nova Gameleira","gabriel@gmail.com","serie 2", 4),
(5,"00000000005","Marco Grossi","Edificações","Nova Gameleira","marco@gmail.com","serie 2", 5),
(6,"00000000006","Nicolas Chagas","Eletrônica","Araxá","nicolas@gmail.com","serie 2", 6);

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario) 
VALUES 
(1,"Cemig","Vagas Setor Manutenção",80,3,"Cursando Eletrotécnica, serie 3", "1550"),
(2,"Copasa","Vagas Setor de TI",80,2,"Cursando Informática, serie 3", "1650"),
(3,"Santa Clara","Vagas Setor de Engenharia",80,10,"Cursando Engenharia Mecânica, periodo 3", "1700"),
(4,"Vivo","Vagas Setor de Telemarketing",80,5,"Ensino Fundamental Completo", "2000"),
(5,"Petrobras","Vagas Setor de Pesquisa",80,5,"Cursando Engenharia Química, serie 3", "1400"),
(6,"Vale","Vagas Setor de Logística",80,4,"Cursando Engenharia de Produção, serie 3", "A combinar"),
(7,"Embraer","Vagas Setor de Aeronáutica",80,6,"Cursando Engenharia Aeronáutica, serie 3", "1600"),
(8,"Embraer","Vagas Setor de Desenvolvimento",80,5,"Cursando Engenharia Aeronáutica, serie 4", "1550");


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

INSERT INTO empresa_has_estagio (empresa_id, estagio_id) 
VALUES 
(1,1),
(4,2),
(6,3),
(2,4),
(3,5),
(4,6),
(4,7);
