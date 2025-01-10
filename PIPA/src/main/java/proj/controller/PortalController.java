package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.NotFoundException;
import proj.dao.ProjetoDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;

@Controller
@RequestMapping("/")
public class PortalController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String mostraPortal(Model model, Principal principal)
	throws Exception{
		
		try(Connection conn = ds.getConnection())
		{
			Aluno a = AlunoDao.getByCfp(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return mostraPaginaDeErro();
		}

		return "portal";
	}	
	
	public String mostraPaginaDeErro() {
		return "erro";
	}

	@GetMapping("/testdao")
	@ResponseBody
	public String test() {
		
		try(Connection conn = ds.getConnection())
		{
			Aluno a = AlunoDao.get(conn, 1);
			System.out.println(a);
			
			Projeto p = ProjetoDao.get(conn, 1);
			System.out.println(p);
			
			Estagio e = EstagioDao.get(conn, 1);
			System.out.println(e);
		}
		catch (NotFoundException e) {
			System.out.println("/usuario nao encontrado");
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		
		
		return "ok";

	}
	
}
