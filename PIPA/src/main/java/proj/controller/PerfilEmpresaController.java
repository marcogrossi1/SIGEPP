package proj.controller;

import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.EmpresaDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;
import proj.model.Empresa;

@Controller
public class PerfilEmpresaController {

    @Autowired
	private HDataSource ds;
    
    @RequestMapping("/perfil-empresa")
	public String mostraPerfilEmpresa(@RequestParam("id") Long empresaId, Model model, Principal principal) throws Exception {
        //try(
            Connection conn = ds.getConnection();//) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());   

            Empresa e = EmpresaDao.get(conn, empresaId);
            ArrayList<Estagio> estagiosEmpresa = EmpresaDao.listEstagiosByEmpresaId(conn, e.getId());
            
            model.addAttribute("empresa", e);   
            model.addAttribute("estagiosEmpresa", estagiosEmpresa);
            model.addAttribute("usuario", u);
            
            if (u.getRole().equals("Aluno")) {
                Aluno a = AlunoDao.getByCpf(conn, principal.getName());
                ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		        ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
                
                model.addAttribute("aluno", a);
                model.addAttribute("estagios", estagios);
                model.addAttribute("projetos", projetos);

                return "perfilEmpresa";
            }
    
            else if (u.getRole().equals("Administrador")) {
                Administrador a = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", a);
                model.addAttribute("listaEmpresas", empresas);

                return "perfilEmpresa";
            }
    
            else if (u.getRole().equals("Empresa")) {
                return "perfilEmpresa";
            }
    
            else {
                return "login";
            }   

        }

        //catch(Exception e) {
        //    return "erro";
        //}
    //}

    @RequestMapping("/encontra-empresa")
    public String encontraEmpresa(@RequestParam("nome") String empresaNome, Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());

            Empresa e = EmpresaDao.getByNome(conn, empresaNome);
            ArrayList<Estagio> estagiosEmpresa = EmpresaDao.listEstagiosByEmpresaId(conn, e.getId());

            model.addAttribute("usuario", u);

            model.addAttribute("empresa", e);   
            model.addAttribute("estagiosEmpresa", estagiosEmpresa);

            if (u.getRole().equals("Aluno")) {
                Aluno a = AlunoDao.getByCpf(conn, principal.getName());
                ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		        ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
                
                model.addAttribute("aluno", a);
                model.addAttribute("estagios", estagios);
                model.addAttribute("projetos", projetos);

                return "perfilEmpresa";
            }
    
            else if (u.getRole().equals("Administrador")) {
                Administrador a = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", a);
                model.addAttribute("listaEmpresas", empresas);

                return "perfilEmpresa";
            }
    
            else if (u.getRole().equals("Empresa")) {
                return "perfilEmpresa";
            }
    
            else {
                return "login";
            }  
        }
        
        catch(Exception e) {
            return "erro";
        }
    }    
}
