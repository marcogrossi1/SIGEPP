SET foreign_key_checks = 0;

TRUNCATE TABLE aluno_has_estagio;
TRUNCATE TABLE aluno_has_projeto;
TRUNCATE TABLE empresa_has_estagio;
TRUNCATE TABLE estagio;
TRUNCATE TABLE projeto;
TRUNCATE TABLE aluno;
TRUNCATE TABLE professor;
TRUNCATE TABLE administrador;
TRUNCATE TABLE empresa;
TRUNCATE TABLE usuario;
TRUNCATE TABLE professor_has_projeto;

SET foreign_key_checks = 1;

INSERT INTO usuario (id, nome, senha, role) 
VALUES 
(1, "00000000001", sha2('123', 512), "Aluno"),
(2, "00000000002", sha2('123', 512), "Aluno"),
(3, "00000000003", sha2('123', 512), "Aluno"),
(4, "00000000004", sha2('123', 512), "Aluno"),
(5, "00000000005", sha2('123', 512), "Aluno"),
(6, "00000000006", sha2('123', 512), "Aluno"),
(7, "00000000007", sha2('123', 512), "Professor"),
(8, "10000000000001", sha2('123', 512), "Empresa"),
(9, "10000000000002", sha2('123', 512), "Empresa"),
(10, "10000000000003", sha2('123', 512), "Empresa"),
(11, "10000000000004", sha2('123', 512), "Empresa"),
(12, "10000000000005", sha2('123', 512), "Empresa"),
(13, "10000000000006", sha2('123', 512), "Empresa"),
(14, "10000000000007", sha2('123', 512), "Empresa"),
(15, "20000000000001", sha2('123', 512), "Administrador"),
(16, "20000000000002", sha2('123', 512), "Administrador");

INSERT INTO professor (id, nome, Usuario_id, telefone, email, cpf) 
VALUES 
(1, "CristianoPicasso", 7, "99999-8586", "cristiano@gmail.com", '00000000081');

INSERT INTO administrador (id, cpf, nome, campus, email, Usuario_id, telefone)
VALUES
(1, "20000000000001", "Marco Mestre", "Nova Gameleira", "marco@gmail.com", 15, "99999-8365"),
(2, "20000000000002", "Buzz Fraco", "Nova Gameleira", "buzz@gmail.com", 16, "99999-8888");

INSERT INTO empresa (id, cnpj, nome, endereco, website, area, telefone, email, Usuario_id) 
VALUES 
(1, "10000000000001", "Vivo", "Rua Viva, Centro, Betim - MG", "www.vivo.com", "Telefonia", "99999-8888", "vivo@gmail.com", 8),
(2, "10000000000002", "Cemig", "Rua da Bahia", "www.cemig.com.br", "Energia", "0800 000 0001", "contato@cemig.br", 9),
(3, "10000000000003", "Petrobras", "Avenida Chile", "www.petrobras.com.br", "Petróleo", "0800 000 0002", "contato@petrobras.com.br", 10),
(4, "10000000000004", "Vale", "Praia de Botafogo", "www.vale.com", "Mineração", "0800 000 0003", "contato@vale.com", 11),
(5, "10000000000005", "Embraer", "Avenida Brigadeiro Faria Lima", "www.embraer.com.br", "Aeronáutica", "0800 000 0004", "contato@embraer.com.br", 12),
(6, "10000000000006", "Copasa", "Avenida do Contorno", "www.copasa.com.br", "Saneamento", "0800 000 0005", "contato@copasa.com.br", 13),
(7, "10000000000007", "Santa Clara", "Rua das Flores", "www.santaclara.com.br", "Alimentos", "0800 000 0006", "contato@santaclara.com.br", 14);

INSERT INTO aluno (id, cpf, nome, curso, campus, email, periodo, usuario_id, telefone) 
VALUES 
(1, "00000000001", "Thiago Figueiredo", "Eletrônica", "Nova Gameleira", "thiago@gmail.com", "serie 1", 1, '00000-0016'),
(2, "00000000002", "Caio Figueiredo", "Informática", "Nova Gameleira", "caio@gmail.com", "serie 2", 2, '00000-0015'),
(3, "00000000003", "Matheus Silva", "Mecânica", "Nova Suiça", "matheus@gmail.com", "serie 3", 3, '00000-0014'),
(4, "00000000004", "Gabriel Tavares", "Informática", "Nova Gameleira", "gabriel@gmail.com", "serie 2", 4, '00000-0013'),
(5, "00000000005", "Marco Grossi", "Edificações", "Nova Gameleira", "marco@gmail.com", "serie 2", 5, '00000-0012'),
(6, "00000000006", "Nicolas Chagas", "Eletrônica", "Araxá", "nicolas@gmail.com", "serie 2", 6, '00000-0011');

