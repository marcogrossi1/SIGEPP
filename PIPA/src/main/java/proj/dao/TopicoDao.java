package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proj.model.Topico;

public class TopicoDao {

    private static final String INSERT_TOPICO_SQL = "INSERT INTO topicos (secao_id, conteudoTexto, conteudoImagem, conteudoArquivo, comprimentoConteudoTexto, alturaConteudoTexto, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_TOPICOS_BY_SECAO_SQL = "SELECT * FROM topicos WHERE secao_id = ?";
    private static final String DELETE_TOPICO_SQL = "DELETE FROM topicos WHERE id = ?";
    private static final String UPDATE_TOPICO_SQL = "UPDATE topicos SET secao_id = ?, conteudoTexto = ?, conteudoImagem = ?, conteudoArquivo = ?, comprimentoConteudoTexto = ?, alturaConteudoTexto = ?, estado = ? WHERE id = ?";
    private static final String SELECT_TOPICO_BY_ID_SQL = "SELECT * FROM topicos WHERE id = ?";
    private static final String SELECT_TOPICOS_SQL = "SELECT id, secao_id, conteudoTexto, conteudoImagem, conteudoArquivo, comprimentoConteudoTexto, alturaConteudoTexto, estado FROM topicos";
    private static final String CONTAR_TOPICOS_BY_SECAO_SQL = "SELECT COUNT(*) FROM topicos WHERE secao_id = ?";
    private static final String CONTAR_TOPICOS_SQL = "SELECT COUNT(*) FROM topicos"; 

    public static void salvarTopico(Connection conn, Topico topico) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_TOPICO_SQL)) {
            stmt.setLong(1, topico.getSecaoId());  
            stmt.setString(2, topico.getConteudoTexto()); 
            stmt.setBytes(3, topico.getConteudoImagem()); 
            stmt.setBytes(4, topico.getConteudoArquivo()); 
            stmt.setObject(5, topico.getComprimentoConteudoTexto());
            stmt.setObject(6, topico.getAlturaConteudoTexto());
            stmt.setBoolean(7, topico.getEstado());
            stmt.executeUpdate();  
        }
    }

    public static List<Topico> listarTopicosPorSecaoId(Connection conn, Long secaoId) throws SQLException {
        List<Topico> topicos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICOS_BY_SECAO_SQL)) {
            stmt.setLong(1, secaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Topico topico = new Topico();
                    topico.setId(rs.getLong("id"));
                    topico.setSecaoId(rs.getLong("secao_id"));
                    topico.setConteudoTexto(rs.getString("conteudoTexto"));
                    topico.setConteudoImagem(rs.getBytes("conteudoImagem"));
                    topico.setConteudoArquivo(rs.getBytes("conteudoArquivo"));
                    topico.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
                    topico.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
                    topico.setEstado(rs.getBoolean("estado"));
                    topicos.add(topico);
                }
            }
        }
        return topicos;
    }
    
    public static List<Topico> listarTodosTopicos(Connection conn) throws SQLException {
        List<Topico> topicos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICOS_SQL)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Topico topico = new Topico();
                    topico.setId(rs.getLong("id"));
                    topico.setSecaoId(rs.getLong("secao_id"));
                    topico.setConteudoTexto(rs.getString("conteudoTexto"));
                    topico.setConteudoImagem(rs.getBytes("conteudoImagem"));
                    topico.setConteudoArquivo(rs.getBytes("conteudoArquivo"));
                    topico.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
                    topico.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
                    topico.setEstado(rs.getBoolean("estado"));
                    topicos.add(topico);
                }
            }
        }
        return topicos;
    }

    public static void excluirTopico(Connection conn, Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_TOPICO_SQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public static void atualizarTopico(Connection conn, Topico topico) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_TOPICO_SQL)) {
            stmt.setLong(1, topico.getSecaoId()); 
            stmt.setString(2, topico.getConteudoTexto()); 
            stmt.setBytes(3, topico.getConteudoImagem()); 
            stmt.setBytes(4, topico.getConteudoArquivo()); 
            stmt.setObject(5, topico.getComprimentoConteudoTexto());
            stmt.setObject(6, topico.getAlturaConteudoTexto());
            stmt.setBoolean(7, topico.getEstado());
            stmt.setLong(8, topico.getId());
            stmt.executeUpdate();
        }
    }

    public static Topico getTopicoPorId(Connection conn, Long topicoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICO_BY_ID_SQL)) {
            stmt.setLong(1, topicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Topico topico = new Topico();
                    topico.setId(rs.getLong("id"));
                    topico.setSecaoId(rs.getLong("secao_id"));
                    topico.setConteudoTexto(rs.getString("conteudoTexto"));
                    topico.setConteudoImagem(rs.getBytes("conteudoImagem"));
                    topico.setConteudoArquivo(rs.getBytes("conteudoArquivo"));
                    topico.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
                    topico.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
                    topico.setEstado(rs.getBoolean("estado"));
                    return topico;
                }
            }
        }
        return null;
    }
    
    public static long contarTopicosPorSecao(Connection conn, Long secao_id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CONTAR_TOPICOS_BY_SECAO_SQL)) {
            stmt.setLong(1, secao_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }
    
    public static long contarTodosTopicos(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CONTAR_TOPICOS_SQL)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }
}