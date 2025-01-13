import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.List;

import jakarta.transaction.Transactional;
import proj.dao.SecaoDao;
import proj.dao.UsuarioDao;
import proj.model.Secao;
import proj.model.Usuario;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioDao usuarioRepository; // Repositório de usuário
    @Autowired
    private SecaoDao secaoRepository; // Repositório de seção

    // Atualiza o perfil do usuário
    @Transactional
    @PostMapping("/perfil/atualizar")
    public String atualizarPerfil(@RequestParam String descricao, 
                                  @RequestParam(required = false) MultipartFile fotoPerfil, 
                                  @RequestParam(required = false) MultipartFile banner,
                                  @RequestParam(required = false) List<Long> secaoIds, 
                                  @AuthenticationPrincipal Usuario usuario) {

        // Atualiza os dados do usuário
        usuario.setDescricao(descricao);
        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            usuario.setFotoPerfil(fotoPerfil.getBytes());
        }
        if (banner != null && !banner.isEmpty()) {
            usuario.setBanner(banner.getBytes());
        }

        // Salva o usuário utilizando o método manual
        usuarioRepository.save(usuario);  // Método do UsuarioDao

        // Atualiza as seções associadas ao usuário
        if (secaoIds != null) {
            for (Long secaoId : secaoIds) {
                Secao secao = secaoRepository.findById(secaoId); // Método do SecaoDao
                if (secao != null) {
                    secao.setUsuario(usuario);  // Associa a seção ao usuário
                    secaoRepository.save(secao);  // Método do SecaoDao
                } else {
                    throw new RuntimeException("Seção não encontrada para o ID: " + secaoId);
                }
            }
        }

        return "redirect:/perfil"; // Redireciona para o perfil após a atualização
    }

    // Adiciona uma nova seção ao perfil do usuário
    @PostMapping("/perfil/adicionar-seccao")
    @ResponseBody
    public String adicionarSecao(@RequestParam String tipoSecao, 
                                  @AuthenticationPrincipal Usuario usuario) {
        // Cria uma nova seção
        Secao novaSecao = new Secao();
        novaSecao.setTipoSecao(tipoSecao);
        novaSecao.setUsuario(usuario); // Associa a nova seção ao usuário
        secaoRepository.save(novaSecao); // Método do SecaoDao

        return "Seção adicionada com sucesso!";
    }
}
