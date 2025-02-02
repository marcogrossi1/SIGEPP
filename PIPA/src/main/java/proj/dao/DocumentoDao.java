package proj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import proj.model.Documento;
import proj.model.DocumentoId;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class DocumentoDao {

    private final DataSource dataSource;

    @Autowired
    public DocumentoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Documento buscarPorId(DocumentoId id) {
        String sql = "SELECT nome_arquivo, conteudo FROM documento WHERE id_aluno = ? AND id_projeto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id.getIdAluno());
            stmt.setLong(2, id.getIdProjeto());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Documento documento = new Documento();
                    documento.setId(id);
                    documento.setNomeArquivo(rs.getString("nome_arquivo"));
                    documento.setConteudo(rs.getBytes("conteudo"));
                    return documento;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar documento por ID", e);
        }
        return null;
    }
    
    public void deletarPorId(DocumentoId id) {
        String sql = "DELETE FROM documento WHERE id_aluno = ? AND id_projeto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id.getIdAluno());
            stmt.setLong(2, id.getIdProjeto());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum documento encontrado para exclusão.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir documento por ID", e);
        }
    }
    public void deletarTodosPorProjetoId(long idProjeto) {
        String sql = "DELETE FROM documento WHERE id_projeto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idProjeto);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum documento encontrado para exclusão neste projeto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir documentos do projeto por ID", e);
        }
    }
}
