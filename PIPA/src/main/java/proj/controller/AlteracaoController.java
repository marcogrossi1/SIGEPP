import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alteracao")
public class AlteracaoController {

    @Autowired
    private AlteracaoRepository alteracaoRepository;

    // Consultar histórico de alterações de um usuário
    @GetMapping("/{usuarioId}")
    public List<Alteracao> getAlteracoesByUsuario(@PathVariable Long usuarioId) {
        return alteracaoRepository.findByUsuarioId(usuarioId);
    }

    // Criar uma nova alteração
    @PostMapping("/{usuarioId}")
    public Alteracao createAlteracao(@PathVariable Long usuarioId, @RequestBody Alteracao alteracao) {
        alteracao.setUsuarioId(usuarioId);
        return alteracaoRepository.save(alteracao);
    }
}
