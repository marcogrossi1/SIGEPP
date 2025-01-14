package proj.controller;

import java.security.Principal;
import java.sql.Connection;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> Asafe

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.dao.HDataSource;
<<<<<<< HEAD
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Projeto;
=======
import proj.dao.UsuarioDao;
>>>>>>> Asafe
import proj.model.Usuario;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping
	public String mostraHomeProjetos(Model model, Principal principal)
	throws Exception {
<<<<<<< HEAD
	    try (Connection conn = ds.getConnection()) {
	        Usuario u = UsuarioDao.getByNome(conn, principal.getName());
	        model.addAttribute("usuario", u);

	        ArrayList<Projeto> projetos = ProjetoDao.list(conn);
	        model.addAttribute("projetos", projetos); 

	        return "homeProjetosDePesquisa";
	    } catch (Exception e) {
	        return "erro";
	    }
	}

=======
		try(Connection conn = ds.getConnection()) {			
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);

			return "homeProjetosDePesquisa";
		}

		catch(Exception e) {
			return "erro";
		}
	}
>>>>>>> Asafe
}