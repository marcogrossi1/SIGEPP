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
import org.springframework.web.bind.annotation.PostMapping;

import proj.dao.AlunoDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.NotFoundException;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Progresso;
import proj.model.Projeto;
import proj.model.Usuario;
import proj.model.Projeto;
@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private HDataSource ds;
	
	@GetMapping
	public String mostraPortal(Model model, Principal principal) throws Exception {
		try (Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (!u.getRole().equals("Aluno")) {
				return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByUsuario_id(conn, u.getId());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);

		} catch (Exception e) {
			e.printStackTrace();
			return mostraPaginaDeErro(model, "Erro interno na aplicação!.");
		}

		return "aluno/home";
	}	
	
	public String mostraPaginaDeErro(Model model, String message) {
		model.addAttribute("message", message);
		return "erro";
	}

	@GetMapping("/estagios")
	public String mostraEstagios(Model model, Principal principal) throws Exception {
		try (Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (!u.getRole().equals("Aluno")) {
				return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Estagio> estagioList = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagioDispList = EstagioDao.list(conn);
			deslistarEstagioJaInscrito(estagioList, estagioDispList);
			model.addAttribute("estagioDispList", estagioDispList);
			model.addAttribute("estagioList", estagioList);
		}
		return "aluno/estagios";
	}
        @GetMapping("/detalhes-estagio")
        public String getDetalhesEstagio(Model model, Principal principal, @RequestParam("n") long id) throws Exception{
            try(Connection conn = ds.getConnection()){
                Estagio es = EstagioDao.get(conn, id);
                Aluno a = AlunoDao.getByCpf(conn, principal.getName());
                Progresso pro = AlunoDao.getProgressoEstagio(conn, a.getId(), id);
                model.addAttribute("estagio", es);
                model.addAttribute("empresa", es.getEmpresa());
                model.addAttribute("descricao", es.getDescricao());
                model.addAttribute("cargaHoraria", es.getCargaHoraria());
                model.addAttribute("vagas", es.getVagas());
                model.addAttribute("requisito", es.getRequisito());
                model.addAttribute("salario", es.getSalario());
                model.addAttribute("progresso", pro);
                System.out.println(pro);
                String docArr[] = null;
                if(es.getDocumentos() != null) docArr = es.getDocumentos().split(",");
                model.addAttribute("documentos", docArr);
                return "aluno/detalhesEstagio";
            }catch(Exception e) {
                return mostraPaginaDeErro(model, e.getMessage());
            }
        }
        
        @GetMapping("/inscrever-estagio")
        public String inscreverEstagio(Model model, Principal principal, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Estagio es = EstagioDao.get(conn, id);
                Aluno al = AlunoDao.getByCpf(conn, principal.getName());
                if(AlunoDao.getProgressoEstagio(conn, al.getId(), es.getId()) != null){
                    model.addAttribute("status", "Você já se inscreveu neste estágio.");
                    model.addAttribute("warning", "bad");
                    return getDetalhesEstagio(model, principal, id);
                }
                AlunoDao.setProgresso(conn, al.getId() , es.getId() , Progresso.PENDENTE);
                if(model.getAttribute("status") == null)
                    model.addAttribute("status", "Estágio inscrito com sucesso!");
                model.addAttribute("warning", "good");
                conn.commit();
                return getDetalhesEstagio(model, principal, id);
                     
            }catch(Exception e){
                return mostraPaginaDeErro(model, e.getMessage());
            }
        }
        
        @GetMapping("/desinscrever-estagio")
        public String desinscreverEstagio(Model model, Principal principal, @RequestParam("n") long id){
            try(Connection conn = ds.getConnection()){
                Aluno a = AlunoDao.getByCpf(conn, principal.getName());
                Estagio e = EstagioDao.get(conn, id);
                if(AlunoDao.desinscreverEstagio(conn, a.getId(), e.getId())){
                    model.addAttribute("status", "Estágio desinscrito com sucesso!");
                    model.addAttribute("warning", "good");
                }else{ 
                    model.addAttribute("status", "Você não é inscrito no estágio.");
                    model.addAttribute("warning", "bad");
                }
                conn.commit();
                return getDetalhesEstagio(model, principal, id);
            }catch(Exception ex){
                return mostraPaginaDeErro(model, ex.getMessage());
            }
        }
        

	@GetMapping("/projetos")
	public String mostraHomeProjetos(Model model, Principal principal) throws Exception {
		try (Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			model.addAttribute("usuario", u);

			if (!u.getRole().equals("Aluno")) {
				return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);

			return "aluno/projetos";

		} catch (Exception e) {

			return "erro";
		}
	}

	@GetMapping("/certificados")			
	public String listaCertificados(Model model, Principal principal) throws Exception {
		try (Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			model.addAttribute("usuario", u);
			
			if (!u.getRole().equals("Aluno")) {
				return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
			}

			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
			ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
			model.addAttribute("estagios", estagios);

		} catch (Exception e) {

			return "erro";
		}

		return "aluno/certificado";
	}

	@GetMapping("/emite")

	public String emiteCertificadoProjeto(@RequestParam("id") Long projetoId, @RequestParam("tipo") String projetoTipo, Model model, Principal principal) throws Exception {
		try (Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			model.addAttribute("usuario", u);
			
			if (!u.getRole().equals("Aluno")) {
				return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
			}

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

			if (projetoTipo.equals("projeto")) {
				String projetoNome = projetoRealizado.getNome();
				Integer projetoCargaHoraria = projetoRealizado.getCargaHoraria();
				String nomeOrientador = projetoRealizado.getResponsavel();
				
				p = new Paragraph(
					"Certificamos que o aluno " + nomeAluno + " do curso Técnico em " + cursoAluno + 
					" concluiu o projeto " + projetoNome + " com carga horária de " + projetoCargaHoraria + 
					 " horas, sob orientação do Prof." + nomeOrientador + ".",
					font);
			} else if (projetoTipo.equals("estagio")) {
				String empresaNome = estagioRealizado.getEmpresa();
				Integer estagioCargaHoraria = estagioRealizado.getCargaHoraria();

				p = new Paragraph(
					"Certificamos que o aluno " + nomeAluno + " do curso Técnico em " + cursoAluno + 
					" concluiu o estágio na empresa " + empresaNome + " com carga horária de " + estagioCargaHoraria + " horas.",
					font);
			} else {
				return "erro";
			}

			try {
				Document document = new Document();
				String outputFilePath = "PIPA/src/main/resources/static/pdf/CertificadoPadrao.pdf";
				PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));

				document.open();
				document.add(title);
				document.add(p);
				document.add(img);
				document.close();
				
				return "aluno/emites";
			} catch (Exception e) {
				e.printStackTrace();
				return "aluno/certificado";
			}
		} catch (Exception e) {
			return "erro";
		} 	
	}	

	private void deslistarEstagioJaInscrito(ArrayList<Estagio> el, ArrayList<Estagio> edl) {
		for (int i = 0; i < edl.size(); i++) {
			for (int j = 0; j < el.size(); j++) {
				if (estagioIsEqual(edl.get(i), el.get(j))) {
					edl.remove(i);
				}
			}
		}
	}

	private boolean estagioIsEqual(Estagio e1, Estagio e2) {
		return e1.getId() == e2.getId();
	}
	
	@GetMapping("/upload")
	public String mostraUploadDocumentos(@RequestParam("estagioId") Long estagioId, Model model, Principal principal) throws Exception {    
	    try (Connection conn = ds.getConnection()){
	        Usuario u = UsuarioDao.getByNome(conn, principal.getName());
	        if (!u.getRole().equals("Aluno")) {
	            return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
	        }
	        
	        Aluno a = AlunoDao.getByCpf(conn, principal.getName());
	        Estagio estagio = EstagioDao.get(conn, estagioId);
	        model.addAttribute("usuarioId", a.getUsuario_id());
	     	 model.addAttribute("alunoId", a.getId());
	        model.addAttribute("alunoNome", a.getNome());
	        model.addAttribute("estagioId", estagioId);
	        model.addAttribute("nomeEstagio", estagio.getEmpresa());
	        return "aluno/upload";
	        } catch (Exception e) {
	        e.printStackTrace();
	        return mostraPaginaDeErro(model, "Erro ao carregar informações do estágio.");
	    }
	}
	@GetMapping("/uploadprojeto")
	public String mostraUploadDocumentosProjeto(@RequestParam("projetoId") Long projetoId, Model model, Principal principal) throws Exception {    
		  try (Connection conn = ds.getConnection()){
		        Usuario u = UsuarioDao.getByNome(conn, principal.getName());
		        if (!u.getRole().equals("Aluno")) {
		            return mostraPaginaDeErro(model, "Usuário não é um Aluno!.");
		        }
		        
		        Aluno a = AlunoDao.getByCpf(conn, principal.getName());
		        Projeto projeto = ProjetoDao.get(conn, projetoId);
		        model.addAttribute("usuarioId", a.getUsuario_id());
		     	 model.addAttribute("alunoId", a.getId());
		        model.addAttribute("alunoNome", a.getNome());
		        model.addAttribute("projetoId", projetoId);
		        model.addAttribute("nomeprojeto", projeto.getNome());
		        return "aluno/uploadprojeto";
		        } catch (Exception e){
		        e.printStackTrace();
		        return mostraPaginaDeErro(model, "Erro ao carregar informações do estágio.");
		    }
	    }
	
	@GetMapping("/explorarProjetos")
	public String explorarProjetos(Principal principal, Model model) throws Exception{
		
		try(Connection conn = ds.getConnection()) {
			Usuario u = UsuarioDao.getByNome(conn, principal.getName());
			if (u.getRole().equals("Aluno") == false) {
				return mostraPaginaDeErro(model , "Usuário não é um Aluno!.");
			}
			
			Aluno a = AlunoDao.getByCpf(conn, principal.getName());
			ArrayList<Projeto> projetos = ProjetoDao.list(conn);
			
			model.addAttribute("aluno", a);
			model.addAttribute("projetos", projetos);
		}
		catch(Exception e) {
			e.printStackTrace();
			return mostraPaginaDeErro(model , "Erro interno na aplicação!.");
		}
		
		return "aluno/explorarProjetos";
	}	
}
