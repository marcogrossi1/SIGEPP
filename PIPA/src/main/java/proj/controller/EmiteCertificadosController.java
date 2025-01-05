/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import jakarta.servlet.ServletContext;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/emite")
public class EmiteCertificadosController {

    @Autowired
	private HDataSource ds;
    
    @Autowired
    private ServletContext servletContext;
    
    @GetMapping
	public String emiteCertificado(Model model, Principal principal) 
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
            e.printStackTrace();
            return "certificado";
        }
            
            try{
                Document document = new Document();
                String outputFilePath = servletContext.getRealPath("/static/pdf/Certificado.pdf");
                PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
                document.open();

                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

                String nomeAluno = "João";
                String cursoAluno = "Informática";

                Paragraph p = new Paragraph("Certificado", font);
                document.add(p);
                p = new Paragraph(
                    "Certificamos que o aluno " + 
                            nomeAluno + 
                            " do curso Técnico em " + 
                            cursoAluno + 
                            " concluiu o projeto. ", 
                    font);

                document.add(p);

                Image img = Image.getInstance(servletContext.getRealPath("/static/img/logo.png"));
                img.scalePercent(10);
                img.setAbsolutePosition(100f, 100f);

                document.add(img);

                document.close();
            
            return "portal";
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            return "emites";
        } 	
    }	
	
    public String mostraPaginaDeErro() {
		return "erro";
	}
}