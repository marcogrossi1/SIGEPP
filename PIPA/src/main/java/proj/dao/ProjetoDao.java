package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.sql.results.spi.ResultsConsumer;

import proj.model.Projeto;
import proj.model.Projeto.TipoProjeto;
import proj.model.Curso;

public class ProjetoDao {
    private final static String getsql = "SELECT * FROM Projeto  WHERE id = ?";
    private final static String listsql = "SELECT * FROM Projeto";
    private final static String listByNomeSql = "SELECT * FROM Projeto WHERE nome like %?% ";
    private final static String insertsql = "INSERT INTO Projeto (nome, responsavel, descricao, carga_horaria, vagas, requisito) VALUES( ?, ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE Projeto SET nome = ?, responsavel = ?, descricao = ?, carga_horaria = ?, vagas_remuneradas = ?, vagas_voluntarias = ?, requisito = ?, campus = ?, tipo_projeto = ?, curso = ? WHERE id = ? ";
    private final static String deletesql = "DELETE FROM Projeto WHERE id = ?";
    private final static String getByNomeSql = "SELECT * FROM Projeto WHERE nome = ?";
    private final static String getCursosByProjetoIDsql = "SELECT curso_id FROM Projeto_has_Curso WHERE projeto_id = ?";

    static Projeto set(ResultSet rs)
            throws SQLException {
        Projeto vo = new Projeto();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setResponsavel(rs.getString("responsavel"));
        vo.setDescricao(rs.getString("descricao"));
        vo.setCargaHoraria(rs.getInt("carga_horaria"));
        vo.setVagasRemuneradas(rs.getInt("vagas_remuneradas"));
        vo.setValorBolsa(rs.getString("valor_bolsa"));
        vo.setVagasVoluntarias(rs.getInt("vagas_voluntarias"));
        vo.setRequisito(rs.getString("requisito"));
        vo.setCampus(rs.getString("campus"));
        vo.setTipoProjeto(TipoProjeto.valueOf(rs.getString("tipo_projeto")));
        return vo;
    }

