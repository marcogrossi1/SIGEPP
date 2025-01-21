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

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.dao.EmpresaDao;

import proj.model.Usuario;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Professor;
import proj.model.Projeto;

@Controller
@RequestMapping("/perfil-aluno")
public class PerfilAlunoController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping
	public String mostraPerfilAluno(@RequestParam("id") Long alunoId, Model model, Principal principal) 
	throws Exception {
		//try(
            Connection conn = ds.getConnection();//) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);

			Aluno a = AlunoDao.get(conn, alunoId);

			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);

			if (u.getRole().equals("Aluno")) {
                return "perfilAluno";
            }
    
            else if (u.getRole().equals("Administrador")) {
                Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", adm);
                model.addAttribute("listaEmpresas", empresas);

                return "perfilAluno";
            }
    
            else if (u.getRole().equals("Empresa")) {
            	Empresa emp = EmpresaDao.getByUsuario_id(conn, u.getId());
            	
            	model.addAttribute("empresa", emp);
            	
                return "perfilAluno";
            }
			
            else if (u.getRole().equals("Professor")) {
            	Professor p = ProfessorDao.getByUsuario_id(conn, u.getId());
            	
            	model.addAttribute("professor", p);
            	
                return "perfilAluno";
            }

			else {
				return mostraPaginaDeErro(model, "Você não tem permissão para acessar esta página.");
			}
		}
		
		//catch(Exception e) {
		//	return "erro";
		//}
	//}

	@GetMapping("/emite")
	public String emiteCertificado(@RequestParam("id") Long projetoId, @RequestParam("tipo") String projetoTipo, @RequestParam("aluno") Long alunoId,Model model, Principal principal) throws Exception {
        try(
            Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);

			if (u.getRole().equals("Aluno")) {
            }
    
            else if (u.getRole().equals("Administrador")) {
                Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", adm);
                model.addAttribute("listaEmpresas", empresas);
            }
    
            else if (u.getRole().equals("Empresa")) {
				Empresa e = EmpresaDao.getByNome(conn, principal.getName());
				model.addAttribute("empresa", e);
			}

			else {
				return mostraPaginaDeErro(model, "Você não tem permissão para acessar esta página.");
			}

	    	Aluno a = AlunoDao.get(conn, alunoId);

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
            
                return "aluno/emites";
            } 
            
            catch (Exception e) {
                e.printStackTrace();
                return mostraPaginaDeErro(model, "Erro ao emitir certificado.");
            }
        }

        catch (Exception e) {
            return "erro";
        } 	
    }	

	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message",message);
		return "erro";
	}
}