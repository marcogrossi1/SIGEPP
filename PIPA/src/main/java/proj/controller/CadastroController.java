package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CadastroController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping("/cadastroAluno")
    public String cadastrarAluno(@RequestParam("cpf") String cpf,
                                 @RequestParam("nome") String nome,
                                 @RequestParam("curso") String curso,
                                 @RequestParam("campus") String campus,
                                 @RequestParam("email") String email,
                                 @RequestParam("periodo") String periodo,
                                 @RequestParam("usuario_id") String usuarioId) {
        try {
            Long usuarioIdLong = Long.parseLong(usuarioId);
            Aluno aluno = new Aluno(cpf, nome, curso, campus, email, periodo, usuarioIdLong);
            alunoRepository.save(aluno);
            return "redirect:/cadastroAluno";
        } catch (NumberFormatException e) {
            return "redirect:/error";
        }
    }
}

