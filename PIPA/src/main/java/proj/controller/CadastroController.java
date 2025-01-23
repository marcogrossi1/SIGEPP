package proj.controller;

import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import proj.dao.AdministradorDao;
import proj.dao.HDataSource;
import proj.model.Administrador;
import proj.dao.AlunoDao;
import proj.dao.ProfessorDao;
import proj.dao.EmpresaDao;
import proj.model.Aluno;
import proj.model.Professor;
import proj.model.Empresa;


@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private HDataSource ds;

    @GetMapping("/aluno")
    public String mostraFormularioCadastroAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastroAluno";
    }

    @PostMapping("/aluno")
    public String criaNovoAluno(@ModelAttribute Aluno aluno, Model model) {
        try (Connection conn = ds.getConnection()) {
            AlunoDao.insert(conn, aluno);
            return "redirect:/aluno";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", e.getMessage());
            return "aluno/home";
        }
    }

    @GetMapping("/professor")
    public String mostraFormularioCadastroProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/cadastroProfessor";
    }
    
    @PostMapping("/professor")
    public String criaNovoProfessor(@ModelAttribute Professor professor, Model model) {
        try (Connection conn = ds.getConnection()) {
            ProfessorDao.insert(conn, professor);
            return "redirect:/professor";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", e.getMessage());
            return "professor/home";
        }
    }

    @GetMapping("/empresa")
    public String mostraFormularioCadastroEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/cadastroEmpresa";
    }

    @PostMapping("/empresa")
    public String criaNovaEmpresa(@ModelAttribute Empresa empresa, Model model) {
        try (Connection conn = ds.getConnection()) {
            EmpresaDao.insert(conn, empresa);
            return "redirect:/empresa";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", e.getMessage());
            return "empresa/home";
        }
    }

    @GetMapping("/administrador")
    public String mostraFormularioCadastroAdministrador(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "administrador/cadastroAdministrador";
    }

   // @PostMapping("/administrador")
   // public String criaNovoAdministrador(@ModelAttribute Administrador administrador, Model model) {
   //     try (Connection conn = ds.getConnection()) {
   //         AdministradorDao.insert(conn, administrador);
   ///         return "redirect:/administrador";
    //    } catch (Exception e) {
    //        e.printStackTrace();
      ///      model.addAttribute("erro", e.getMessage());
      //      return "administrador/home";
      //  }
    //}
}