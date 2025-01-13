package proj.controller;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.model.Aluno;

@Controller
@RequestMapping("/listaalunos")
public class PesquisaAlunoController {
	
	@Autowired
	private HDataSource ds;

	@GetMapping
	public String mostraPainelAlunos(Model model) {
		
		try(Connection conn = ds.getConnection()){
			ArrayList<Aluno> listaAlunos = AlunoDao.list(conn);
			
			model.addAttribute("listaAlunos", listaAlunos);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "aluno/pesquisaDeAlunos";
	}
}
