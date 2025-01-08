package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.EstagioDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;

@Controller
public class AdministradorController {

    @Autowired
	private HDataSource ds;
    
    @RequestMapping("/editar-estagio")
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

            return "editarEstagioAdm";
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
}