package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.dao.EstagioDao;
import proj.dao.AdministradorDao;

import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;
import proj.model.Administrador;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
	private HDataSource ds;
    
    @GetMapping("")
	public String mostraPortal(Model model, Principal principal) throws Exception {		
		//try(
            Connection conn = ds.getConnection();//)
		//{
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (u.getRole().equals("Administrador") == false)
			{
				return mostraPaginaDeErro(model , "Usuário não é um Administrador!.");
			}
			
            Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
			
            model.addAttribute("usuario", u);
            model.addAttribute("administrador", adm);
		//}
		//catch(Exception e) {
		//	e.printStackTrace();
		//	return mostraPaginaDeErro(model , "Erro interno na aplicação!.");
		//}

		return "administrador/home";
	}	
	
    @GetMapping("/editar-estagio")
	public String editarEstagio(@RequestParam("n") Long estagioId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
	        Aluno a = AlunoDao.getByCpf(conn, principal.getName());
		    ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		    ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());

            Estagio e = EstagioDao.get(conn, estagioId);

            model.addAttribute("aluno", a);
		    model.addAttribute("estagios", estagios);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagio", e);

            return "administrador/editarEstagioAdm";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/deletar-estagio")
    public String deletarEstagio(@RequestParam("n") Long estagioId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Aluno a = AlunoDao.getByCpf(conn, principal.getName());
            ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
            ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());

            Estagio e = EstagioDao.get(conn, estagioId);

            model.addAttribute("aluno", a);
            model.addAttribute("estagios", estagios);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagio", e);

            return "deletarEstagioAdm";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}

    @GetMapping("/perfil")
    public String mostraPerfilPessoal() {
        return "administrador/perfil";
    }
}