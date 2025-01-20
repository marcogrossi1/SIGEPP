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

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Professor;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/perfilPublicoProfessor")
public class PerfilPublicoProfessorController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping
	public String mostraPerfilProfessor(@RequestParam("id") Long professorId, Model model, Principal principal) 
	throws Exception {
		try(Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);

			Professor p = ProfessorDao.get(conn, professorId);
			
			model.addAttribute("professor", p);

			if (u.getRole().equals("Professor")) {
                return "perfilPublicoProfessor";
            }
    
            else if (u.getRole().equals("Administrador")) {
                Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", adm);
                model.addAttribute("listaEmpresas", empresas);

                return "perfilPublicoProfessor";
            }
    
            else if (u.getRole().equals("Empresa")) {
                return "perfilPublicoProfessor";
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
}