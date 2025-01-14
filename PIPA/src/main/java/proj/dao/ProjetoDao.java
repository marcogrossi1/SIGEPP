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

import proj.model.Projeto;

public class ProjetoDao {
    private final static String getsql = "SELECT * FROM Projeto  WHERE id = ?";
    private final static String listsql = "SELECT * FROM Projeto";
    private final static String listByNomeSql = "SELECT * FROM Projeto WHERE nome like %?% ";
    private final static String insertsql = "INSERT INTO Projeto (nome, responsavel, descricao, carga_horaria, vagas, requisito) VALUES( ?, ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE Projeto SET nome = ?, responsavel = ?, descricao = ?, carga_horaria = ?, vagas = ?, requisito = ? WHERE id = ? ";
    private final static String deletesql = "DELETE FROM Projeto WHERE id = ?";
    private final static String getByNomeSql = "SELECT * FROM Projeto WHERE nome = ?";

    static Projeto set(ResultSet rs)
            throws SQLException {
        Projeto vo = new Projeto();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setResponsavel(rs.getString("responsavel"));
        vo.setDescricao(rs.getString("descricao"));
        vo.setCargaHoraria(rs.getInt("carga_horaria"));
        vo.setVagas(rs.getInt("vagas"));
        vo.setRequisito(rs.getString("requisito"));
        return vo;
    }

    public static Projeto get(Connection conn, long id)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found [" + id + "]");
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

            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagas());
            ps.setString(6, vo.getRequisito());
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
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagas());
            ps.setString(6, vo.getRequisito());
            ps.setLong(7, vo.getId());

            // Executa a atualização
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + vo.getId() + "].");
            }

            // Confirma a transação
            conn.commit();
        } catch (SQLException e) {
            // Reverte a transação em caso de erro
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("Erro ao fazer rollback: " + e1.getMessage());
            }
            throw e; // Relança a exceção para ser tratada no nível superior
        } finally {
            // Restaura o estado original do auto-commit
            try {
                conn.setAutoCommit(autoCommitOriginal);
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
            closeResource(ps);
        }
    }

    public static void delete(Connection conn, int id) throws NotFoundException, SQLException {
        boolean autoCommitOriginal = conn.getAutoCommit(); // Salva o estado original do auto-commit

        try {
            // Desativa o auto-commit para gerenciar transações manualmente
            conn.setAutoCommit(false);

            // 1. Remove as relações na tabela professor_has_projeto
            removerPorProjeto(conn, id);

            // 2. Deleta o projeto
            try (PreparedStatement ps = conn.prepareStatement(deletesql)) {
                ps.setInt(1, id);
                int count = ps.executeUpdate();
                if (count == 0) {
                    throw new NotFoundException("Projeto não encontrado com o ID: " + id);
                }
            }

            // Confirma a transação
            conn.commit();
        } catch (SQLException e) {
            // Reverte a transação em caso de erro
            rollbackConnection(conn);
            throw e;
        } finally {
            // Restaura o estado original do auto-commit
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
}
