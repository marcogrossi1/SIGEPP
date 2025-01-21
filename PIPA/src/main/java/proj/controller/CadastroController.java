package proj.controller;

import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.model.Aluno;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private HDataSource ds;

    @GetMapping
    public String mostraFormularioCadastroAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "cadastroAluno";
    }

    @PostMapping
    public String criaNovoAluno(@ModelAttribute Aluno aluno, Model model) {
        try (Connection conn = ds.getConnection()) {
            AlunoDao.insert(conn, aluno);
            return "redirect:/aluno";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", e.getMessage());
            return "cadastroAluno";
        }
    }
}