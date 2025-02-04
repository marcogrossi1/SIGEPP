package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Usuario;
import proj.ui.model.PesquisaFiltrosAluno;
import proj.ui.model.PesquisaFiltrosUsuario;
import proj.ui.model.PesquisaNomeAluno;
import proj.ui.model.PesquisaNomeUsuario;

@Controller
@RequestMapping("/pesquisa-usuario")
public class PesquisaGeralController {
	
	@Autowired
	private HDataSource ds;

	@GetMapping("/listaausuarios")
	public String mostraPainelUsuarios(Model model, Principal principal) 
	throws SQLException
	{
		
		try(Connection conn = ds.getConnection()){
			ArrayList<Aluno> listaAlunos = AlunoDao.list(conn);
			ArrayList<Professor> listaProfessores = ProfessorDao.list(conn);
			ArrayList<Administrador> listaAdministradores = AdministradorDao.list(conn);

			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			String nome = null;

			if (u.getRole().equals("Professor")) {
				Professor x = ProfessorDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
				model.addAttribute("professor", x);
			} else if(u.getRole().equals("Administrador")) {
				Administrador x = AdministradorDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
				model.addAttribute("administrador", x);
			} else if(u.getRole().equals("Aluno")) {
				Aluno x = AlunoDao.getByUsuario_id(conn, u.getId());
				nome = x.getNome();
				model.addAttribute("aluno", x);
			}
			System.out.println("NOME: "+ nome);
			
			model.addAttribute("nome", nome);
			model.addAttribute("usuario", u);
			
			List<Object> usuarios = new ArrayList<>();
            usuarios.addAll(listaAlunos);
            usuarios.addAll(listaProfessores);
            usuarios.addAll(listaAdministradores);
            
			model.addAttribute("listaUsuarios", usuarios);
		}

		model.addAttribute("pesqNome", new PesquisaNomeUsuario());
		model.addAttribute("pesqFiltro", new PesquisaFiltrosUsuario());
		return "pesquisaUsuario";
	}
	
	
	@PostMapping("/pesquisaNomeUsuario")
	public String pesquisaNomeUsuario(Model model, Principal principal, 
	        @ModelAttribute("pesqNome") PesquisaNomeUsuario pesqNome,
	        BindingResult result) 
	throws SQLException {
	    try (Connection conn = ds.getConnection()) {

	        Usuario u = UsuarioDao.getByNome(conn, principal.getName());
	        String nome = null;

	        System.out.println(u.getRole());

	        switch (u.getRole()) {
	            case "Professor":
	                nome = ProfessorDao.getByUsuario_id(conn, u.getId()).getNome();
	                break;
	            case "Administrador":
	                nome = AdministradorDao.getByUsuario_id(conn, u.getId()).getNome();
	                break;
	            case "Aluno":
	                nome = AlunoDao.getByUsuario_id(conn, u.getId()).getNome();
	                break;
	        }

	        System.out.println("NOME: " + nome);

	        model.addAttribute("nome", nome);
	        model.addAttribute("usuario", u);
	        model.addAttribute("pesqFiltro", new PesquisaFiltrosUsuario());

	        List<Object> usuarios = new ArrayList<>();

	        if (pesqNome == null || pesqNome.getNome() == null || pesqNome.getNome().trim().isEmpty()) {
	            // Se não foi passado nenhum nome, carrega todos os usuários
	            usuarios.addAll(AlunoDao.list(conn));
	            usuarios.addAll(AdministradorDao.list(conn));
	        } else {
	            // Se um nome foi fornecido, filtra os usuários
	            String nomePesquisa = pesqNome.getNome().trim();
	            usuarios.addAll(AlunoDao.findByName(conn, nomePesquisa));
	            usuarios.addAll(AdministradorDao.findByName(conn, nomePesquisa));

	            if (usuarios.isEmpty()) {
	                result.rejectValue("nome", "nome", "Nenhum resultado encontrado para [" + nomePesquisa + "]");
	            }
	        }

	        model.addAttribute("listaUsuarios", usuarios);
	        model.addAttribute("pesqNome", pesqNome);
	    }

	    return "pesquisaUsuario";
	}
}
