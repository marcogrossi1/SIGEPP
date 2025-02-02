package proj.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import proj.model.DocumentoP;
import proj.model.DocumentoPRepository;
import proj.model.Documento;
import proj.model.DocumentoId;
import proj.model.DocumentoRepository;
import proj.service.NotificacaoService;

@Controller
public class DocumentoController {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private NotificacaoService notificacaoService;
    
    @Autowired
    private DocumentoPRepository documentoPRepository;

    @GetMapping("/upload")
    public String formularioUpload() {
        return "aluno/upload";
    }
    
    
    @PostMapping("/upload")
    public String uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAluno") Long idAluno,
            @RequestParam("idProjeto") Long idProjeto,
            @RequestParam("nomeEstagio") String nomeEmpresa,
            @RequestParam("usuarioId") Long usuarioId,
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
            String mensagemNotificacao = "Os documentos para o estágio " + nomeEmpresa + " foram enviados.";
            notificacaoService.salvarNotificacao(usuarioId, mensagemNotificacao);
            model.addAttribute("status", "Upload realizado com sucesso!");
            return "redirect:/aluno/inscrever-estagio?n=" + idProjeto;
        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao realizar upload: " + e.getMessage());
            e.printStackTrace();
        }
        return "aluno/upload";
        
    }
    
    @PostMapping("/uploadprojeto")
    public String uploadDocumentoP(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAluno") Long idAluno,
            @RequestParam("idProjeto") Long idProjeto,
            @RequestParam("nomeprojeto") String nomeProjeto,
            @RequestParam("usuarioId") Long usuarioId,
            Model model
    ) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("mensagem", "Nenhum arquivo foi enviado!");
                return "aluno/uploadprojeto"; 
            }
            DocumentoP documentoP = new DocumentoP();
            DocumentoId documentoId = new DocumentoId(idAluno, idProjeto);
            documentoP.setId(documentoId);
            documentoP.setNomeArquivo(file.getOriginalFilename());
            documentoP.setConteudo(file.getBytes());
            documentoPRepository.save(documentoP);

            String mensagemNotificacao = "Os documentos para o projeto " + nomeProjeto + " foram enviados.";
            notificacaoService.salvarNotificacao(usuarioId, mensagemNotificacao);
            model.addAttribute("mensagem", "Upload realizado com sucesso!");

        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao realizar upload: " + e.getMessage());
            e.printStackTrace();
        }
        return "aluno/uploadprojeto";
    }
}