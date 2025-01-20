package proj.controller;

import java.io.FileOutputStream;
import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Usuario;

@Controller
@RequestMapping("/perfilPublico")
public class PerfilPublicoController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping("/perfilPublico")
    public String perfilAlunoProfessor() {
        return "aluno/perfilPublico";
    }

}