INSERT INTO estagio (id, empresa, descricao, carga_horaria, vagas, requisito, salario, documentos) 
VALUES 
(1,"Cemig","Vagas Setor Manutenção",80,3,"Cursando Eletrotécnica, serie 3", "1550", "RG"),
(2,"Copasa","Vagas Setor de TI",80,2,"Cursando Informática, serie 3", "1650", "RG,Certificado de conclusão do Ensino Médio"),
(3,"Santa Clara","Vagas Setor de Engenharia",80,10,"Cursando Engenharia Mecânica, periodo 3", "1700", NULL),
(4,"Vivo","Vagas Setor de Telemarketing",80,5,"Ensino Fundamental Completo", "2000", NULL),
(5,"Petrobras","Vagas Setor de Pesquisa",80,5,"Cursando Engenharia Química, serie 3", "1400", "RG,Certificado de conclusão do Ensino Fundamental"),
(6,"Vale","Vagas Setor de Logística",80,4,"Cursando Engenharia de Produção, serie 3", "A combinar", "RG,Certificado de conclusão do Ensino Médio,Certificado de conclusão do Ensino Fundamental"),
(7,"Embraer","Vagas Setor de Aeronáutica",80,6,"Cursando Engenharia Aeronáutica, serie 3", "1600", NULL),
(8,"Embraer","Vagas Setor de Desenvolvimento",80,5,"Cursando Engenharia Aeronáutica, serie 4", "1550", NULL);


INSERT INTO projeto (id, nome, responsavel, descricao, carga_horaria, vagas, requisito) 
VALUES 
(1,"Pipa","Cristiano Amaral Maffort", "Trabalho em sistema de coordenação de oportunidades acadêmicas.",80,10,"Informática serie 2"),
(2,"Ficando Rico","Lucia Próspera","Projeto em matemática financeira.",50,2,"Ser aluno do Cefet."),	
(3, "Plataforma de Ensino Online", "Cristiano Amaral Maffort", "Desenvolvimento de uma plataforma para ensino a distância, focada em acessibilidade e interatividade para alunos de diversos níveis.", 120, 15, "Conhecimento básico em tecnologias web."),
(4, "Sistema de Monitoramento de Desempenho Acadêmico", "Cristiano Amaral Maffort", "Criação de um sistema de monitoramento para acompanhar o desempenho dos alunos, com relatórios detalhados e alertas para professores.", 100, 12, "Noções de bancos de dados e programação orientada a objetos.");

INSERT INTO aluno_has_estagio (aluno_id, estagio_id) 
VALUES 
(1, 1),
(2, 1),
(2, 2);

INSERT INTO aluno_has_projeto (aluno_id, projeto_id) 
VALUES 
(1, 1),
(3, 1),
(4, 2);

INSERT INTO empresa_has_estagio (empresa_id, estagio_id) 
VALUES 
(1, 1),
(4, 2),
(6, 3),
(2, 4),
(3, 5),
(4, 6),
(4, 7);

INSERT INTO professor_has_projeto (professor_id, projeto_id) 
VALUES 
(1,1),
(1,3),
(1,4);


insert into Seguidores (seguindo_id, seguidor_id)
values 
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(3, 1),
(4, 1);

INSERT INTO candidatura (candidato_id, oportunidade_id, mensagem, data_aplicacao, status) 
VALUES 
(1, 2, "Estou muito interessado nesta oportunidade e acredito que posso contribuir muito.", NOW(), 'EM_ANDAMENTO'), 
(2, 1, "Gostaria de me candidatar a esta vaga, pois tenho experiência na área.", NOW(), 'VALIDADA'), 
(3, 3, "Acredito que este projeto é uma ótima oportunidade para aplicar meus conhecimentos.", NOW(), 'INVALIDADA');  
