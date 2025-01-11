package proj.dao;

import proj.model.Candidatura;
import proj.model.Aluno;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidaturaDao {

    private final DataSource dataSource; // Fonte de dados para conexão com o banco

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

            stmt.setLong(1, candidatura.getCandidato().getId()); // ID do candidato (aluno)
            stmt.setLong(2, candidatura.getIDoportunidade());    // ID da oportunidade (projeto)
            stmt.setString(3, candidatura.getMensagem());        // Mensagem da candidatura
            stmt.setTimestamp(4, Timestamp.valueOf(candidatura.getDataAplicacao())); // Data da candidatura

            stmt.executeUpdate(); // Executa a inserção no banco
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

            stmt.setLong(1, oportunidadeId); // Define o ID do projeto na consulta

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Candidatura candidatura = mapResultSetToCandidatura(rs);
                    candidaturas.add(candidatura); // Adiciona a candidatura à lista
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar candidaturas por projeto: " + e.getMessage(), e);
        }

        return candidaturas; // Retorna a lista de candidaturas
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

        candidatura.setId(rs.getLong("id")); // Obtém o ID da candidatura

        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("candidato_id")); // Obtém o ID do aluno (candidato)
        candidatura.setCandidato(aluno); // Associa o aluno à candidatura

        candidatura.setIDoportunidade(rs.getLong("oportunidade_id")); // Obtém o ID da oportunidade (projeto)
        candidatura.setMensagem(rs.getString("mensagem")); // Obtém a mensagem da candidatura
        candidatura.setDataAplicacao(rs.getTimestamp("data_aplicacao").toLocalDateTime()); // Obtém a data da candidatura

        return candidatura; // Retorna o objeto Candidatura
    }
}
