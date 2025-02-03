package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proj.model.Avaliacao;
import proj.repository.AvaliacaoRepository;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @GetMapping("/{alunoId}")
    public List<Avaliacao> listarAvaliacoesPorAluno(@PathVariable Long alunoId) {
        return avaliacaoRepository.findByAlunoId(alunoId);
    }

    @PostMapping
    public Avaliacao adicionarAvaliacao(@RequestBody Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }
}
