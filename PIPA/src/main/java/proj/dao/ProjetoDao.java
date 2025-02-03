package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import proj.model.Curso;
import proj.model.Novidade;
import proj.model.Projeto;

public class ProjetoDao {
	
    private final static String getsql = "SELECT * FROM Projeto  WHERE id = ?";
    private final static String listsql = "SELECT * FROM Projeto";
    private final static String listByNomeSql = "SELECT * FROM Projeto WHERE nome like %?% ";
    private final static String insertsql = "INSERT INTO Projeto (nome, responsavel, descricao, carga_horaria, vagas_remuneradas, valor_bolsa, vagas_voluntarias, requisito, campus, tipo_projeto) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String updatesql = "UPDATE Projeto SET nome = ?, responsavel = ?, descricao = ?, carga_horaria = ?, vagas_remuneradas = ?, valor_bolsa = ?, vagas_voluntarias = ?, requisito = ?, campus = ?, tipo_projeto = ? WHERE id = ?";
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
        vo.setTipoProjeto(rs.getString("tipo_projeto"));
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
                System.out.println("Projeto não encontrado com ID: " + id);
                throw new NotFoundException("Object not found [" + id + "]");
            }
            Projeto b = set(rs);
            System.out.println("Projeto encontrado: " + b.getNome());
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
            ps.setString(6, vo.getValorBolsa());
            ps.setInt(7, vo.getVagasVoluntarias());
            ps.setString(8, vo.getRequisito());
            ps.setString(9, vo.getCampus());
            ps.setString(10, vo.getTipoProjeto());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                vo.setId(id);
            } else {
                throw new SQLException(
                        "Não foi possível recuperar a CHAVE gerada na criação do registro no banco de dados");
            }

            inserirCursosProjeto(conn, vo);
            inserirProfessorProjeto(conn, professorId, vo.getId());

            Novidade n = new Novidade(vo.getNome(), vo.getDescricao(), false, vo.getId());
            NovidadeDao.adicionarNovidade(conn, n);

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
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(updatesql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagasRemuneradas());
            ps.setString(6, vo.getValorBolsa());
            ps.setInt(7, vo.getVagasVoluntarias());
            ps.setString(8, vo.getRequisito());
            ps.setString(9, vo.getCampus());
            ps.setString(10, vo.getTipoProjeto());
            ps.setLong(11, vo.getId());
            ps.executeUpdate();

            removerCursosdoProjeto(conn, vo.getId());
            inserirCursosProjeto(conn, vo);

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
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(deletesql)) {
                // Removendo as relações do projeto.
                removerProfessoresdoProjeto(conn, id);
                removerCandidaturasdoProjeto(conn, id);
                removerCursosdoProjeto(conn, id);
                removerAlunodoProjeto(conn, id);
		
                Projeto p = ProjetoDao.get(conn, id);

                NovidadeDao.removerNovidade(conn, p.getNome());

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
     * Remove as relações relacionadas a um projeto da tabela Professor_has_Projeto
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @throws SQLException Se ocorrer um erro durante a remoção.
     */
    public static void removerProfessoresdoProjeto(Connection conn, int projetoId) throws SQLException {
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

            closeResource(ps, rs);
        }

        return projetosIds;
    }

    /**
     * Retorna um conjunto de cursos relacioandos a um projeto.
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @return Um conjunto de cursos relacionados ao projeto.
     * @throws SQLException Se ocorrer um erro durante a consulta.
     */
    public static HashSet<Curso> getCursos(Connection conn, Long projetoId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Cria uma lista com os ids dos cursos.
            ps = conn.prepareStatement(getCursosByProjetoIDsql);
            ps.setLong(1, projetoId);
            rs = ps.executeQuery();
            HashSet<Integer> ids = new HashSet<>();
            while (rs.next()) {
                ids.add(rs.getInt("curso_id"));
            }

            // A partir da lista de IDs, monta a lista de cursos.
            HashSet<Curso> cursos = new HashSet<>();
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

    /**
     * Insere um relação entre um curso e um projeto na tabela Projeto_has_Curso
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @return Um conjunto de cursos relacionados ao projeto.
     * @throws SQLException Se ocorrer um erro durante a consulta.
     */
    public static void inserirCursosProjeto(Connection conn, Projeto vo) throws SQLException {
        try {
            long projetoId = vo.getId();
            for (Curso curso : vo.getCursos()) {
                PreparedStatement ps = conn
                        .prepareStatement("INSERT INTO Projeto_has_Curso (projeto_id, curso_id) VALUES (?, ?)");
                ps.setLong(1, projetoId);
                ps.setInt(2, curso.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Remove as relações relacionadas a um projeto da tabela Projeto_has_curso
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @throws SQLException Se ocorrer um erro durante a remoção.
     */
    public static void removerCursosdoProjeto(Connection conn, long projetoId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Projeto_has_Curso WHERE projeto_id = ?")) {
            ps.setLong(1, projetoId);
            ps.executeUpdate();
        }
    }

    /**
     * Remove as candidaturas relacionadas a um projeto da tabela Candidaturas
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @throws SQLException Se ocorrer um erro durante a remoção.
     */
    public static void removerCandidaturasdoProjeto(Connection conn, int projetoId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM candidatura WHERE oportunidade_id = ?")) {
            ps.setInt(1, projetoId);
            ps.executeUpdate();
        }
    }

    /**
     * Remove os alunos relacionados a um projeto da tabela Aluno_has_Projeto
     *
     * @param conn      A conexão com o banco de dados.
     * @param projetoId O ID do projeto.
     * @throws SQLException Se ocorrer um erro durante a remoção.
     */
    public static void removerAlunodoProjeto(Connection conn, int projetoId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Aluno_has_Projeto WHERE projeto_id = ?")) {
            ps.setInt(1, projetoId);
            ps.executeUpdate();
        }
    }
}