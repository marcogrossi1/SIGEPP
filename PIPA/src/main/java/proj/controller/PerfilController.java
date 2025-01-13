package proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import proj.dao.AlunoService;
import proj.dao.UsuarioDao;
import proj.model.Aluno;
import proj.model.Usuario;
import proj.service.UsuarioService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private UsuarioDao usuarioService;

    // Endpoint para buscar o perfil com todos os dados
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getPerfil(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        // Busca o perfil do aluno associado ao id e usuário autenticado
        Aluno aluno = alunoService.getPerfil(id, usuario);
        return ResponseEntity.ok(aluno);
    }

    // Endpoint para atualizar o perfil (foto de perfil, banner, descrição)
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> updatePerfil(@PathVariable Long id,
                                              @RequestParam(value = "descricao") String descricao,
                                              @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
                                              @RequestParam(value = "banner", required = false) MultipartFile banner,
                                              @AuthenticationPrincipal Usuario usuario) {
        // Chama o serviço para atualizar o perfil
        Aluno updatedAluno = alunoService.updatePerfil(id, descricao, fotoPerfil, banner, usuario);
        return ResponseEntity.ok(updatedAluno);
    }
}
