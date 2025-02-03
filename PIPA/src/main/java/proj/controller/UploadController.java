package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import proj.dao.HDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class UploadController {

    @Autowired
    private HDataSource ds;

    @PostMapping("/uploadRelatorio")
    public String uploadRelatorio(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "por favor, selecione um arquivo para enviar.");
            return "redirect:uploadStatus";
        }

        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO relatorio_estagio (file_name, file_data) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, file.getOriginalFilename());
                ps.setBytes(2, file.getBytes());
                ps.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "falha ao enviar o arquivo: " + e.getMessage());
            return "redirect:uploadStatus";
        }

        redirectAttributes.addFlashAttribute("message", "Você enviou com sucesso '" + file.getOriginalFilename() + "'");
        return "redirect:/uploadStatus";
    }
}