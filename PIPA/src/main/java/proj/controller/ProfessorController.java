package proj.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import proj.model.Projeto;
import proj.model.Professor;
import proj.model.Usuario;
import proj.dao.ProfessorDao;
import proj.dao.ProjetoDao;
import proj.dao.UsuarioDao;
import proj.dao.HDataSource;

@Controller
public class ProfessorController {

    @Autowired
    private HDataSource ds;

    @GetMapping("/professor")
    public String mostraHomeProfessor(Principal principal, Model model) {
        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            if (!u.getRole().equals("Professor")) {
                return mostraPaginaDeErro(model, "Usuário não é um Professor!");
            }

            long id = convertStringToLong(principal.getName()); // gambiarra
            Professor p = ProfessorDao.getByUsuario_id(conn, id);

            model.addAttribute("professor", p);
            return "professor/home";
        } catch (Exception e) {
            return "erro";
        }
    }

    @GetMapping("/professor/projetos")
    public String mostraProjetosProfessor(@RequestParam long id, Model model, Principal principal) {
        try (Connection conn = ds.getConnection()) {
            ArrayList<Projeto> projetos = ProjetoDao.listPorIdProfessor(conn, id);

            long professorId = convertStringToLong(principal.getName()); // gambiarra
            Professor p = ProfessorDao.getByUsuario_id(conn, professorId);

            model.addAttribute("professor", p);
            model.addAttribute("projetos", projetos);

            return "professor/projetos";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }

    @GetMapping("/professor/editarProjeto")
    public String editarProjeto(@RequestParam long id, Model model, Principal principal) {
        try (Connection conn = ds.getConnection()) {

            // Passando o professor Id para o model
            long professorId = convertStringToLong(principal.getName()); // gambiarra
            Professor p = ProfessorDao.getByUsuario_id(conn, professorId);
            model.addAttribute("professor", p);

            // Passando o projeto para o model
            Projeto projeto = ProjetoDao.get(conn, id);
            model.addAttribute("projeto", projeto);

            return "professor/editarProjeto";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/professor/projetos/editarProjeto")
    public String salvarProjeto(@ModelAttribute Projeto projeto, Model model, Principal principal) {
        System.out.println("ID: " + projeto.getId());
        System.out.println("Nome: " + projeto.getNome());
        System.out.println("Responsável: " + projeto.getResponsavel());
        System.out.println("Descrição: " + projeto.getDescricao());
        System.out.println("Carga Horária: " + projeto.getCargaHoraria());
        System.out.println("Vagas Remuneradas: " + projeto.getVagasRemuneradas());
        System.out.println("Vagas Voluntárias: " + projeto.getVagasVoluntarias());
        System.out.println("Requisito: " + projeto.getRequisito());

        try (Connection conn = ds.getConnection()) {
            long professorUsuarioId = convertStringToLong(principal.getName()); // gambiarra
            Professor p = ProfessorDao.getByUsuario_id(conn, professorUsuarioId);
            long professorId = p.getId();

            // Atualiza o projeto no banco de dados
            ProjetoDao.update(conn, projeto);
            model.addAttribute("message", "Projeto atualizado com sucesso!");
            return "redirect:/professor/projetos?id=" + professorId; // ou alguma outra página de sucesso
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/professor/projetos/deletarProjeto")
    public String deletarProjeto(@RequestParam int id, Model model, Principal principal) {
        try (Connection conn = ds.getConnection()) {
            long professorUsuarioId = convertStringToLong(principal.getName());
            Professor p = ProfessorDao.getByUsuario_id(conn, professorUsuarioId);
            long professorId = p.getId();

            ProjetoDao.delete(conn, id);

            // Adiciona uma mensagem de ucesso ao modelo
            model.addAttribute("message", "Projeto deletado com sucesso!");

            return "redirect:/professor/projetos?id=" + professorId;
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao deletar o projeto: " + e.getMessage());
            return "erro";
        }
    }

    @GetMapping("professor/projetos/criarProjeto")
    public String mostraCriarProjeto(Principal principal, Model model) {
        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            if (!u.getRole().equals("Professor")) {
                return mostraPaginaDeErro(model, "Usuário não é um Professor!");
            }

            long id = convertStringToLong(principal.getName()); // gambiarra
            Professor p = ProfessorDao.getByUsuario_id(conn, id);

            model.addAttribute("projeto", new Projeto());
            model.addAttribute("professor", p);
            return "professor/criarProjeto";
        } catch (Exception e) {
            return "erro";
        }
    }

    @PostMapping("/professor/projetos/criarProjeto")
    public String criarProjeto(@ModelAttribute Projeto projeto, Model model, Principal principal) {
        try (Connection conn = ds.getConnection()) {
            long professorUsuarioId = convertStringToLong(principal.getName());
            Professor p = ProfessorDao.getByUsuario_id(conn, professorUsuarioId);
            long professorId = p.getId();

            projeto.setResponsavel(p.getNome());

            ProjetoDao.insert(conn, projeto, professorId);

            // Adiciona uma mensagem de sucesso ao modelo
            model.addAttribute("message", "Projeto criado com sucesso!");
            return "redirect:/professor/projetos?id=" + professorId;
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao criar o projeto: " + e.getMessage());
            return "erro";
        }
    }

    public String mostraPaginaDeErro(Model model, String message) {
        model.addAttribute("message", message);
        return "erro";
    }

    // gambiarra para pegar o id de um professor, pois principal não tem metodo para
    // pegar o id de usuario e os professores não tem cpf
    public static Long convertStringToLong(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String noZeros = input.replace("0", "");
        if (noZeros.isEmpty()) {
            return 0L;
        }
        return Long.parseLong(noZeros);
    }
}