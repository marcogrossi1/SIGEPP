package proj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.web.bind.annotation.ModelAttribute;

import proj.dao.HDataSource;
import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.SecaoDao;
import proj.dao.SeguidoresDao;
import proj.dao.TopicoDao;
import proj.dao.UsuarioDao;
import proj.dao.EmpresaDao;
import proj.dao.EstagioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Projeto;
import proj.model.Secao;
import proj.model.Topico;
import proj.model.Usuario;
import proj.model.Empresa;
import proj.model.Estagio;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String mostraPerfilPessoal() {
		return "home";
	}
	
	@GetMapping("/login")
	public String mostrarLogin(Model model, Principal principal) 
	throws Exception {
		try(Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			
	    	model.addAttribute("usuarioId", u.getId());
	    	return "login";
		}
		
		catch(Exception e) {
			return "erro";
		}
	}
	
	@GetMapping("/cadastroAluno")
	public String mostrarCadastroAluno(@RequestParam("id") Long Usuario_id, Model model, Principal principal) 
	throws Exception {
		try(Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);
        	
        	if (u.getRole().equals("Aluno")) {
        	    Aluno a = AlunoDao.get(conn, Usuario_id);
        	    model.addAttribute("aluno", a);
        	    
        	    String nome = a.getNome();
        	    String cpf = a.getCpf();
        	    String curso = a.getCurso();
        	    String campus = a.getCampus();
        	    String email = a.getEmail();
        	    String periodo = a.getPeriodo();
        	    String telefone = a.getTelefone();
        	    String senha = u.getSenha();

        	    model.addAttribute("nome", nome);
        	    model.addAttribute("cpf", cpf);
        	    model.addAttribute("curso", curso);
        	    model.addAttribute("campus", campus);
        	    model.addAttribute("email", email);
        	    model.addAttribute("periodo", periodo);
        	    model.addAttribute("telefone", telefone);
        	    model.addAttribute("senha", senha);

        	    return "cadastroAluno";
        	}
        	
        	/*else if (u.getRole().equals("Professor")) {
        		Professor p = ProfessorDao.get(conn, Usuario_id);
        		return "/configPerfil/contaConfigPerfil";
        	}
        	else if (u.getRole().equals("Administrador")) {
        		Administrador admin = AdministradorDao.get(conn, Usuario_id);
        		return "/configPerfil/contaConfigPerfil";
        	}*/
			else {
				return mostraPaginaDeErro(model, "Você não tem permissão para acessar esta página.");
			}
		}
		
		catch(Exception e) {
			return "erro";
		}
	}
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}
}
