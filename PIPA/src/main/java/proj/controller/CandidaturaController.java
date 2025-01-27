package proj.controller;

import proj.model.Candidatura;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Projeto;
import proj.model.Secao;
import proj.model.StatusCandidatura;
import proj.model.Usuario;
import proj.dao.CandidaturaDao;
import proj.dao.HDataSource;
import proj.dao.AlunoDao;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.SecaoDao;
import proj.dao.UsuarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Base64;
import java.util.List;
import java.security.Principal;

/**
 * Controlador responsável pelas operações relacionadas às candidaturas.
 * Este controlador lida com as interações entre alunos, professores e candidaturas para projetos.
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
            Projeto projeto = ProjetoDao.get(conn, id);

            if (projeto == null) {
                model.addAttribute("erro", "Projeto não encontrado.");
                return "error";
            }

            // Obtém o aluno logado
            Aluno alunoLogado = AlunoDao.getByCpf(conn, principal.getName());

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
                    return "conclusao";
                }
            }

            model.addAttribute("aluno", alunoLogado);
            model.addAttribute("projeto", projeto);
            return "aplicacao";
        } catch (Exception e) {
            e.printStackTrace();
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

            // Verifica se o aluno já se candidatou ao projeto
            List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(oportunidadeId);
            for (Candidatura candidatura : candidaturas) {
                if (candidatura.getCandidato().getId() == alunoLogado.getId()) {
                    model.addAttribute("erro", "Você já se candidatou a este projeto.");
                    return "error";
                }
            }

            // Cria e salva a candidatura
            Candidatura candidatura = new Candidatura();
            candidatura.setCandidato(alunoLogado);
            candidatura.setIDoportunidade(oportunidadeId);
            candidatura.setMensagem(mensagem);
            candidatura.setDataAplicacao(java.time.LocalDateTime.now());
            candidatura.setStatus(StatusCandidatura.EM_ANDAMENTO);

            candidaturaDao.salvar(candidatura);

            model.addAttribute("sucesso", "Candidatura enviada com sucesso!");
            model.addAttribute("projeto", projeto);
            model.addAttribute("aluno", alunoLogado);

            return "conclusao";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao salvar candidatura.");
            return "error";
        }
    }

    // Parte do professor

    /**
     * Exibe todas as candidaturas para um projeto específico.
     *
     * @param id        O ID do projeto
     * @param model     Modelo para passar dados à view
     * @param principal Objeto que representa o professor logado
     * @return O nome da view a ser exibida
     */
    @GetMapping("/professor/candidatos/{id}")
    public String exibirCandidaturas(@PathVariable Long id, Model model, Principal principal) {
        try (Connection conn = dataSource.getConnection()) {
            Projeto projeto = ProjetoDao.get(conn, id);

            if (projeto == null) {
                model.addAttribute("erro", "Projeto não encontrado.");
                return "error";
            }

            // Verifica o professor logado
            try {
                Professor professorLogado = ProfessorDao.getByUsuario_id(conn, Long.parseLong(principal.getName()));
                if (professorLogado != null) {
                    model.addAttribute("professor", professorLogado);
                }
            } catch (NumberFormatException e) {
                model.addAttribute("errluo", "Erro ao identificar o professor logado.");
                return "error";
            }

            // Recupera as candidaturas do projeto
            List<Candidatura> candidaturas = candidaturaDao.listarPorProjeto(id);

            if (candidaturas == null || candidaturas.isEmpty()) {
                model.addAttribute("info", "Ainda não há candidaturas para este projeto.");
            } else {
                // Adiciona as fotos dos alunos à lista de candidaturas
                for (Candidatura candidatura : candidaturas) {
                    Aluno aluno = AlunoDao.get(conn, candidatura.getCandidato().getId());
                    if (aluno.getFotoPerfil() != null) {
        			    String fotoPerfilBase64 = Base64.getEncoder().encodeToString(aluno.getFotoPerfil());
        			    model.addAttribute("fotoPerfil", fotoPerfilBase64);
        			}
                }
            }

            model.addAttribute("candidaturas", candidaturas);
            model.addAttribute("projeto", projeto);

            return "professor/candidatos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar as candidaturas.");
            return "error";
        }
    }


    /**
     * Exibe a tela de validação de candidatura para um professor.
     *
     * @param candidaturaId ID da candidatura
     * @param model         Modelo para passar dados à view
     * @return O nome da view a ser exibida
     */
    
	@Autowired
    private HDataSource ds;

    @GetMapping("/professor/validarCandidatura/{candidaturaId}")
    public String exibirTelaValidacao(@PathVariable Long candidaturaId, Model model) {
        try (Connection conn = ds.getConnection()){
            
            Candidatura candidatura = candidaturaDao.get(candidaturaId);
            
            Aluno a = AlunoDao.get(conn, candidatura.getId());
			
			if (a.getFotoPerfil() != null) {
			    String fotoPerfilBase64 = Base64.getEncoder().encodeToString(a.getFotoPerfil());
			    model.addAttribute("fotoPerfil", fotoPerfilBase64);
			}

            if (candidatura == null) {
                model.addAttribute("erro", "Candidatura não encontrada.");
                return "error";
            }

            model.addAttribute("candidatura", candidatura);
            return "professor/validarCandidatura";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar dados da candidatura.");
            return "error";
        }
    }

    /**
     * Processa a validação ou invalidação de uma candidatura após a escolha do professor.
     *
     * @param candidaturaId ID da candidatura
     * @param acao          Ação de validação ou invalidação
     * @param model         Modelo para passar dados à view
     * @return O nome da view a ser exibida
     */
    @PostMapping("/professor/validarCandidatura/{candidaturaId}")
    public String processarCandidatura(@PathVariable Long candidaturaId, @RequestParam("acao") String acao,
                                       Model model) {
        try {
            // Obtém a candidatura usando o ID da candidatura
            Candidatura candidatura = candidaturaDao.get(candidaturaId);

            if (candidatura == null) {
                model.addAttribute("erro", "Candidatura não encontrada.");
                return "error";
            }

            // Normaliza a ação (remove espaços extras)
            acao = acao.trim().toUpperCase();

            // Define o status conforme a ação recebida
            StatusCandidatura status = null;
            if ("VALIDAR".equals(acao)) {
                status = StatusCandidatura.VALIDADA;
            } else if ("INVALIDAR".equals(acao)) {
                status = StatusCandidatura.INVALIDADA;
            } else {
                model.addAttribute("erro", "Ação desconhecida.");
                return "error";
            }

            // Atualiza o status da candidatura
            candidaturaDao.atualizarStatus(candidaturaId, status);

            // Define a mensagem de sucesso
            if ("VALIDAR".equals(acao)) {
                model.addAttribute("sucesso", "Candidatura aprovada com sucesso!");
            } else {
                model.addAttribute("sucesso", "Candidatura invalidada com sucesso!");
            }

            // Retorna para a lista de candidaturas do projeto
            return "redirect:/projeto/professor/candidatos/" + candidatura.getIDoportunidade();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao processar a candidatura.");
            return "error";
        }
    }

    /**
     * Exibe os detalhes de uma candidatura para um professor.
     *
     * @param candidaturaId ID da candidatura
     * @param model         Modelo para passar dados à view
     * @return O nome da view a ser exibida
     */
    @GetMapping("/professor/verCandidatura/{candidaturaId}")
    public String exibirDetalhesCandidatura(@PathVariable Long candidaturaId, Model model) {
        try (Connection conn = ds.getConnection()){
            // Busca a candidatura pelo ID
            Candidatura candidatura = candidaturaDao.get(candidaturaId);
            
            
            Aluno a = AlunoDao.get(conn, candidaturaId);
			
			if (a.getFotoPerfil() != null) {
			    String fotoPerfilBase64 = Base64.getEncoder().encodeToString(a.getFotoPerfil());
			    model.addAttribute("fotoPerfil", fotoPerfilBase64);
			}
            if (candidatura == null) {
                model.addAttribute("erro", "Candidatura não encontrada.");
                return "error";
            }

            // Adiciona a candidatura no modelo para exibir na página
            model.addAttribute("candidatura", candidatura);
            return "professor/verCandidatura"; // Nome da view para exibir detalhes
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar os detalhes da candidatura.");
            return "error";
        }
    }

}
