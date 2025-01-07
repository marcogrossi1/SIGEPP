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
import java.security.Principal;

@Controller
@RequestMapping("/projeto")
public class CandidaturaController {

    @Autowired
    private CandidaturaDao candidaturaDao;

    @Autowired
    private DataSource dataSource;

    /**
     * Exibe o formulário de candidatura com o ID do projeto passado como parâmetro.
     */
    @GetMapping("/{id}/aplicacao")
    public String exibirFormularioCandidatura(
            @PathVariable Long id, Model model,
            Principal principal) {

        try (Connection conn = dataSource.getConnection()) {
            Projeto projeto = ProjetoDao.get(conn, id);

            // Obtém o aluno logado pelo nome de usuário (cpf, por exemplo)
            Aluno alunoLogado = AlunoDao.getByCfp(conn, principal.getName());

            model.addAttribute("aluno", alunoLogado);
            model.addAttribute("projeto", projeto);

            return "aplicacao";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar o projeto.");
            return "error";
        }
    }

    /**
     * Recebe os dados do formulário e cria uma candidatura.
     */
    @PostMapping("/aplicacao")
    public String enviarCandidatura(
            @RequestParam("mensagem") String mensagem,
            @RequestParam("IDoportunidade") Long oportunidadeId,
            Model model, Principal principal) {

        if (principal != null) {
            try (Connection conn = dataSource.getConnection()) {
                Aluno alunoLogado = AlunoDao.getByCfp(conn, principal.getName());

                Candidatura candidatura = new Candidatura();
                candidatura.setCandidato(alunoLogado);
                candidatura.setIDoportunidade(oportunidadeId);
                candidatura.setMensagem(mensagem);
                candidatura.setDataAplicacao(java.time.LocalDateTime.now());

                candidaturaDao.salvar(candidatura);
                model.addAttribute("sucesso", "Candidatura enviada com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("erro", "Erro ao salvar candidatura.");
            }
        } else {
            model.addAttribute("erro", "Usuário não autenticado.");
        }

        return "aplicacao";
    }
}
