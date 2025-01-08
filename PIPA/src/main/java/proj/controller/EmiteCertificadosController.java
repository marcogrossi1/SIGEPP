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
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.dao.ProjetoDao;
import proj.dao.EstagioDao;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.io.FileOutputStream;

@Controller
public class EmiteCertificadosController {

    @Autowired
	private HDataSource ds;
    
    @RequestMapping("/emite")
	public String emiteCertificadoProjeto(@RequestParam("id") Long projetoId, @RequestParam("tipo") String projetoTipo,Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
	    	Aluno a = AlunoDao.getByCpf(conn, principal.getName());
		    ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		    ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
            Projeto projetoRealizado = ProjetoDao.get(conn, projetoId);
            Estagio estagioRealizado = EstagioDao.get(conn, projetoId);
            
		    model.addAttribute("aluno", a);
		    model.addAttribute("projetos", projetos);
		    model.addAttribute("estagios", estagios);

            String nomeAluno = a.getNome();
            String cursoAluno = a.getCurso();
            
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph title = new Paragraph("Certificado", font);
            Paragraph p;
            
            String imagePath = "PIPA/src/main/resources/static/img/logo-cefet.png";
            Image img = Image.getInstance(imagePath);
            img.scalePercent(10);
            img.setAbsolutePosition(100f, 100f);

            if(projetoTipo.equals("projeto")) {
                String projetoNome = projetoRealizado.getNome();
                Integer projetoCargaHoraria = projetoRealizado.getCargaHoraria();
                String nomeOrientador = projetoRealizado.getResponsavel();
                
                p = new Paragraph(
                    "Certificamos que o aluno " + nomeAluno + " do curso Técnico em " + cursoAluno + 
                    " concluiu o projeto " + projetoNome + " com carga horária de " + projetoCargaHoraria + 
                    " horas, sob orientação do Prof." + nomeOrientador + ".",
                    font);
            } 
            
            else if (projetoTipo.equals("estagio")) {
                String empresaNome = estagioRealizado.getEmpresa();
                Integer estagioCargaHoraria = estagioRealizado.getCargaHoraria();

                p = new Paragraph(
                    "Certificamos que o aluno " + nomeAluno + " do curso Técnico em " + cursoAluno + 
                    " concluiu o estágio na empresa " + empresaNome + " com carga horária de " + estagioCargaHoraria + " horas.",
                    font);
            }
            
            else {
                return "erro";
            }

            try{
                Document document = new Document();

                String outputFilePath = "PIPA/src/main/resources/static/pdf/CertificadoPadrao.pdf";
                PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));

                document.open();
                
                document.add(title);
                document.add(p);
                document.add(img);

                document.close();
            
                return "emites";
            } 
            
            catch (Exception e) {
                e.printStackTrace();
                return "certificado";
            }
        }

        catch (Exception e) {
            return "erro";
        } 	
    }	
}