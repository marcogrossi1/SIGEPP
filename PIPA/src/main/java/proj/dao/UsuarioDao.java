package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;  // Importação do DataSource

import org.springframework.beans.factory.annotation.Autowired;  // Importação para injeção de dependência
import org.springframework.stereotype.Repository;

import proj.model.Secao;
import proj.model.Usuario;

@Repository
public class UsuarioDao {

    @Autowired
    private DataSource dataSource;  // Injeção do DataSource (conexão com o banco)

    private final static String getsql = "SELECT * FROM usuario WHERE id = ?";
    private final static String getByNomeSql = "SELECT * FROM usuario WHERE nome = ?";
    private final static String listsql = "SELECT * FROM usuario";
    private final static String insertsql = "INSERT INTO usuario (nome, senha, role) VALUES( ?, ?, ?)";
    private final static String updatesql = "UPDATE usuario SET nome = ?, senha = ?, role = ? WHERE id = ?";
    private final static String deletesql = "DELETE FROM usuario WHERE id = ?";
    private final static String listSecoesSql = "SELECT * FROM secao WHERE usuario_id = ?";
    private final static String insertSecaoSql = "INSERT INTO secao (tipo_seccao, usuario_id) VALUES (?, ?)";

    // Adicionando as novas variáveis SQL
    private final static String updateForNomeSql = "UPDATE usuario SET nome = ? WHERE id = ?";
    private final static String updateForSenhaSql = "UPDATE usuario SET senha = ? WHERE id = ?";
    private final static String updateForRoleSql = "UPDATE usuario SET role = ? WHERE id = ?";

    public void save(Secao secao) {
        String sql = "INSERT INTO secao (tipo_secao, usuario_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();  // Usando a injeção de dataSource
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, secao.getTipoSecao());
            stmt.setLong(2, secao.getUsuario().getId());  // Associa o usuário à seção
            stmt.executeUpdate();  // Executa o comando SQL de inserção

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a seção", e);
        }
    }
    
    public Secao findById(Long id) {
        String sql = "SELECT * FROM secao WHERE id = ?";
        try (Connection conn = dataSource.getConnection();  // Usando a injeção de dataSource
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

    private static void closeResource(PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Logando erro
        }
    }

    private static void closeResource(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Logando erro
        }
        closeResource(ps); // Fechar PreparedStatement
    }

    static Usuario set(ResultSet rs) throws SQLException {
        Usuario vo = new Usuario();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setSenha(rs.getString("senha"));
        vo.setRole(rs.getString("role"));
        return vo;
    }

    // Buscar usuário por ID
    public static Usuario get(Connection conn, long id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found [" + id + "]");  // Lançando exceção se não encontrar
            }
            return set(rs);
        } finally {
            closeResource(ps, rs);
        }
    }

    // Buscar seções do usuário
    public static List<Secao> listSecoes(Connection conn, long usuarioId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listSecoesSql);
            ps.setLong(1, usuarioId);
            rs = ps.executeQuery();
            List<Secao> secoes = new ArrayList<>();
            while (rs.next()) {
                Secao secao = new Secao();
                secao.setId(rs.getLong("id"));
                secao.setTipoSecao(rs.getString("tipo_seccao"));
                secoes.add(secao);
            }
            return secoes;
        } finally {
            closeResource(ps, rs);
        }
    }

    // Inserir uma seção
    public static void insertSecao(Connection conn, Secao secao) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insertSecaoSql);
            ps.setString(1, secao.getTipoSecao());
            ps.setLong(2, secao.getUsuario().getId());
            ps.executeUpdate();
            conn.commit(); // Realiza o commit após a inserção
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }
    
    public static void deleteSecao(Connection conn, long secaoId) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM secao WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, secaoId);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new SQLException("Object not found [" + secaoId + "] .");
            }
            conn.commit(); // Realiza o commit após a exclusão
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }

    // Buscar usuário por nome
    public static Usuario getByNome(Connection conn, String nome) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found By [" + nome + "]");  // Lançando exceção se não encontrar
            }
            return set(rs);
        } finally {
            closeResource(ps, rs);
        }
    }

    // Listar todos os usuários
    public static ArrayList<Usuario> list(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            ArrayList<Usuario> list = new ArrayList<>();
            while (rs.next()) {
                list.add(set(rs));
            }
            return list;
        } finally {
            closeResource(ps, rs);
        }
    }

    // Inserir novo usuário
    public static void insert(Connection conn, Usuario vo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getSenha());
            ps.setString(3, vo.getRole());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                vo.setId(rs.getLong(1));
            } else {
                throw new SQLException("Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados");
            }
            conn.commit(); // Realiza o commit após a inserção
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps, rs);
        }
    }

    // Atualizar usuário
    public static void update(Connection conn, Usuario vo) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getSenha());
            ps.setString(3, vo.getRole());
            ps.setLong(4, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + vo.getId() + "] .");
            }
            conn.commit(); // Realiza o commit após a atualização
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }

    // Atualizar nome do usuário
    public static void updateForNome(Connection conn, long id, String nome) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForNomeSql);
            ps.setString(1, nome);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            conn.commit(); // Realiza o commit após a atualização
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }

    // Atualizar senha do usuário
    public static void updateForSenha(Connection conn, long id, String senha) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForSenhaSql);
            ps.setString(1, senha);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            conn.commit(); // Realiza o commit após a atualização
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }

    // Atualizar role do usuário
    public static void updateForRole(Connection conn, long id, String role) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForRoleSql);
            ps.setString(1, role);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            conn.commit(); // Realiza o commit após a atualização
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }

    // Deletar usuário
    public static void delete(Connection conn, long id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletesql);
            ps.setLong(1, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            conn.commit(); // Realiza o commit após a exclusão
        } catch (SQLException e) {
            conn.rollback(); // Rollback em caso de erro
            throw e;
        } finally {
            closeResource(ps);
        }
    }
}
