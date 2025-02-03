package proj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import proj.dao.AdministradorDao;
import proj.dao.AlunoDao;
import proj.dao.EmpresaDao;
import proj.dao.EstagioDao;
import proj.dao.HDataSource;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.SecaoDao;
import proj.dao.SeguidoresDao;
import proj.dao.TopicoDao;
import proj.dao.UsuarioDao;
import proj.model.Administrador;
import proj.model.Aluno;
import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Professor;
import proj.model.Projeto;
import proj.model.Secao;
import proj.model.Topico;
import proj.model.Usuario;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	@Autowired
    private HDataSource ds;

	@GetMapping
	public String mostraPerfilAluno(@RequestParam("id") Long usuarioId, Model model, Principal principal) throws Exception {
	    try(Connection conn = ds.getConnection()) {
	    	
	        Usuario u = UsuarioDao.get(conn, usuarioId);
	        model.addAttribute("usuario", u);
	        
	        if(u.getRole().equals("Aluno")) {
		        Aluno a = AlunoDao.getByUsuario_id(conn, usuarioId);
		        
		        long idVisitante = UsuarioDao.getIdByNome(conn, principal.getName());
		        model.addAttribute("idVisitante", idVisitante);
		        //System.out.println(idVisitante);
	
		        model.addAttribute("usuarioId", usuarioId);
	
		        boolean isDonoDoPerfil = u.getId() == (a.getUsuario_id());
	
		        List<Secao> secoes = SecaoDao.listarSecoesPorUsuarioId(conn, usuarioId);
		        model.addAttribute("secoes", secoes);
		        
	
		        Secao secaoLicencas = null;
		        
		        for (Secao secao : secoes) {
		            if (secao.getTipo().equals("Licenças e Certificados")) {
		                secaoLicencas = secao;
		                break;
		            }
		        }
	
		        if (secaoLicencas != null) {
		            List<Topico> topicos = TopicoDao.listarTopicosPorSecaoId(conn, secaoLicencas.getId());
		            model.addAttribute("topicos", topicos);
		            model.addAttribute("qtdTopicos", TopicoDao.contarTopicosPorSecao(conn, secaoLicencas.getId()));
		        } else {
		            model.addAttribute("topicos", new ArrayList<>());
		            model.addAttribute("qtdTopicos", 0);
		        }
		        
		        long qtdSecoesTextoLivre = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Texto Livre", a.getId());
		        long qtdSecoesProjetosConcluidos = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Projetos Concluídos", a.getId());
		        long qtdSecoesLicencasECertificados = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Licenças e Certificados", a.getId());
	
		        model.addAttribute("qtdSecoesTextoLivre", qtdSecoesTextoLivre);
		        model.addAttribute("qtdSecoesProjetosConcluidos", qtdSecoesProjetosConcluidos);
		        model.addAttribute("qtdSecoesLicencasECertificados", qtdSecoesLicencasECertificados);
	
		        if (a.getFotoPerfil() != null) {
		            String fotoPerfilBase64 = Base64.getEncoder().encodeToString(a.getFotoPerfil());
		            model.addAttribute("fotoPerfil", fotoPerfilBase64);
		        }
	
		        if (a.getBannerPerfil() != null) {
		            String bannerBase64 = Base64.getEncoder().encodeToString(a.getBannerPerfil());
		            model.addAttribute("banner", bannerBase64);
		        }
	
		        ArrayList<Projeto> projetos = AlunoDao.listProjetosByAlunoId(conn, a.getId());
		        ArrayList<Estagio> estagios = AlunoDao.listEstagiosByAlunoId(conn, a.getId());
		        int n_seguidores = SeguidoresDao.listSeguidores(conn, a.getUsuario_id()).getNumeroSeguidores();
		        int n_seguidos = SeguidoresDao.listSeguidos(conn, a.getUsuario_id()).getNumeroSeguidores();
	
		        long idDoUsuarioVisitante = u.getId();
	
		        model.addAttribute("seguidores", n_seguidores);
		        model.addAttribute("idDoUsuario", idDoUsuarioVisitante);
		        model.addAttribute("seguidos", n_seguidos);
		        model.addAttribute("u", a);
		        model.addAttribute("projetos", projetos);
		        model.addAttribute("estagios", estagios);
		        model.addAttribute("isDonoDoPerfil", isDonoDoPerfil);
		        
		        //pras avaliações
		        model.addAttribute("nome", a.getNome());
		        
		        return "perfil";
	        }
	        else if(u.getRole().equals("Professor")) {
	        	Professor p = ProfessorDao.getByUsuario_id(conn, usuarioId);
		        
		        long idVisitante = UsuarioDao.getIdByNome(conn, principal.getName());
		        model.addAttribute("idVisitante", idVisitante);
		        //System.out.println(idVisitante);
	
		        model.addAttribute("usuarioId", usuarioId);
	
		        boolean isDonoDoPerfil = u.getId() == (p.getUsuario_id());
	
		        List<Secao> secoes = SecaoDao.listarSecoesPorUsuarioId(conn, usuarioId);
		        model.addAttribute("secoes", secoes);
		        
		        Secao secaoLicencas = null;
		        
		        for (Secao secao : secoes) {
		            if (secao.getTipo().equals("Licenças e Certificados")) {
		                secaoLicencas = secao;
		                break;
		            }
		        }
	
		        if (secaoLicencas != null) {
		            List<Topico> topicos = TopicoDao.listarTopicosPorSecaoId(conn, secaoLicencas.getId());
		            model.addAttribute("topicos", topicos);
		            model.addAttribute("qtdTopicos", TopicoDao.contarTopicosPorSecao(conn, secaoLicencas.getId()));
		        } else {
		            model.addAttribute("topicos", new ArrayList<>());
		            model.addAttribute("qtdTopicos", 0);
		        }
		        
		        long qtdSecoesTextoLivre = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Texto Livre", usuarioId);
		        long qtdSecoesProjetosConcluidos = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Projetos Concluídos", usuarioId);
		        long qtdSecoesLicencasECertificados = SecaoDao.contarSecoesPorTipoEUsuario(conn, "Licenças e Certificados", usuarioId);
	
		        model.addAttribute("qtdSecoesTextoLivre", qtdSecoesTextoLivre);
		        model.addAttribute("qtdSecoesProjetosConcluidos", qtdSecoesProjetosConcluidos);
		        model.addAttribute("qtdSecoesLicencasECertificados", qtdSecoesLicencasECertificados);
	
		        if (p.getFotoPerfil() != null) {
		            String fotoPerfilBase64 = Base64.getEncoder().encodeToString(p.getFotoPerfil());
		            model.addAttribute("fotoPerfil", fotoPerfilBase64);
		        }
	
		        if (p.getBannerPerfil() != null) {
		            String bannerBase64 = Base64.getEncoder().encodeToString(p.getBannerPerfil());
		            model.addAttribute("banner", bannerBase64);
		        }
	
		        ArrayList<Projeto> projetos = ProjetoDao.listPorIdProfessor(conn, usuarioId);
		        int n_seguidores = SeguidoresDao.listSeguidores(conn, p.getUsuario_id()).getNumeroSeguidores();
		        int n_seguidos = SeguidoresDao.listSeguidos(conn, p.getUsuario_id()).getNumeroSeguidores();
	
		        long idDoUsuarioVisitante = u.getId();
	
		        model.addAttribute("seguidores", n_seguidores);
		        model.addAttribute("idDoUsuario", idDoUsuarioVisitante);
		        model.addAttribute("seguidos", n_seguidos);
		        model.addAttribute("u", p);
		        model.addAttribute("projetos", projetos);
		        model.addAttribute("isDonoDoPerfil", isDonoDoPerfil);
		        
		        model.addAttribute("nome", p.getNome());
		        
		        return "perfil";
	        }
	        else if (u.getRole().equals("Administrador") || u.getRole().equals("Empresa")) {
	            return "perfil";
	        } else {
	            return mostraPaginaDeErro(model, "Você não tem permissão para acessar esta página.");
	        }
	    } catch (Exception e) {
	        return "erro";
	    }
	}
	
	@GetMapping("/emite")
	public String emiteCertificado(@RequestParam("id") Long projetoId, @RequestParam("tipo") String projetoTipo, @RequestParam("aluno") Long alunoId,Model model, Principal principal) throws Exception {
        try(Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
        	model.addAttribute("usuario", u);
    
            if (u.getRole().equals("Administrador")) {
                Administrador adm = AdministradorDao.getByCpf(conn, principal.getName());
                ArrayList<Empresa> empresas = AdministradorDao.listEmpresas(conn);

                model.addAttribute("administrador", adm);
                model.addAttribute("listaEmpresas", empresas);
            }
    
			if (u.getRole().equals("Empresa")) {
				Empresa e = EmpresaDao.getByNome(conn, principal.getName());
				model.addAttribute("empresa", e);
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
            
            InputStream imageStream = getClass().getClassLoader().getResourceAsStream("static/img/logo-cefet.png");

            Image img = Image.getInstance(imageStream.readAllBytes());
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
                
                String outputFilePath = "src/main/resources/static/pdf/CertificadoPadrao.pdf";
                File outputFile = new File(outputFilePath);
                PdfWriter.getInstance(document, new FileOutputStream(outputFile));

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
	
	@PostMapping("/atualizar")
	public String atualizarDadosPerfil(
	    @RequestParam("id") Long usuarioId, 
	    @RequestParam("fotoPerfil") MultipartFile fotoPerfil,
	    @RequestParam("banner") MultipartFile banner,
	    @RequestParam("descricao") String descricaoPerfil,
	    Principal principal, Model model) throws Exception {
	    try (Connection conn = ds.getConnection()) {
	    	Usuario u = UsuarioDao.get(conn, usuarioId);
	        model.addAttribute("usuario", u);
	        
	    	//pra verificar se tá funcionando
	    	System.out.println("DESCRIÇÃO:\n\n" + descricaoPerfil);
	    	
	    	System.out.println("Tamanho da fotoPerfil: " + fotoPerfil.getSize());
	        System.out.println("Tamanho do banner: " + banner.getSize());
	        
	        byte[] fotoPerfilBytes = fotoPerfil.getBytes();
	        byte[] bannerBytes = banner.getBytes();
	        
	        //pra verificar se tá funcionando
	        System.out.println("Bytes fotoPerfil: " + fotoPerfilBytes.length);
	        System.out.println("Bytes banner: " + bannerBytes.length);
	        
	        if(u.getRole().equals("Aluno")) {
		        Aluno a = AlunoDao.get(conn, usuarioId);
		        
		        a.setDescricaoPerfil(descricaoPerfil);
		        
		        if (fotoPerfil != null && !fotoPerfil.isEmpty())
		        	a.setFotoPerfil(fotoPerfilBytes);
		        if (banner != null && !banner.isEmpty())
		        	a.setBannerPerfil(bannerBytes);
		        
		        AlunoDao.updateForDescricaoPerfil(conn, a.getId(), a.getDescricaoPerfil());
		        AlunoDao.updateForFotoPerfil(conn, a.getId(), a.getFotoPerfil());
		        AlunoDao.updateForBannerPerfil(conn, a.getId(), a.getBannerPerfil());
		        conn.commit();
	
		        return "redirect:/perfil?id=" + usuarioId;
	        } else if(u.getRole().equals("Professor")) {
		        Professor p = ProfessorDao.get(conn, usuarioId);
		        
		        p.setDescricaoPerfil(descricaoPerfil);
		        
		        if (fotoPerfil != null && !fotoPerfil.isEmpty())
		        	p.setFotoPerfil(fotoPerfilBytes);
		        if (banner != null && !banner.isEmpty())
		        	p.setBannerPerfil(bannerBytes);
		        
		        ProfessorDao.update(conn, p);
		        
		        conn.commit();
	
		        return "redirect:/perfil?id=" + usuarioId;
	        } if(u.getRole().equals("Administrador")) {
		        /*Administrador ad = AdministradorDao.get(conn, usuarioId);
		        
		        ad.setDescricaoPerfil(descricaoPerfil);
		        
		        if (fotoPerfil != null && !fotoPerfil.isEmpty())
		        	ad.setFotoPerfil(fotoPerfilBytes);
		        if (banner != null && !banner.isEmpty())
		        	ad.setBannerPerfil(bannerBytes);
		        
		        AdministradorDao.update(conn, ad);
		        conn.commit();*/
	
		        return "redirect:/perfil?id=" + usuarioId;
	        } else {
	        	return "redirect:/perfil?id=" + usuarioId;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Erro ao atualizar dados.");
	        return "erro";
	    }
	}
	
	@PostMapping("/atualizar-secoes")
	public String atualizarSecoes(
		@RequestParam(value = "id", required = true) Long usuarioId,
        @RequestParam(value = "titulo", required = false, defaultValue = "") String titulo,
        @RequestParam(value = "conteudoTexto", required = false, defaultValue = "Escreva seu texto...") String conteudoTexto,
        @RequestParam(value = "conteudoTextoTopico", required = false) List<String> conteudosTextoTopico,
        @RequestParam(value = "conteudoArquivo", required = false) List<MultipartFile> conteudoArquivos,
        @RequestParam(value = "conteudoDesenho", required = false) String conteudoDesenho,
        @RequestParam(value = "conteudoImagem", required = false) List<MultipartFile> conteudoImagens,
        @RequestParam(value = "qtdTopicos", required = false) Long qtdTopicos,
        @RequestParam(value = "tipo", required = true) String tipo,
        @RequestParam(value = "ordem", required = true) Integer ordem,
        @RequestParam(value = "comprimentoConteudoTexto", required = false) Integer comprimentoConteudoTexto,
        @RequestParam(value = "alturaConteudoTexto", required = false) Integer alturaConteudoTexto,
        @RequestParam(value = "comprimentoConteudoTextoTopico", required = false) List<Integer> comprimentosConteudoTextoTopico,
        @RequestParam(value = "alturaConteudoTextoTopico", required = false) List<Integer> alturasConteudoTextoTopico,
        @RequestParam(value = "leftConteudoTexto", required = false) Integer leftConteudoTexto,
        @RequestParam(value = "topConteudoTexto", required = false) Integer topConteudoTexto,
	    Model model) {

	    try (Connection conn = ds.getConnection()) {
	        if (ordem == null) 
	        	ordem = 0;
	        
	        Secao sec = new Secao();
	        
	        if (conteudoDesenho != null && !conteudoDesenho.isEmpty()) {
	        	conteudoDesenho = conteudoDesenho.replaceFirst("data:image\\/\\w+;base64,", "");
	        	conteudoDesenho = conteudoDesenho.replaceAll("[^A-Za-z0-9+/=]", "");
	        	byte[] desenhoBytes = Base64.getDecoder().decode(conteudoDesenho);
	        	sec.setConteudoImagem(desenhoBytes);
	        }
	        
	        //VERIFICAR SE TÁ DANDO LARGURA E ALTURA MESMO DO CONTEUDO TEXTO E TOP E LEFT
	        System.out.println("Largura: " + comprimentoConteudoTexto + ", Altura: " + alturaConteudoTexto);
	        System.out.println("Top: " + comprimentoConteudoTexto + ", Left: " + alturaConteudoTexto);
            
            sec.setUsuarioId(usuarioId);
            sec.setTipo(tipo);
            sec.setTitulo(titulo); 
            sec.setOrdem(ordem);
            sec.setComprimentoConteudoTexto(comprimentoConteudoTexto);
            sec.setAlturaConteudoTexto(alturaConteudoTexto);
            sec.setTopConteudoTexto(topConteudoTexto);
            sec.setLeftConteudoTexto(leftConteudoTexto);
            
            System.out.println("qtdTopicos recebido no backend: " + qtdTopicos);
            	
            if (conteudoTexto == null || conteudoTexto.isEmpty())
            	sec.setConteudoTexto("Escreva seu texto..."); 
            else {
            	sec.setConteudoTexto(conteudoTexto); 
            }   
            
            SecaoDao.salvarSecao(conn, sec);
            //System.out.println("SecaoId " + sec.getId());
            
            if (tipo.equals("Licenças e Certificados")) {
		        for (int i = 0; i < qtdTopicos; i++) {
		            Topico topico = new Topico();
		            topico.setSecaoId(sec.getId());
		            topico.setEstado(false);
		            
		            /*verificar id secao do topico*/
		            System.out.println("SecaoId do Topico" + topico.getSecaoId());
		
		            if (conteudosTextoTopico != null && !conteudosTextoTopico.isEmpty() && i < conteudosTextoTopico.size()) {
		                topico.setConteudoTexto(conteudosTextoTopico.get(i));
		            } else {
		                topico.setConteudoTexto("Escreva seu texto...");
		            }
		            
		            //System.out.println("Topico Texto " + i + ":" + conteudosTextoTopico.get(i));
		
		            if (conteudoArquivos != null && i < conteudoArquivos.size()) {
		                MultipartFile arquivo = conteudoArquivos.get(i);
		                if (!arquivo.isEmpty()) {
		                    topico.setConteudoArquivo(arquivo.getBytes());
		                }
		            }
		
		            if (conteudoImagens != null && i < conteudoImagens.size()) {
		                MultipartFile imagem = conteudoImagens.get(i);
		                if (!imagem.isEmpty()) {
		                    topico.setConteudoImagem(imagem.getBytes());
		                }
		            }
		            
		            if (comprimentosConteudoTextoTopico != null && i < comprimentosConteudoTextoTopico.size()) {
		                System.out.println("Salvando Tópico " + i + " - Largura: " + comprimentosConteudoTextoTopico.get(i));
		                topico.setComprimentoConteudoTexto(comprimentosConteudoTextoTopico.get(i));
		            }

		            if (alturasConteudoTextoTopico != null && i < alturasConteudoTextoTopico.size()) {
		                System.out.println("Salvando Tópico " + i + " - Altura: " + alturasConteudoTextoTopico.get(i));
		                topico.setAlturaConteudoTexto(alturasConteudoTextoTopico.get(i));
		            }
		
		            TopicoDao.salvarTopico(conn, topico);
		        }
		    }
            
	        conn.commit();

	        return "redirect:/perfil?id=" + usuarioId;
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Erro ao salvar as seções: " + e.getMessage());
	        return "erro";
	    }
	}
	
	@PostMapping("/atualizar-secoes-editadas")
	public String atualizarSecoesEditadas(
		@RequestParam(value = "id", required = true) Long usuarioId,
		@RequestParam(value = "idSecao", required = true) Long idSecao,
        @RequestParam(value = "titulo", required = false, defaultValue = "") String titulo,
        @RequestParam(value = "conteudoTexto", required = false, defaultValue = "Escreva seu texto...") String conteudoTexto,
        @RequestParam(value = "conteudoTextoTopico", required = false) List<String> conteudosTextoTopico,
        @RequestParam(value = "conteudoArquivo", required = false) List<MultipartFile> conteudoArquivos,
        @RequestParam(value = "conteudoImagem", required = false) List<MultipartFile> conteudoImagens,
        @RequestParam(value = "qtdTopicos", required = false) Long qtdTopicos,
        @RequestParam(value = "tipo", required = true) String tipo,
        @RequestParam(value = "ordem", required = true) Integer ordem,
        @RequestParam(value = "comprimentoConteudoTexto", required = false) Integer comprimentoConteudoTexto,
        @RequestParam(value = "alturaConteudoTexto", required = false) Integer alturaConteudoTexto,
        @RequestParam(value = "comprimentoConteudoTextoTopico", required = false) List<Integer> comprimentosConteudoTextoTopico,
        @RequestParam(value = "alturaConteudoTextoTopico", required = false) List<Integer> alturasConteudoTextoTopico,
        @RequestParam(value = "leftConteudoTexto", required = false) Integer leftConteudoTexto,
        @RequestParam(value = "topConteudoTexto", required = false) Integer topConteudoTexto,
        @RequestParam(value = "estado", required = false) Boolean estado,
	    Model model) {

	    try (Connection conn = ds.getConnection()) {
	        if (ordem == null) 
	        	ordem = 0;
	        
	        //VERIFICAR SE TÁ DANDO LARGURA E ALTURA MESMO DO CONTEUDO TEXTO E TOP E LEFT
	        System.out.println("Largura: " + comprimentoConteudoTexto + ", Altura: " + alturaConteudoTexto);
	        System.out.println("Top: " + comprimentoConteudoTexto + ", Left: " + alturaConteudoTexto);
	        
            Secao sec = SecaoDao.getSecaoPorId(conn, idSecao);
            
            sec.setUsuarioId(usuarioId);
            sec.setTipo(tipo);
            sec.setTitulo(titulo); 
            sec.setOrdem(ordem);
            sec.setComprimentoConteudoTexto(comprimentoConteudoTexto);
            sec.setAlturaConteudoTexto(alturaConteudoTexto);
            sec.setTopConteudoTexto(topConteudoTexto);
            sec.setLeftConteudoTexto(leftConteudoTexto);
            
            System.out.println("qtdTopicos recebido no backend: " + qtdTopicos);
            	
            if (conteudoTexto == null || conteudoTexto.isEmpty())
            	sec.setConteudoTexto("Escreva seu texto..."); 
            else {
            	sec.setConteudoTexto(conteudoTexto); 
            }   
            
            SecaoDao.atualizarSecao(conn, sec);
            //System.out.println("SecaoId " + sec.getId());
            /*
            if (tipo.equals("Licenças e Certificados")) {
		        for (int i = 0; i < qtdTopicos; i++) {
		            Topico topico = new Topico();
		            topico.setSecaoId(sec.getId());
		            topico.setEstado(false);
		            
		            /*verificar id secao do topico
		            System.out.println("SecaoId do Topico" + topico.getSecaoId());
		
		            if (conteudosTextoTopico != null && !conteudosTextoTopico.isEmpty() && i < conteudosTextoTopico.size()) {
		                topico.setConteudoTexto(conteudosTextoTopico.get(i));
		            } else {
		                topico.setConteudoTexto("Escreva seu texto...");
		            }
		            
		            //System.out.println("Topico Texto " + i + ":" + conteudosTextoTopico.get(i));
		
		            if (conteudoArquivos != null && i < conteudoArquivos.size()) {
		                MultipartFile arquivo = conteudoArquivos.get(i);
		                if (!arquivo.isEmpty()) {
		                    topico.setConteudoArquivo(arquivo.getBytes());
		                }
		            }
		
		            if (conteudoImagens != null && i < conteudoImagens.size()) {
		                MultipartFile imagem = conteudoImagens.get(i);
		                if (!imagem.isEmpty()) {
		                    topico.setConteudoImagem(imagem.getBytes());
		                }
		            }
		            
		            if (comprimentosConteudoTextoTopico != null && i < comprimentosConteudoTextoTopico.size()) {
		                System.out.println("Salvando Tópico " + i + " - Largura: " + comprimentosConteudoTextoTopico.get(i));
		                topico.setComprimentoConteudoTexto(comprimentosConteudoTextoTopico.get(i));
		            }

		            if (alturasConteudoTextoTopico != null && i < alturasConteudoTextoTopico.size()) {
		                System.out.println("Salvando Tópico " + i + " - Altura: " + alturasConteudoTextoTopico.get(i));
		                topico.setAlturaConteudoTexto(alturasConteudoTextoTopico.get(i));
		            }
		
		            TopicoDao.salvarTopico(conn, topico);
		        }
		    }*/
            
	        conn.commit();

	        return "redirect:/perfil?id=" + usuarioId;
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Erro ao salvar as seções: " + e.getMessage());
	        return "erro";
	    }
	}
	
	@PostMapping("/apagar-secao")
	public String apagarSecao(
		@RequestParam("idSecao") Long secaoId,
		@RequestParam("idUsuario") Long usuarioId,
	    Model model) {

	    try (Connection conn = ds.getConnection()) {

            SecaoDao.excluirSecao(conn, secaoId);
	        conn.commit();

	        return "redirect:/perfil?id=" + usuarioId;
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Erro ao salvar as seções: " + e.getMessage());
	        return "erro";
	    }
	}

}