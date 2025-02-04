package proj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import proj.model.DocumentoP;
import proj.model.DocumentoId;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class DocumentoPDao {
	
	@Autowired
    private final DataSource dataSource;

    
    public DocumentoPDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DocumentoP buscarPorId(DocumentoId id) {
        String sql = "SELECT nome_arquivo, conteudo FROM documentop WHERE id_aluno = ? AND id_projeto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id.getIdAluno());
            stmt.setLong(2, id.getIdProjeto());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DocumentoP documento = new DocumentoP();
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
        String sql = "DELETE FROM documentop WHERE id_aluno = ? AND id_projeto = ?";
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
        String sql = "DELETE FROM documentop WHERE id_projeto = ?";
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
