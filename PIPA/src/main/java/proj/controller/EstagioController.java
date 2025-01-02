package proj.controller;

import java.util.ArrayList;
import java.sql.Connection;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;

import proj.model.Aluno;
import proj.model.Estagio;

@Controller
@RequestMapping("/estagios")
public class EstagioController {
        @Autowired
        private HDataSource ds;
        
        @GetMapping
	public String mostraEstagios(Model model, Principal principal)
	throws Exception{
		
		try(Connection conn = ds.getConnection())
		{
                        Aluno a = AlunoDao.getByCfp(conn, principal.getName());
			ArrayList<Estagio> estagioList = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			model.addAttribute("estagioList", estagioList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return mostraPaginaDeErro();
		}
		return "homeEstagios";
	}
        public String mostraPaginaDeErro() {
		return "erro";
	}
}
