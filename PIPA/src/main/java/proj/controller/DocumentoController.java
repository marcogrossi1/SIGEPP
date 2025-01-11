package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import proj.model.DocumentoRepository;
import proj.model.Documento;
import java.io.IOException;

@Controller
public class DocumentoController{

    @Autowired
    private DocumentoRepository documentoRepository;

    @GetMapping("/")
    public String formularioUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocumento(@RequestParam("file") MultipartFile file, Model model) {
        try {
            Documento documento = new Documento();
            documento.setNomeArquivo(file.getOriginalFilename());
            documento.setConteudo(file.getBytes());
            documentoRepository.save(documento);

            model.addAttribute("mensagem", "Upload realizado com sucesso!");
        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao realizar upload: " + e.getMessage());
        }
        return "upload";
    }
}