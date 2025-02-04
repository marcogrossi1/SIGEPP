package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proj.model.Secao;

public class SecaoDao {

    private static final String INSERT_SECAO_SQL = "INSERT INTO secoes (usuario_id, titulo, tipo, conteudoTexto, comprimentoConteudoTexto, alturaConteudoTexto, leftConteudoTexto, topConteudoTexto, conteudoImagem, ordem) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SECOES_BY_USUARIO_SQL = "SELECT * FROM secoes WHERE usuario_id = ?";
    private static final String DELETE_SECAO_SQL = "DELETE FROM secoes WHERE id = ?";
    private static final String UPDATE_SECAO_SQL = "UPDATE secoes SET titulo = ?, tipo = ?, conteudoTexto = ?, comprimentoConteudoTexto = ?, alturaConteudoTexto = ?, leftConteudoTexto = ?, topConteudoTexto = ?, conteudoImagem = ?, ordem = ? WHERE id = ?";
    private static final String SELECT_SECAO_BY_ID_SQL = "SELECT * FROM secoes WHERE id = ?";
    private static final String CONTAR_SECOES_BY_TIPO_E_USUARIO = "SELECT COUNT(*) FROM secoes WHERE tipo = ? AND usuario_id = ?";
    
    public static void salvarSecao(Connection conn, Secao secao) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SECAO_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, secao.getUsuarioId());
            stmt.setString(2, secao.getTitulo());
            stmt.setString(3, secao.getTipo());
            stmt.setString(4, secao.getConteudoTexto());
            stmt.setInt(5, secao.getComprimentoConteudoTexto() != null ? secao.getComprimentoConteudoTexto() : 0);
            stmt.setInt(6, secao.getAlturaConteudoTexto() != null ? secao.getAlturaConteudoTexto() : 0);
            stmt.setInt(7, secao.getLeftConteudoTexto() != null ? secao.getLeftConteudoTexto() : 0);
            stmt.setInt(8, secao.getTopConteudoTexto() != null ? secao.getTopConteudoTexto() : 0);
            stmt.setBytes(9, secao.getConteudoImagem());
            stmt.setInt(10, secao.getOrdem());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    secao.setId(rs.getLong(1));
                }
            }
        }
    }

    public static List<Secao> listarSecoesPorUsuarioId(Connection conn, Long usuarioId) throws SQLException {
        List<Secao> secoes = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(SELECT_SECOES_BY_USUARIO_SQL)) {

            stmt.setLong(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Secao secao = new Secao();

                    secao.setId(rs.getLong("id"));
                    secao.setUsuarioId(rs.getLong("usuario_id"));
                    secao.setTitulo(rs.getString("titulo"));
                    secao.setTipo(rs.getString("tipo"));
                    secao.setConteudoTexto(rs.getString("conteudoTexto"));
                    secao.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
                    secao.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
                    secao.setLeftConteudoTexto(rs.getInt("leftConteudoTexto"));
                    secao.setTopConteudoTexto(rs.getInt("topConteudoTexto"));
                    secao.setConteudoImagem(rs.getBytes("conteudoImagem"));
                    secao.setOrdem(rs.getInt("ordem"));

                    secoes.add(secao);
                }
            }
        }
       
        return secoes;
    }

    public static void excluirSecao(Connection conn, Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SECAO_SQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public static void atualizarSecao(Connection conn, Secao secao) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SECAO_SQL)) {
            stmt.setString(1, secao.getTitulo());
            stmt.setString(2, secao.getTipo());
            stmt.setString(3, secao.getConteudoTexto());
            stmt.setInt(4, secao.getComprimentoConteudoTexto() != null ? secao.getComprimentoConteudoTexto() : 0);
            stmt.setInt(5, secao.getAlturaConteudoTexto() != null ? secao.getAlturaConteudoTexto() : 0);
            stmt.setInt(6, secao.getLeftConteudoTexto() != null ? secao.getLeftConteudoTexto() : 0);
            stmt.setInt(7, secao.getTopConteudoTexto() != null ? secao.getTopConteudoTexto() : 0);
            stmt.setBytes(8, secao.getConteudoImagem());
            stmt.setInt(9, secao.getOrdem());
            stmt.setLong(10, secao.getId());
            stmt.executeUpdate();
        }
    }

    public static Secao getSecaoPorId(Connection conn, Long secaoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_SECAO_BY_ID_SQL)) {
            stmt.setLong(1, secaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Secao secao = new Secao();
                    secao.setId(rs.getLong("id"));
                    secao.setUsuarioId(rs.getLong("usuario_id"));
                    secao.setTitulo(rs.getString("titulo"));
                    secao.setTipo(rs.getString("tipo"));
                    secao.setConteudoTexto(rs.getString("conteudoTexto"));
                    secao.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
                    secao.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
                    secao.setLeftConteudoTexto(rs.getInt("leftConteudoTexto"));
                    secao.setTopConteudoTexto(rs.getInt("topConteudoTexto"));
                    secao.setConteudoImagem(rs.getBytes("conteudoImagem"));
                    secao.setOrdem(rs.getInt("ordem"));
                    return secao;
                }
            }
        }
        return null;
    }
    
    public static long contarSecoesPorTipoEUsuario(Connection conn, String tipo, Long usuarioId) throws SQLException {
        
        try (PreparedStatement stmt = conn.prepareStatement(CONTAR_SECOES_BY_TIPO_E_USUARIO)) {
            stmt.setString(1, tipo);
            stmt.setLong(2, usuarioId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }
}