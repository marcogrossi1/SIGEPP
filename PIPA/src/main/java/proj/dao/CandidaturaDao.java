package proj.dao;

import proj.model.Aluno;
import proj.model.Candidatura;
import proj.model.StatusCandidatura;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados relacionadas às candidaturas.
 */
@Repository
public class CandidaturaDao {

    private final DataSource dataSource;

    public CandidaturaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Salva uma candidatura no banco de dados.
     *
     * @param candidatura Objeto da candidatura a ser salva
     * @throws SQLException Em caso de falhas no banco
     */
    public void salvar(Candidatura candidatura) throws SQLException {
        String sql = "INSERT INTO candidatura (candidato_id, oportunidade_id, mensagem, data_aplicacao, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, candidatura.getCandidato().getId());
            stmt.setLong(2, candidatura.getIDoportunidade());
            stmt.setString(3, candidatura.getMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(candidatura.getDataAplicacao()));

            // Salva o nome do enum no banco em vez da descrição
            stmt.setString(5, candidatura.getStatus().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar a candidatura: " + e.getMessage(), e);
        }
    }

    /**
     * Lista candidaturas associadas a um projeto específico.
     *
     * @param oportunidadeId ID da oportunidade/projeto
     * @return Lista de candidaturas associadas à oportunidade
     * @throws SQLException Em caso de falhas no banco
     */
    public List<Candidatura> listarPorProjeto(Long oportunidadeId) throws SQLException {
        String sql = "SELECT * FROM candidatura WHERE oportunidade_id = ?";
        List<Candidatura> candidaturas = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, oportunidadeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Candidatura candidatura = mapResultSetToCandidatura(rs, connection);
                    candidaturas.add(candidatura);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar candidaturas por projeto: " + e.getMessage(), e);
        }

        return candidaturas;
    }

    /**
     * Mapeia o ResultSet para um objeto Candidatura.
     *
     * @param rs ResultSet com os dados da candidatura
     * @return Objeto Candidatura
     * @throws SQLException Em caso de falhas ao acessar o ResultSet
     */
    private Candidatura mapResultSetToCandidatura(ResultSet rs, Connection conn) throws SQLException {
        Candidatura candidatura = new Candidatura();

        candidatura.setId(rs.getLong("id"));
        long candidatoId = rs.getLong("candidato_id");

        Aluno aluno = AlunoDao.get(conn, candidatoId);
        candidatura.setCandidato(aluno);
        candidatura.setIDoportunidade(rs.getLong("oportunidade_id"));
        candidatura.setMensagem(rs.getString("mensagem"));
        candidatura.setDataAplicacao(rs.getTimestamp("data_aplicacao").toLocalDateTime());

        // Lê o status pelo nome do enum
        String statusName = rs.getString("status");
        try {
            StatusCandidatura status = StatusCandidatura.valueOf(statusName);
            candidatura.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new SQLException("Status inválido encontrado no banco de dados: " + statusName, e);
        }

        return candidatura;
    }

    /**
     * Atualiza o status de uma candidatura.
     *
     * @param candidaturaId ID da candidatura a ser atualizada
     * @param status Novo status a ser atribuído à candidatura
     * @throws SQLException Em caso de falhas no banco
     */
    public void atualizarStatus(Long candidaturaId, StatusCandidatura status) throws SQLException {
        String sql = "UPDATE candidatura SET status = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status.name()); // Define o novo status usando o nome do enum
            stmt.setLong(2, candidaturaId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar status da candidatura: " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Recupera uma candidatura pelo seu ID.
     *
     * @param candidaturaId ID da candidatura a ser recuperada
     * @return Objeto Candidatura
     * @throws SQLException Em caso de falhas no banco
     */
    public Candidatura get(Long candidaturaId) throws SQLException {
        String sql = "SELECT * FROM candidatura WHERE id = ?";
        Candidatura candidatura = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, candidaturaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    candidatura = mapResultSetToCandidatura(rs, connection);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar candidatura: " + e.getMessage(), e);
        }

        return candidatura;
    }

}


