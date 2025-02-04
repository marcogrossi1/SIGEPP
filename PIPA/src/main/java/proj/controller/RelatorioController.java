package proj.controller;

import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import proj.dao.HDataSource;
import proj.dao.RelatorioDao;
import proj.dao.UsuarioDao;
import proj.model.Relatorio;
import proj.model.Usuario;

@Controller
public class RelatorioController {

    @Autowired
    private HDataSource ds;

    @GetMapping("/enviarRelatorio")
    public String showEnviarRelatorio() {
        return "enviarRelatorio";
    }

    @PostMapping("/enviarRelatorio")
    public String enviarRelatorio(
        @RequestParam("idEstagio") Long idEstagio,
        @RequestParam("arquivo") MultipartFile arquivo,
        Principal principal, Model model) {
        
        try (Connection conn = ds.getConnection()) {
            Usuario usuario = UsuarioDao.getByNome(conn, principal.getName());
            Relatorio relatorio = new Relatorio();
            relatorio.setIdAluno(usuario.getId());
            relatorio.setIdEstagio(idEstagio);
            relatorio.setArquivo(arquivo.getBytes());

            RelatorioDao.insert(conn, relatorio);
            conn.commit();

            return "redirect:/enviarRelatorio?success=true";
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao enviar relatório: " + e.getMessage());
            return "erro";
        }
    }
}