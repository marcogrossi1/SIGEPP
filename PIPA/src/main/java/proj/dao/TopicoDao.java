package proj.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import proj.model.Topico;

public class TopicoDao {

    private static final String INSERT_TOPICO_SQL = "INSERT INTO topicos (secao_id, conteudoTexto, conteudoImagem, conteudoArquivo, comprimentoConteudoTexto, alturaConteudoTexto, estado, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_TOPICOS_BY_SECAO_SQL = "SELECT * FROM topicos WHERE secao_id = ?";
    private static final String DELETE_TOPICO_SQL = "DELETE FROM topicos WHERE id = ?";
    private static final String UPDATE_TOPICO_SQL = "UPDATE topicos SET secao_id = ?, conteudoTexto = ?, conteudoImagem = ?, conteudoArquivo = ?, comprimentoConteudoTexto = ?, alturaConteudoTexto = ?, estado = ?, data = ? WHERE id = ?";
    private static final String SELECT_TOPICO_BY_ID_SQL = "SELECT * FROM topicos WHERE id = ?";
    private static final String SELECT_TOPICOS_SQL = "SELECT * FROM topicos";
    private static final String CONTAR_TOPICOS_BY_SECAO_SQL = "SELECT COUNT(*) FROM topicos WHERE secao_id = ?";
    private static final String CONTAR_TOPICOS_SQL = "SELECT COUNT(*) FROM topicos"; 
    private static final String UPDATE_VALIDACAO_SQL = "UPDATE topicos SET estado = ?, data = ? WHERE id = ?";

    public static void salvarTopico(Connection conn, Topico topico) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_TOPICO_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, topico.getSecaoId());  
            stmt.setString(2, topico.getConteudoTexto()); 
            stmt.setBytes(3, topico.getConteudoImagem()); 
            stmt.setBytes(4, topico.getConteudoArquivo()); 
            stmt.setObject(5, topico.getComprimentoConteudoTexto());
            stmt.setObject(6, topico.getAlturaConteudoTexto());
            stmt.setBoolean(7, topico.getEstado());
            stmt.setTimestamp(8, topico.getData() != null ? Timestamp.valueOf(topico.getData()) : null);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    topico.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public static List<Topico> listarTopicosPorSecaoId(Connection conn, Long secaoId) throws SQLException {
        List<Topico> topicos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICOS_BY_SECAO_SQL)) {
            stmt.setLong(1, secaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    topicos.add(extrairTopico(rs));
                }
            }
        }
        return topicos;
    }

    public static List<Topico> listarTodosTopicos(Connection conn) throws SQLException {
        List<Topico> topicos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICOS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                topicos.add(extrairTopico(rs));
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
            stmt.setTimestamp(8, topico.getData() != null ? Timestamp.valueOf(topico.getData()) : null);
            stmt.setLong(9, topico.getId());
            stmt.executeUpdate();
        }
    }

    public static Topico getTopicoPorId(Connection conn, Long topicoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TOPICO_BY_ID_SQL)) {
            stmt.setLong(1, topicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairTopico(rs);
                }
            }
        }
        return null;
    }

    public static long contarTopicosPorSecao(Connection conn, Long secaoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CONTAR_TOPICOS_BY_SECAO_SQL)) {
            stmt.setLong(1, secaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }

    public static long contarTodosTopicos(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CONTAR_TOPICOS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }

    private static Topico extrairTopico(ResultSet rs) throws SQLException {
        Topico topico = new Topico();
        topico.setId(rs.getLong("id"));
        topico.setSecaoId(rs.getLong("secao_id"));
        topico.setConteudoTexto(rs.getString("conteudoTexto"));
        topico.setConteudoImagem(rs.getBytes("conteudoImagem"));
        topico.setConteudoArquivo(rs.getBytes("conteudoArquivo"));
        topico.setComprimentoConteudoTexto(rs.getInt("comprimentoConteudoTexto"));
        topico.setAlturaConteudoTexto(rs.getInt("alturaConteudoTexto"));
        topico.setEstado(rs.getBoolean("estado"));

        Timestamp timestamp = rs.getTimestamp("data");
        if (timestamp != null) {
            topico.setData(timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } else {
            topico.setData(null);
        }

        return topico;
    }
    
    public static void validarTopico(Connection conn, Long idTopico, Boolean estado) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_VALIDACAO_SQL)) {
            stmt.setBoolean(1, estado);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(3, idTopico);
            stmt.executeUpdate();
        }
    }
}