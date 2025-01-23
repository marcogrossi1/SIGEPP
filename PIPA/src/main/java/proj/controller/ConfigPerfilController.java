package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Usuario;
import proj.security.SecurityConfig;

@Controller
@RequestMapping("/configPerfil")
public class ConfigPerfilController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping("/contaConfigPerfil")
	public String contaConfigPerfil(@RequestParam("id") Long Usuario_id, Model model, Principal principal) 
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

        	    return "/configPerfil/contaConfigPerfil";
        	}
        	
        	else if (u.getRole().equals("Professor")) {
        		Professor p = ProfessorDao.get(conn, Usuario_id);
        		return "/configPerfil/contaConfigPerfil";
        	}
        	else if (u.getRole().equals("Administrador")) {
        		Administrador admin = AdministradorDao.get(conn, Usuario_id);
        		return "/configPerfil/contaConfigPerfil";
        	}
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
	
	@PostMapping("/atualizar-config-conta")
	public String atualizarDadosConfig(
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
	        Usuario u = UsuarioDao.getByNome(conn, principal.getName());
	        
	        /*String senhaCriptografada = senha;
	        u.setSenha(senhaCriptografada);
	        UsuarioDao.updateForSenha(conn, UsuarioId, senhaCriptografada);*/
	        //POR ENQUANTO!

	        if (u.getRole().equals("Aluno")) {
	            Aluno a = AlunoDao.get(conn, UsuarioId);
	            
	            if (nome != null && !nome.isEmpty()) {
	                a.setNome(nome);
	            }
	            if (cpf != null && !cpf.isEmpty()) {
	                a.setCpf(cpf);
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
	            
	            //PRA TESTAR!!! "DEBUG"
	            System.out.println("Valores recebidos:");
	            System.out.println("Nome: " + nome);
	            System.out.println("CPF: " + cpf);
	            System.out.println("Curso: " + curso);
	            System.out.println("Campus: " + campus);
	            System.out.println("Email: " + email);
	            System.out.println("Período: " + periodo);
	            System.out.println("Telefone: " + telefone);
	            System.out.println("Senha: " + senha);
	            
	            AlunoDao.update(conn, a);
	            
	        }

	        conn.commit();
	        
	        return "redirect:/configPerfil/contaConfigPerfil?id=" + u.getId();
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Erro ao atualizar dados.");
	        return "erro";
	    }
	}
}