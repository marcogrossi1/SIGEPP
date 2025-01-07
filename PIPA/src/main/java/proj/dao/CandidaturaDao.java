package proj.dao;

import proj.model.Candidatura;
import proj.model.Aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidaturaDao {

    private final DataSource dataSource;

    @Autowired
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
        String sql = "INSERT INTO candidatura (candidato_id, oportunidade_id, mensagem, data_aplicacao) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, candidatura.getCandidato().getId());
            stmt.setLong(2, candidatura.getIDoportunidade());
            stmt.setString(3, candidatura.getMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(candidatura.getDataAplicacao()));

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
                    Candidatura candidatura = mapResultSetToCandidatura(rs);
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
    private Candidatura mapResultSetToCandidatura(ResultSet rs) throws SQLException {
        Candidatura candidatura = new Candidatura();

        candidatura.setId(rs.getLong("id"));

        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("candidato_id"));
        candidatura.setCandidato(aluno);

        candidatura.setIDoportunidade(rs.getLong("oportunidade_id"));
        candidatura.setMensagem(rs.getString("mensagem"));
        candidatura.setDataAplicacao(rs.getTimestamp("data_aplicacao").toLocalDateTime());

        return candidatura;
    }
}
