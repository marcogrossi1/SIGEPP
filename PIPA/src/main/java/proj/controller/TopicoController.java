package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import proj.dao.TopicoDao;
import proj.model.Topico;

import java.util.List;

@RestController
@RequestMapping("/api/topico")
public class TopicoController {

    @Autowired
    private TopicoDao topicoRepository;

    // Consultar tópicos de uma seção
    @GetMapping("/{secaoId}")
    public List<Topico> getTopicosBySecao(@PathVariable Long secaoId) {
        return topicoRepository.findBySecaoId(secaoId);
    }

    // Criar ou atualizar tópico de uma seção
    @PostMapping("/{secaoId}")
    public Topico createOrUpdateTopico(@PathVariable Long secaoId, @RequestBody Topico topico) {
        topico.setSecaoId(secaoId);
        return topicoRepository.save(topico);
    }
}
