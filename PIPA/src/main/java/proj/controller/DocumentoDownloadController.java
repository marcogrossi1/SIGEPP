package proj.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import proj.dao.DocumentoDao;
import proj.model.DocumentoId;
import proj.model.Documento;

@RestController

@RequestMapping("/downloade")
public class DocumentoDownloadController {
    private final DocumentoDao documentoDao;
    public DocumentoDownloadController(DocumentoDao documentoPDao) {
        this.documentoDao = documentoPDao;
    }
    @GetMapping
    public void downloadDocumento(
            @RequestParam("alunoId") long alunoId,
            @RequestParam("projetoId") long projetoId,
            HttpServletResponse response) throws IOException {
        DocumentoId documentoId = new DocumentoId(alunoId, projetoId);
        Documento documento = documentoDao.buscarPorId(documentoId);

        if (documento == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Documento não encontrado");
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + documento.getNomeArquivo() + "\"");

        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(documento.getConteudo());
            out.flush();
        }
    }
}