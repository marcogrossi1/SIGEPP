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
import proj.dao.ProjetoDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;

@Controller
public class PerfilProjetoController {

    @Autowired
	private HDataSource ds;
    
    @RequestMapping("/encontra-projeto")
    public String encontraEmpresa(@RequestParam("nome") String projeto, Model model, Principal principal) throws Exception {
        //try(
            Connection conn = ds.getConnection();//) {
            Aluno a = AlunoDao.getByCpf(conn, principal.getName());
            ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
            ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());

            Projeto p = ProjetoDao.getByNome(conn, projeto);

            model.addAttribute("aluno", a);
            model.addAttribute("estagios", estagios);
            model.addAttribute("projetos", projetos);

            model.addAttribute("projeto", p);   

            return "perfilProjeto";
        //}
        //
        //catch(Exception e) {
        //    return "erro";
        //}
    }    
}
