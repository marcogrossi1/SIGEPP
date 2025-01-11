package proj.controller;

import proj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DocumentoController {

    @Autowired
    private DocumentoRepository documentoRepository;

    @GetMapping("/upload")
    public String formularioUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAluno") Long idAluno,
            @RequestParam("idProjeto") Long idProjeto,
            Model model
    ) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("mensagem", "Nenhum arquivo foi enviado!");
                return "aluno/upload";
            }

            Documento documento = new Documento();
            DocumentoId documentoId = new DocumentoId(idAluno, idProjeto);
            documento.setId(documentoId);
            documento.setNomeArquivo(file.getOriginalFilename());
            documento.setConteudo(file.getBytes());
            documentoRepository.save(documento);

            model.addAttribute("mensagem", "Upload realizado com sucesso!");

        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao realizar upload: " + e.getMessage());
            e.printStackTrace();
        }
        return "aluno/upload";
    }
}