package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import proj.model.Secao;

@Repository
public class SecaoDao {

    @Autowired
    private DataSource dataSource;  // Injeção da fonte de dados (DataSource)

    // Método para salvar a seção
    public void save(Secao secao) {
        String sql = "INSERT INTO secao (tipo_secao, usuario_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, secao.getTipoSecao());
            stmt.setLong(2, secao.getUsuario().getId());  // Associa o usuário à seção
            stmt.executeUpdate();  // Executa o comando SQL de inserção

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a seção", e);
        }
    }

    // Exemplo de outro método, como "findById"
    public Secao findById(Long id) {
        String sql = "SELECT * FROM secao WHERE id = ?";
        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            var result = stmt.executeQuery();

            if (result.next()) {
                Secao secao = new Secao();
                secao.setId(result.getLong("id"));
                secao.setTipoSecao(result.getString("tipo_secao"));
                return secao;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a seção", e);
        }
        return null;  // Retorna null se não encontrar
    }

    // Métodos adicionais, como delete, update, etc., podem ser implementados dessa forma
}
