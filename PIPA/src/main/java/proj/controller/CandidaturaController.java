package proj.controller;

import proj.model.Candidatura;
import proj.model.Aluno;
import proj.model.Projeto;
import proj.model.StatusCandidatura;
import proj.dao.CandidaturaDao;
import proj.dao.AlunoDao;
import proj.dao.ProjetoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.security.Principal;

/**
 * Controlador responsável pelas operações relacionadas às candidaturas.
 */
@Controller
@RequestMapping("/projeto")
public class CandidaturaController {

    @Autowired
    private CandidaturaDao candidaturaDao; // DAO responsável pelas operações de candidatura

    @Autowired
    private DataSource dataSource; // Fonte de dados para conexão com o banco

    /**
     * Exibe o formulário de candidatura para um projeto específico.
     *
     * @param id        O ID do projeto
     * @param model     Modelo para passar dados à view
     * @param principal Objeto que representa o aluno logado
     * @return O nome da view a ser exibida
     */
    @GetMapping("/{id}/aplicacao")
    public String exibirFormularioCandidatura(@PathVariable Long id, Model model, Principal principal) {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Buscando projeto com ID: " + id); // Log do ID do projeto
            Projeto projeto = ProjetoDao.get(conn, id);

            if (projeto == null) {
                model.addAttribute("erro", "Projeto não encontrado.");
                return "error";
            }

            // Obtém o aluno logado
            Aluno alunoLogado = AlunoDao.getByCpf(conn, principal.getName());

            // Caso o aluno não seja encontrado, exibe uma mensagem de erro
            if (alunoLogado == null) {
                model.addAttribute("erro", "Usuário não autenticado.");
                return "error";
            }

            // Verifica se o aluno já se candidatou ao projeto
            List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(id);
            for (Candidatura candidatura : candidaturas) {
                if (candidatura.getCandidato().getId() == alunoLogado.getId()) {
                    model.addAttribute("conclusao", "Você já se candidatou a este projeto.");
                    model.addAttribute("projeto", projeto);
                    model.addAttribute("aluno", alunoLogado);
                    return "conclusao"; // Tela de conclusão, se o aluno já se candidatou
                }
            }

            // Caso o aluno não tenha se candidatado, exibe o formulário
            model.addAttribute("aluno", alunoLogado);
            model.addAttribute("projeto", projeto); // Corrigido: removido espaço extra

            return "aplicacao"; // Tela de candidatura
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro
            model.addAttribute("erro", "Erro ao carregar os dados.");
            return "error";
        }
    }

    /**
     * Processa o envio de uma candidatura.
     *
     * @param mensagem       Mensagem do aluno para o projeto
     * @param oportunidadeId ID do projeto (oportunidade)
     * @param model          Modelo para passar dados à view
     * @param principal      Objeto que representa o aluno logado
     * @return O nome da view a ser exibida
     */
    @PostMapping("/aplicacao")
    public String enviarCandidatura(@RequestParam("mensagem") String mensagem,
                                    @RequestParam("IDoportunidade") Long oportunidadeId, Model model, Principal principal) {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Mensagem recebida: " + mensagem); // Log da mensagem
            System.out.println("ID da oportunidade: " + oportunidadeId); // Log do ID da oportunidade

            Aluno alunoLogado = AlunoDao.getByCpf(conn, principal.getName());

            // Caso o aluno não esteja autenticado, exibe uma mensagem de erro
            if (alunoLogado == null) {
                model.addAttribute("erro", "Usuário não autenticado.");
                return "error";
            }

            Projeto projeto = ProjetoDao.get(conn, oportunidadeId);

            // Caso o projeto não seja encontrado, exibe uma mensagem de erro
            if (projeto == null) {
                model.addAttribute("erro", "Projeto não encontrado.");
                return "error";
            }

            // Verifica se o aluno já se candidatou ao projeto
            List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(oportunidadeId);
            System.out.println("Candidaturas encontradas: " + candidaturas.size()); // Log do número de candidaturas
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
            candidatura.setStatus(StatusCandidatura.EM_ANDAMENTO); // Define o status como "Em Andamento"

            candidaturaDao.salvar(candidatura);

            model.addAttribute("sucesso", "Candidatura enviada com sucesso!");
            model.addAttribute("projeto", projeto);
            model.addAttribute("aluno", alunoLogado);

            return "conclusao"; // Página de conclusão
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao salvar candidatura.");
            return "error"; // Erro ao tentar salvar a candidatura
        }
    }

    // Parte do professor

    /**
     * Exibe todas as candidaturas para um projeto específico.
     *
     * @param id    O ID do projeto
     * @param model Modelo para passar dados à view
     * @return O nome da view a ser exibida
     */
    @GetMapping("/professor/candidatos/{id}")
    public String exibirCandidaturas(@PathVariable Long id, Model model) {
        try (Connection conn = dataSource.getConnection()) {
            // Obtém o projeto pelo ID
            Projeto projeto = ProjetoDao.get(conn, id);

            // Caso o projeto não seja encontrado, exibe uma mensagem de erro
            if (projeto == null) {
                model.addAttribute("erro", "Projeto não encontrado.");
                return "error";
            }

            // Lista as candidaturas para o projeto
            List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(id);
            model.addAttribute("candidaturas", candidaturas);
            model.addAttribute("projeto", projeto);

            return "listarCandidaturas"; // Tela de listagem das candidaturas
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar as candidaturas.");
            return "error"; // Erro ao tentar carregar os dados
        }
    }

    /**
     * Valida a candidatura de um aluno para um projeto específico.
     *
     * @param candidaturaId ID da candidatura a ser validada
     * @param model        Modelo para passar dados à view
     * @return O nome da view a ser exibida
     */
    @PostMapping("/professor/validarCandidatura/{candidaturaId}")
    public String validarCandidatura(@PathVariable Long candidaturaId, Model model) {
        try {
            // Valida a candidatura, definindo o status como "Validada"
            candidaturaDao.atualizarStatus(candidaturaId, StatusCandidatura.VALIDADA);

            model.addAttribute("sucesso", "Candidatura aprovada com sucesso!");

            return "redirect:/professor/candidatos"; // Redireciona para a lista de candidaturas
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao validar a candidatura.");
            return "error"; // Erro ao tentar validar a candidatura
        }
    }
}