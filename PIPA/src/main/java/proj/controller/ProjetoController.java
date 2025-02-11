package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Usuario;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping
	public String mostraHomeProjetos(Model model, Principal principal)
	throws Exception {
		try(Connection conn = ds.getConnection()) {			
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);

			return "homeProjetosDePesquisa";
		}

		catch(Exception e) {
			return "erro";
		}
	}
}