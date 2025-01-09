package proj.controller;

import proj.model.Candidatura;
import proj.model.Aluno;
import proj.model.Projeto;
import proj.dao.AlunoDao;
import proj.dao.CandidaturaDao;
import proj.dao.ProjetoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping("/projeto")
public class CandidaturaController {

	@Autowired
	private CandidaturaDao candidaturaDao;

	@Autowired
	private DataSource dataSource;

	@GetMapping("/{id}/aplicacao")
	public String exibirFormularioCandidatura(
	        @PathVariable Long id, Model model,
	        Principal principal) {

	    try (Connection conn = dataSource.getConnection()) {
	        // Obtém o projeto pelo ID
	        Projeto projeto = ProjetoDao.get(conn, id);

	        if (projeto == null) {
	            model.addAttribute("erro", "Projeto não encontrado.");
	            return "error";
	        }

	        // Obtém o aluno logado pelo CPF ou outro identificador
	        Aluno alunoLogado = AlunoDao.getByCpf(conn, principal.getName());

	        if (alunoLogado == null) {
	            model.addAttribute("erro", "Usuário não autenticado.");
	            return "error";
	        }

	        // Verifica se o aluno já se candidatou a este projeto
	        List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(id);
	        for (Candidatura candidatura : candidaturas) {
	        	if (candidatura.getCandidato().getId() == alunoLogado.getId()) {
	                model.addAttribute("conclusao", "Você já se candidatou a este projeto.");
	                model.addAttribute("projeto", projeto);
	                model.addAttribute("aluno", alunoLogado);
	                return "conclusao"; // Tela de conclusão
	            }
	        }

	        // Adiciona os objetos ao modelo
	        model.addAttribute("aluno", alunoLogado);
	        model.addAttribute("projeto", projeto);

	        return "aplicacao";
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("erro", "Erro ao carregar os dados.");
	        return "error";
	    }
	}

	@PostMapping("/aplicacao")
	public String enviarCandidatura(
	        @RequestParam("mensagem") String mensagem,
	        @RequestParam("IDoportunidade") Long oportunidadeId,
	        Model model, Principal principal) {

	    try (Connection conn = dataSource.getConnection()) {
	        Aluno alunoLogado = AlunoDao.getByCpf(conn, principal.getName());

	        if (alunoLogado == null) {
	            model.addAttribute("erro", "Usuário não autenticado.");
	            return "error";
	        }

	        Projeto projeto = ProjetoDao.get(conn, oportunidadeId);

	        if (projeto == null) {
	            model.addAttribute("erro", "Projeto não encontrado.");
	            return "error";
	        }

	        // Verifica se o aluno já se candidatou
	        List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(oportunidadeId);
	        for (Candidatura candidatura : candidaturas) {
	        	if (candidatura.getCandidato().getId() == alunoLogado.getId()) {
	                model.addAttribute("erro", "Você já se candidatou a este projeto.");
	                return "error"; // Retorna erro se já se candidatou
	            }
	        }

	        // Cria e salva a candidatura
	        Candidatura candidatura = new Candidatura();
	        candidatura.setCandidato(alunoLogado);
	        candidatura.setIDoportunidade(oportunidadeId);
	        candidatura.setMensagem(mensagem);
	        candidatura.setDataAplicacao(java.time.LocalDateTime.now());

	        candidaturaDao.salvar(candidatura);
	        model.addAttribute("sucesso", "Candidatura enviada com sucesso!");
	        model.addAttribute("projeto", projeto);
	        model.addAttribute("aluno", alunoLogado);

	        return "conclusao"; // Página de conclusão
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("erro", "Erro ao salvar candidatura.");
	        return "error";
	    }
	}

}
