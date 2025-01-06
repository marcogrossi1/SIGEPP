package proj.controller;

import proj.model.Candidatura;
import proj.dao.CandidaturaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaDao candidaturaDao;

    /**
     * Endpoint para enviar uma nova candidatura
     */
    @PostMapping
    public void enviarCandidatura(@RequestBody Candidatura candidatura) {
        candidatura.setDataAplicacao(LocalDateTime.now());
        try {
            candidaturaDao.salvar(candidatura);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar candidatura.", e);
        }
    }

    /**
     * Endpoint para listar candidaturas por projeto
     */
    @GetMapping("/projeto/{oportunidadeId}")
    public List<Candidatura> listarCandidaturasPorProjeto(@PathVariable Long oportunidadeId) {
        try {
            return candidaturaDao.listarPorProjeto(oportunidadeId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar candidaturas.", e);
        }
    }
}
