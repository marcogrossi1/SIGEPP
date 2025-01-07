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
import proj.dao.EmpresaDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Empresa;

@Controller
public class PerfilEmpresaController {

    @Autowired
	private HDataSource ds;
    
    @RequestMapping("/empresa")
	public String emiteCertificadoProjeto(@RequestParam("id") Long empresaId, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
	    	Aluno a = AlunoDao.getByCfp(conn, principal.getName());
		    ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		    ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());

            Empresa e = EmpresaDao.get(conn, empresaId);
		    ArrayList<Estagio> estagiosEmpresa = EmpresaDao.listEstagiosByEmpresaId(conn, e.getId());

            model.addAttribute("aluno", a);
		    model.addAttribute("estagios", estagios);
            model.addAttribute("projetos", projetos);

            model.addAttribute("empresa", e);   
            model.addAttribute("estagiosEmpresa", estagiosEmpresa);

            return "perfilEmpresa";
        }
        
        catch(Exception e) {
            return "erro";
        }
    }

    @RequestMapping("/encontra-empresa")
    public String encontraEmpresa(@RequestParam("nome") String empresaNome, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Aluno a = AlunoDao.getByCfp(conn, principal.getName());
            ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
            ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());

            Empresa e = EmpresaDao.getByNome(conn, empresaNome);
            ArrayList<Estagio> estagiosEmpresa = EmpresaDao.listEstagiosByEmpresaId(conn, e.getId());

            model.addAttribute("aluno", a);
            model.addAttribute("estagios", estagios);
            model.addAttribute("projetos", projetos);

            model.addAttribute("empresa", e);   
            model.addAttribute("estagiosEmpresa", estagiosEmpresa);

            return "perfilEmpresa";
        }
        
        catch(Exception e) {
            return "erro";
        }
    }    
}
