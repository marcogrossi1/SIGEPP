import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secao")
public class SecaoController {

    @Autowired
    private SecaoRepository secaoRepository;

    // Consultar seções do usuário
    @GetMapping("/{usuarioId}")
    public List<Secao> getSecaoByUsuario(@PathVariable Long usuarioId) {
        return secaoRepository.findByUsuarioId(usuarioId);
    }

    // Criar ou atualizar seção do usuário
    @PostMapping("/{usuarioId}")
    public Secao createOrUpdateSecao(@PathVariable Long usuarioId, @RequestBody Secao secao) {
        secao.setUsuarioId(usuarioId);
        return secaoRepository.save(secao);
    }
}
