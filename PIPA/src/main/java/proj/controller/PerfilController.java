import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.io.IOException;
import java.util.List;


@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Para persistir os dados do usuário
    @Autowired
    private SecaoRepository secaoRepository; // Para persistir as seções

    @GetMapping("/perfil")
    public String exibirPerfil(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("secoes", usuario.getSecoes()); // Exibir as seções relacionadas
        return "perfil"; // Nome do arquivo HTML (perfil.html)
    }

    @PostMapping("/perfil/atualizar")
    public String atualizarPerfil(@RequestParam String descricao, 
                                  @RequestParam(required = false) MultipartFile fotoPerfil, 
                                  @RequestParam(required = false) MultipartFile banner,
                                  @RequestParam(required = false) List<String> seccaoIds, // IDs das seções criadas
                                  @AuthenticationPrincipal Usuario usuario) {
        // Atualizar os dados do usuário
        usuario.setDescricao(descricao);

        try {
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                usuario.setFotoPerfil(fotoPerfil.getBytes()); // Lidar com imagem
            }
            if (banner != null && !banner.isEmpty()) {
                usuario.setBanner(banner.getBytes()); // Lidar com imagem
            }

            // Atualizar as seções conforme o id passado
            if (seccaoIds != null) {
                for (String id : seccaoIds) {
                    Secao secao = secaoRepository.findById(Long.parseLong(id)).orElse(null);
                    if (secao != null) {
                        // Modificar a seção (associar ao usuário)
                        secao.setUsuario(usuario);
                        secaoRepository.save(secao); // Atualizar seção no banco
                    }
                }
            }

            usuarioRepository.save(usuario); // Persistir os dados do usuário no banco
        } catch (IOException e) {
            // Tratar erros de upload de arquivos
            e.printStackTrace();
        }
        
        return "redirect:/perfil"; // Redirecionar de volta para a página de perfil
    }

    @PostMapping("/perfil/adicionar-seccao")
    @ResponseBody
    public String adicionarSeccao(@RequestParam String tipoSeccao, 
                                  @AuthenticationPrincipal Usuario usuario) {
        // Lógica para adicionar uma nova seção
        Secao novaSecao = new Secao();
        novaSecao.setTipoSeccao(tipoSeccao);
        novaSecao.setUsuario(usuario);
        secaoRepository.save(novaSecao); // Salvar a seção no banco

        return "Seção adicionada com sucesso!";
    }
}
