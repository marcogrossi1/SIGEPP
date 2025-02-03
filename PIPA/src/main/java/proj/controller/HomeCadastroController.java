package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Usuario;

@Controller
public class HomeCadastroController {
	
	@Autowired
	private HDataSource ds;

    @GetMapping("/cadastro")
    public String showHomeCadastro() {
        return "homeCadastro"; 
    }

    @GetMapping("/cadastroAluno")
    public String showCadastroAluno() {
        return "cadastroAluno";
    }
    @GetMapping("/cadastroProfessor")
    public String showCadastroProfessor() {
        return "cadastroProfessor"; 
    }

    @GetMapping("/cadastroEmpresa")
    public String showCadastroEmpresa() {
        return "cadastroEmpresa"; 
    }
    @GetMapping("/cadastroAdministrador")
    public String showCadastroAdministrador() {
        return "cadastroAdministrador"; 
    }
    
    @PostMapping("/criarAluno")
	public String criaNovoAluno(
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
	        Aluno a = new Aluno();
	        Usuario u = new Usuario();
	        u.setRole("Aluno");

	        if (senha != null && !senha.isEmpty()) {
	            String senhaCriptografada = DigestUtils.sha512Hex(senha);
	            u.setSenha(senhaCriptografada);
	        }

	        if (u.getRole().equals("Aluno")) {
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
	            
	            a.setDescricaoPerfil("Sem descrição.");
	            /*a.setFotoPerfil(null);
	            a.setBannerPerfil(null);*/
	            
	            if (cpf != null && !cpf.isEmpty()) {
	                a.setCpf(cpf);
	                u.setNome(cpf);
	            }

	            UsuarioDao.insert(conn, u);
	            
	            a.setUsuario_id(u.getId());

	            AlunoDao.insert(conn, a);
	            
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
	    }
	    return "aluno";
	    //AQUI Q TÁ DANDO ERRO DO REDIRECT
	}
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}	
}