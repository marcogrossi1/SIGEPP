package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
	private HDataSource ds;
    
    @GetMapping
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
        //try(
            Connection conn = ds.getConnection();//) {
	        
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Estagio e = EstagioDao.get(conn, estagioId);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("estagio", e);

            return "administrador/editarEstagioAdm";
        //}
//  
        //catch(Exception e) {
        //    return "erro";
        //}
    }

    @GetMapping("/editar-projeto")
	public String editarProjeto(@RequestParam("n") Long projetoId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
	        
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Projeto e = ProjetoDao.get(conn, projetoId);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projeto", e);

            return "administrador/editarProjetoAdm";
        }
  
        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/deletar-estagio")
    public String deletarEstagio(@RequestParam("n") Long estagioId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            Estagio e = EstagioDao.get(conn, estagioId);

            EstagioDao.delete(conn, e.getId());

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("estagio", e);

            return "/administrador/estagios";
        }

        catch(Exception e) {
            return mostraPaginaDeErro(model, "Este estágio já pertence à uma empresa e, portanto, não pode mais ser deletado.");
        }
    }

    @RequestMapping("/projetos")
    public String listaProjetos(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/projetos";
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
    public String mostraPerfilPessoal(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());
            
            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);
            
            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/perfil";
        }

        catch(Exception e) {
            return "erro";
        }
    }
    
    @RequestMapping("/estagios")
    public String listaEstagios(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Projeto> projetos = AdministradorDao.listProjetos(conn);
            ArrayList<Estagio> estagios = AdministradorDao.listEstagios(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("projetos", projetos);
            model.addAttribute("estagios", estagios);

            return "administrador/estagios";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/listar-empresas")
    public String listaEmpresas(Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("listaEmpresas", empresas);

            return "administrador/empresas";
        }

        catch(Exception e) {
            return "erro";
        }
    }

    @GetMapping("/listar-alunos")
    public String listaAlunos(Model model, Principal principal) throws Exception {
        //try(
            Connection conn = ds.getConnection();//) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            Administrador a = AdministradorDao.getByCpf(conn, principal.getName());

            ArrayList<Aluno> alunos = AdministradorDao.listAlunos(conn);

            model.addAttribute("usuario", u);
            model.addAttribute("administrador", a);
            model.addAttribute("listaAlunos", alunos);

            return "administrador/alunos";
        //}

        //catch(Exception e) {
        //    return "erro";
        //}
    }
    
    
    @PostMapping("/deletar-aluno")
    public String deletarAluno(@RequestParam("n") Long alunoId, Model model, Principal principal) 
    throws Exception 
    {
        try(Connection conn = ds.getConnection())
        {
            Aluno al = AlunoDao.get(conn, alunoId);
            AlunoDao.delete(conn, al.getId());
            conn.commit();
            return "redirect:/administrador/listar-alunos";
        }
        catch(Exception e) {
            return mostraPaginaDeErro(model, "Não foi possível deletar.");
        }
    }
}