    public static Projeto get(Connection conn, long id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("Projeto não encontrado com ID: " + id); // Log de não encontrado
                throw new NotFoundException("Object not found [" + id + "]");
            }
            Projeto b = set(rs);
            System.out.println("Projeto encontrado: " + b.getNome()); // Log do projeto encontrado
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
        }
    }

    public static Projeto getByNome(Connection conn, String nome)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found [" + nome + "]");
            }
            Projeto b = set(rs);
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static ArrayList<Projeto> list(Connection conn)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Projeto>();
            }
            ArrayList<Projeto> list = new ArrayList<Projeto>();
            do {
                Projeto b = set(rs);
                list.add(b);
            } while (rs.next());
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static ArrayList<Projeto> listByNome(Connection conn, String nome)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Projeto>();
            }
            ArrayList<Projeto> list = new ArrayList<Projeto>();
            do {
                Projeto b = set(rs);
                list.add(b);
            } while (rs.next());
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static void insert(Connection conn, Projeto vo, long professorId)
            throws SQLException {
        boolean autoCommitOriginal = conn.getAutoCommit();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);

            // Prepara a declaração SQL
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagasRemuneradas());
            ps.setInt(6, vo.getVagasVoluntarias());
            ps.setString(7, vo.getRequisito());
            ps.setString(8, vo.getCampus());
            ps.setString(9, vo.getTipoProjeto().getnomeTipo());
            ps.setLong(11, vo.getId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                vo.setId(id);
            } else {
                throw new SQLException(
                        "Não foi possível recuperar a CHAVE gerada na criação do registro no banco de dados");
            }

            ProjetoDao.inserirProfessorProjeto(conn, professorId, vo.getId());

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommitOriginal);

            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static void update(Connection conn, Projeto vo) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        boolean autoCommitOriginal = conn.getAutoCommit(); // Salva o estado original do auto-commit

        try {
            // Desativa o auto-commit para gerenciar transações manualmente
            conn.setAutoCommit(false);

            // Prepara a declaração SQL
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagasRemuneradas());
            ps.setInt(6, vo.getVagasVoluntarias());
            ps.setString(7, vo.getRequisito());
            ps.setString(8, vo.getCampus());
            ps.setString(9, vo.getTipoProjeto().getnomeTipo());
            ps.setLong(11, vo.getId());

            // Executa a atualização
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + vo.getId() + "].");
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("Erro ao fazer rollback: " + e1.getMessage());
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(autoCommitOriginal);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            closeResource(ps);
        }
    }

    public static void delete(Connection conn, int id) throws NotFoundException, SQLException {
        boolean autoCommitOriginal = conn.getAutoCommit(); // Salva o estado original do auto-commit

        try {
            // Desativa o auto-commit para gerenciar transações manualmente
            conn.setAutoCommit(false);

            // Remove as relações na tabela professor_has_projeto
            removerPorProjeto(conn, id);

            // Deleta o projeto
            try (PreparedStatement ps = conn.prepareStatement(deletesql)) {
                ps.setInt(1, id);
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotFoundException("Projeto não encontrado com o ID: " + id);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            rollbackConnection(conn);
            throw e;
        } finally {
            try {
                conn.setAutoCommit(autoCommitOriginal);
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    protected static void rollbackConnection(Connection conn) {
        try {
            if (conn != null)
                conn.rollback();
        } catch (Exception e) {
            conn = null;
        }
    }

    protected static void closeResource(Statement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            rs = null;
        }

        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }

    protected static void closeResource(Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }

    public static ArrayList<Projeto> listPorIdProfessor(Connection conn, long professor_id) throws SQLException {
        ArrayList<Projeto> list = new ArrayList<>();
        try {
            List<Long> projetosIds = acharProjetosPorProfessorId(conn, professor_id);
            for (Long projetoId : projetosIds) {
                Projeto projeto = get(conn, projetoId);
                if (projeto != null) {
                    list.add(projeto);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }

    @PersistenceContext
    private EntityManager entityManager;

    private static final String INSERT_SQL = "INSERT INTO professor_has_projeto (professor_id, projeto_id) VALUES (?, ?)";

    /**
     * Insere uma relação entre um professor e um projeto na tabela
     * professor_has_projeto.
     *
     * @param conn        A conexão com o banco de dados.
     * @param professorId O ID do professor.
     * @param projetoId   O ID do projeto recém-criado.
     * @throws SQLException Se ocorrer um erro durante a inserção.
     */
    public static void inserirProfessorProjeto(Connection conn, long professorId, long projetoId) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(INSERT_SQL);
            ps.setLong(1, professorId);
            ps.setLong(2, projetoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
        }
    }

    /**
     * Remove as relações relacionadas a um projeto da tabela professor_has_projeto.
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @throws SQLException Se ocorrer um erro durante a remoção.
     */
    public static void removerPorProjeto(Connection conn, int projetoId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM professor_has_projeto WHERE projeto_id = ?")) {
            ps.setInt(1, projetoId);
            ps.executeUpdate();
        }
    }

    /**
     * Retorna uma lista de IDs de projetos relacionados a um professor.
     *
     * @param conn        A conexão com o banco de dados.
     * @param professorId O ID do professor.
     * @return Uma lista de IDs de projetos relacionados ao professor.
     * @throws SQLException Se ocorrer um erro durante a consulta.
     */
    public static List<Long> acharProjetosPorProfessorId(Connection conn, long professorId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Long> projetosIds = new ArrayList<>();

        try {
            String query = "SELECT projeto_id FROM professor_has_projeto WHERE professor_id = ?";
            ps = conn.prepareStatement(query);
            ps.setLong(1, professorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                long projetoId = rs.getLong("projeto_id");
                projetosIds.add(projetoId);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw e;
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
        }

        return projetosIds;
    }

    /**
     * Retorna uma lista de cursos relacioandos a um projeto.
     *
     * @param conn        A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @return Uma lista de cursos relacionados ao projeto.
     * @throws SQLException Se ocorrer um erro durante a consulta.
     */
    public ArrayList<Curso> getCursos(Connection conn, int projetoId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Cria uma lista com os ids dos cursos.
            ps = conn.prepareStatement(getCursosByProjetoIDsql);
            ps.setInt(1, projetoId);
            rs = ps.executeQuery();
            ArrayList<Integer> ids = new ArrayList<>();
            while (rs.next()) {
                ids.add(rs.getInt("curso_id"));
            }

            // A partir da lista de IDs, monta a lista de cursos.
            ArrayList<Curso> cursos = new ArrayList<>();
            for (int id : ids) {
                Curso c = CursoDao.get(conn, id);
                cursos.add(c);
            }
            return cursos;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
        }
    }
}