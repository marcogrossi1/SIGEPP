package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Professor;
import proj.model.Usuario;
import proj.ui.model.PesquisaFiltrosAluno;
import proj.ui.model.PesquisaNomeAluno;

@Controller
@RequestMapping("/pesquisa")
public class PesquisaAlunoController {
	
	@Autowired
	private HDataSource ds;

	@GetMapping("/listaalunos")
	public String mostraPainelAlunos(Model model, Principal principal) 
	throws SQLException
	{
		
		try(Connection conn = ds.getConnection()){
			ArrayList<Aluno> listaAlunos = AlunoDao.list(conn);
			ArrayList<String> listaCursos = AlunoDao.listCursos(conn);
			ArrayList<String> listaCampus = AlunoDao.listCampus(conn);
			ArrayList<String> listaPeriodos = AlunoDao.listPeriodos(conn);
			

			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			String nome = null;
			
			if(u.getRole().equals("Empresa")) {
				Empresa x = EmpresaDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
			}
			else  {
				Professor x = ProfessorDao.getByUsuario_id(conn, u.getId());
				model.addAttribute("professor", x);
				nome = x.getNome();
			}
			System.out.println("NOME: "+nome);
			
			model.addAttribute("nome", nome);
			model.addAttribute("usuario", u);
			
			
			model.addAttribute("listaCursos", listaCursos);
			model.addAttribute("listaCampus", listaCampus);
			model.addAttribute("listaPeriodos", listaPeriodos);
			model.addAttribute("listaAlunos", listaAlunos);
		}

		model.addAttribute("pesqNome", new PesquisaNomeAluno());
		model.addAttribute("pesqFiltro", new PesquisaFiltrosAluno());
		return "pesquisa/pesquisaDeAlunos";
	}
	
	
	@PostMapping("/pesquisaNome")
	public String pesquisaNomeAluno(Model model, Principal principal, 
			@ModelAttribute("pesqNome") PesquisaNomeAluno pesqNome,
			BindingResult result) 
	throws SQLException
	{
		try(Connection conn = ds.getConnection()){
			
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			String nome = null;
			System.out.println(u.getRole());
			if(u.getRole().equals("Empresa")) {
				Empresa x = EmpresaDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
			}
			else {
				Professor x = ProfessorDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
			}
			System.out.println("NOME: "+nome);
			
			model.addAttribute("nome", nome);
			model.addAttribute("usuario", u);
			
			
			ArrayList<String> listaCursos = AlunoDao.listCursos(conn);
			ArrayList<String> listaCampus = AlunoDao.listCampus(conn);
			ArrayList<String> listaPeriodos = AlunoDao.listPeriodos(conn);
			
			model.addAttribute("listaCursos", listaCursos);
			model.addAttribute("listaCampus", listaCampus);
			model.addAttribute("listaPeriodos", listaPeriodos);
			model.addAttribute("pesqFiltro", new PesquisaFiltrosAluno());
			
			if (pesqNome == null || pesqNome.getNome() == null || pesqNome.getNome().trim().equals("")) 
			{
				ArrayList<Aluno> listaAlunos = AlunoDao.list(conn);
				
				model.addAttribute("listaAlunos", listaAlunos);
				model.addAttribute("pesqNome", new PesquisaNomeAluno());
			}
			else
			{
				ArrayList<Aluno> listaAlunos = AlunoDao.listByNome(conn,pesqNome.getNome());
				
				if (listaAlunos.isEmpty()) {
					result.rejectValue("nome", "nome", "Nenhum resultado encontrado para a pesquisa de ["+pesqNome.getNome()+"]"); 
				}
				
				model.addAttribute("listaAlunos", listaAlunos);
				model.addAttribute("pesqNome", pesqNome);
			}
		}
		
		return "pesquisa/pesquisaDeAlunos";		
	}
	
	@PostMapping("/pesquisaFiltro")
	public String pesquisaAlunoComFiltro (Model model, Principal principal,  
			@Valid @ModelAttribute("pesqFiltro") PesquisaFiltrosAluno filtro,
			BindingResult result) 
	throws SQLException
	{
		try(Connection conn = ds.getConnection())
		{
			ArrayList<String> listaCursos = AlunoDao.listCursos(conn);
			ArrayList<String> listaCampus = AlunoDao.listCampus(conn);
			ArrayList<String> listaPeriodos = AlunoDao.listPeriodos(conn);
			
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			String nome = null;
			if(u.getRole().equals("Empresa")) {
				Empresa x = EmpresaDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
			}
			else  {
				Professor x = ProfessorDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
			}
			System.out.println("NOME: "+nome);
			
			model.addAttribute("usuario", u);
			
			model.addAttribute("nome", nome);
			model.addAttribute("listaCursos", listaCursos);
			model.addAttribute("listaCampus", listaCampus);
			model.addAttribute("listaPeriodos", listaPeriodos);
			
			ArrayList<Aluno> listaAlunos = AlunoDao.listByCursoCampusPeriodo(conn, filtro.getCurso(), filtro.getCampus(), filtro.getPeriodo());
			
			if (listaAlunos.isEmpty()) {
				result.reject("lista-vazia", "Nenhum resultado encontrado para a pesquisa."); 
			}
			
			model.addAttribute("listaAlunos", listaAlunos);
		}
		
		model.addAttribute("pesqFiltro", filtro);
		model.addAttribute("pesqNome", new PesquisaNomeAluno());
		
		return "pesquisa/pesquisaDeAlunos";
	
	}
}
