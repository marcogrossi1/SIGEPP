package proj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import proj.model.DocumentoP;
import proj.model.DocumentoId;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class DocumentoPDao {

    private final DataSource dataSource;

    @Autowired
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
}
