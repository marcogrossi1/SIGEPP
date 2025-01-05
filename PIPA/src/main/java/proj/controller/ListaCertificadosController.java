package proj.controller;

import java.security.Principal;
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
import proj.model.Estagio;
import proj.model.Projeto;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/certificados")
public class ListaCertificadosController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String listaCertificados(Model model, Principal principal)
	throws Exception {
		
		try(Connection conn = ds.getConnection()) {
			Aluno a = AlunoDao.getByCfp(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);
		}

		catch(Exception e) {
			return mostraPaginaDeErro();
		}
		
		return "certificado";
	}
        
	public String mostraPaginaDeErro() {
		return "erro";
	}
}