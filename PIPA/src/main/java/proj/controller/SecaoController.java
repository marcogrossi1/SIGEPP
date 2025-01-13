package proj.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proj.dao.UsuarioDao;
import proj.model.Secao;
import proj.model.Usuario;

@RestController
@RequestMapping("/secao")
public class SecaoController {

    @Autowired
    private DataSource dataSource;  // Certifique-se de injetar a fonte de dados corretamente

    // Listar seções do usuário
    @GetMapping("/{usuarioId}")
    public List<Secao> getSecoes(@PathVariable long usuarioId) {
        try (Connection conn = dataSource.getConnection()) {
            return UsuarioDao.listSecoes(conn, usuarioId);  // Usando o método de listar seções
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }

    // Adicionar uma nova seção ao usuário
    @PostMapping("/{usuarioId}")
    public void addSecao(@PathVariable long usuarioId, @RequestBody Secao secao) throws NotFoundException {
        try (Connection conn = dataSource.getConnection()) {
            Usuario usuario = UsuarioDao.get(conn, usuarioId);  // Garantir que o usuário exista
            secao.setUsuario(usuario);
            UsuarioDao.insertSecao(conn, secao);  // Inserir seção no banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar seção", e);
        }
    }

    // Deletar uma seção do usuário
    @DeleteMapping("/{usuarioId}/{secaoId}")
    public void deleteSecao(@PathVariable long usuarioId, @PathVariable long secaoId) throws NotFoundException {
        try (Connection conn = dataSource.getConnection()) {
            Usuario usuario = UsuarioDao.get(conn, usuarioId);  // Garantir que o usuário exista
            
            // Consultar a seção para garantir que ela pertence ao usuário
            List<Secao> secoes = UsuarioDao.listSecoes(conn, usuarioId);
            boolean secaoPertenceAoUsuario = secoes.stream()
                                                    .anyMatch(secao -> secao.getId() == secaoId);

            if (!secaoPertenceAoUsuario) {
                throw new NotFoundException();
            }
            
            // Se a seção pertence ao usuário, deletar a seção
            UsuarioDao.deleteSecao(conn, secaoId);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar seção", e);
        }
    }
}
