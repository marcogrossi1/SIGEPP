package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String mostraPortal(Model model, Principal principal)
	throws Exception{
		
		//try(
			Connection conn = ds.getConnection();
		//	)
		//{
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (u.getRole().equals("Aluno") == false)
			{
				return mostraPaginaDeErro(model , "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);
		//}
		//catch(Exception e) {
		//	e.printStackTrace();
		//	return mostraPaginaDeErro(model , "Erro interno na aplicação!.");
		//}

		return "aluno/home";
	}	
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}

	
	@GetMapping("/estagios")
	public String mostraHomeEstagios() {
		return "aluno/estagios";
	}

	
	@GetMapping("/projetos")
	public String mostraHomeProjetos() {
		return "aluno/projetos";
	}
	
	@GetMapping("/perfil")
	public String mostraPerfilPessoal() {
		return "aluno/perfil";
	}
}
