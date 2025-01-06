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

    public void salvar(Candidatura candidatura) throws SQLException {
        String sql = "INSERT INTO candidatura (candidato_id, oportunidade_id, mensagem, data_aplicacao) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, candidatura.getCandidato().getId());
            stmt.setLong(2, candidatura.getIDoportunidade());
            stmt.setString(3, candidatura.getMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(candidatura.getDataAplicacao()));
            stmt.executeUpdate();
        }
    }

    public List<Candidatura> listarPorProjeto(Long oportunidadeId) throws SQLException {
        String sql = "SELECT * FROM candidatura WHERE oportunidade_id = ?";
        List<Candidatura> candidaturas = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, oportunidadeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Candidatura candidatura = new Candidatura();
                    candidatura.setId(rs.getLong("id"));

                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getLong("candidato_id"));
                    candidatura.setCandidato(aluno);

                    candidatura.setIDoportunidade(rs.getLong("oportunidade_id"));
                    candidatura.setMensagem(rs.getString("mensagem"));
                    candidatura.setDataAplicacao(rs.getTimestamp("data_aplicacao").toLocalDateTime());

                    candidaturas.add(candidatura);
                }
            }
        }
        return candidaturas;
    }
}

