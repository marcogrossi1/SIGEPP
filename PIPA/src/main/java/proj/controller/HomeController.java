package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Usuario;

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
	public String login() {
		return "login";
	}
	
	@GetMapping("/cadastroAluno")
	public String mostraPaginaDeCadastro() {
		return "cadastroAluno";
	}
	
	@PostMapping("/criarAluno")
	public String criaNovoAluno(
		@RequestParam("id") Long UsuarioId, 
	    @RequestParam(value = "nome", required = false) String nome,
	    @RequestParam(value = "cpf", required = false) String cpf,
	    @RequestParam(value = "curso", required = false) String curso,
	    @RequestParam(value = "campus", required = false) String campus,
	    @RequestParam(value = "email", required = false) String email,
	    @RequestParam(value = "periodo", required = false) String periodo,
	    @RequestParam(value = "telefone", required = false) String telefone,
	    @RequestParam(value = "senha", required = false) String senha,
	    Principal principal, Model model) throws Exception {
	    try (Connection conn = ds.getConnection()) {
	        UsuarioDao.insert(conn, u);
	    	AlunoDao.insert(conn, a);

	        if (senha != null && !senha.isEmpty()) {
		        String senhaCriptografada = DigestUtils.sha512Hex(senha);
		        u.setSenha(senhaCriptografada);
		        UsuarioDao.updateForSenha(conn, UsuarioId, senhaCriptografada);
	        }
	        
	        if (u.getRole().equals("Aluno")) {
	            Aluno a = AlunoDao.get(conn, UsuarioId);
	            
	            if (nome != null && !nome.isEmpty()) {
	                a.setNome(nome);
	            }
	            
	            if (curso != null && !curso.isEmpty()) {
	                a.setCurso(curso);
	            }
	            if (campus != null && !campus.isEmpty()) {
	                a.setCampus(campus);
	            }
	            if (email != null && !email.isEmpty()) {
	                a.setEmail(email);
	            }
	            if (periodo != null && !periodo.isEmpty()) {
	                a.setPeriodo(periodo);
	            }
	            if (telefone != null && !telefone.isEmpty()) {
	                a.setTelefone(telefone);
	            }
	            
	            if (cpf != null && !cpf.isEmpty()) {
	                a.setCpf(cpf);
	                u.setNome(cpf);
	            }
	            
	            AlunoDao.update(conn, a);
                UsuarioDao.update(conn, u);
                conn.commit();
	            
	            System.out.println("Valores recebidos:");
	            System.out.println("Nome: " + nome);
	            System.out.println("CPF: " + cpf);
	            System.out.println("Curso: " + curso);
	            System.out.println("Campus: " + campus);
	            System.out.println("Email: " + email);
	            System.out.println("Período: " + periodo);
	            System.out.println("Telefone: " + telefone);
	            System.out.println("Senha: " + senha);
	            
	        }
		return "redirect:/aluno";
	}
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}	
}
















