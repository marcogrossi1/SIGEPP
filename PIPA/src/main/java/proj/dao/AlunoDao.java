package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Progresso;
import proj.model.Projeto;
import proj.model.Usuario;

public class AlunoDao {

    private final static String listAlunoProjetosSql = "select p.* from aluno_has_projeto ap, projeto p  where ap.projeto_id = p.id and ap.aluno_id = ? ";
    private final static String listAlunoEstagiosSql = "select p.* from aluno_has_estagio ap, estagio p  where ap.estagio_id = p.id and ap.aluno_id = ? ";

    private final static String getsql = "SELECT * FROM aluno  WHERE id = ?";
    private final static String getByCpfSql = "SELECT * FROM aluno  WHERE cpf = ?";
    private final static String getByEmailSql = "SELECT * FROM aluno  WHERE email = ?";
    private final static String getByUsuario_idSql = "SELECT * FROM aluno  WHERE usuario_id = ?";
    private final static String listsql = "SELECT * FROM aluno";
    private final static String listByNomeSql = "SELECT * FROM aluno WHERE nome like ? ";
    private final static String listByCursoSql = "SELECT * FROM aluno WHERE curso = ? ";
    private final static String listByCampusSql = "SELECT * FROM aluno WHERE campus = ? ";
    private final static String listByPeriodoSql = "SELECT * FROM aluno WHERE periodo = ? ";
    private final static String listCursosSql = "SELECT DISTINCT curso FROM aluno ORDER BY curso";
    private final static String listCampusSql = "SELECT DISTINCT campus FROM aluno ORDER BY campus";
    private final static String listPeriodosSql = "SELECT DISTINCT periodo FROM aluno ORDER BY periodo";
    private final static String insertsql = "INSERT INTO aluno (cpf, nome, curso, campus, email, periodo, usuario_id, telefone, fotoPerfil, bannerPerfil, descricaoPerfil) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE aluno SET cpf = ?, nome = ?, curso = ?, campus = ?, email = ?, periodo = ?, usuario_id = ?, telefone = ?, fotoPerfil = ?, bannerPerfil = ?, descricaoPerfil = ? WHERE id = ? ";
    private final static String updateForCpfSql = "UPDATE aluno SET cpf = ?  WHERE id = ? ";
    private final static String updateForNomeSql = "UPDATE aluno SET nome = ?  WHERE id = ? ";
    private final static String updateForCursoSql = "UPDATE aluno SET curso = ?  WHERE id = ? ";
    private final static String updateForCampusSql = "UPDATE aluno SET campus = ?  WHERE id = ? ";
    private final static String updateForEmailSql = "UPDATE aluno SET email = ?  WHERE id = ? ";
    private final static String updateForPeriodoSql = "UPDATE aluno SET periodo = ?  WHERE id = ? ";
    private final static String updateForTelefoneSql = "UPDATE aluno SET telefone = ?  WHERE id = ? ";
    private final static String updateForFotoPerfilSql = "UPDATE aluno SET fotoPerfil = ?  WHERE id = ? ";
    private final static String updateForBannerPerfilSql = "UPDATE aluno SET bannerPerfil = ?  WHERE id = ? ";
    private final static String updateForDescricaoPerfilSql = "UPDATE aluno SET descricaoPerfil = ?  WHERE id = ? ";
    private final static String updateForUsuario_idSql = "UPDATE aluno SET usuario_id = ?  WHERE id = ? ";
    private final static String getProgressoEstagioSql = "SELECT progresso FROM aluno_has_estagio WHERE aluno_id = ? AND estagio_id = ?";
    private final static String setProgressoEstagioSql = "INSERT INTO aluno_has_estagio (aluno_id, estagio_id, progresso) VALUES (?, ?, ?) AS upd_row ON DUPLICATE KEY UPDATE progresso = upd_row.progresso;";
    private final static String deleteHasEstagioSql = "DELETE FROM aluno_has_estagio WHERE aluno_id = ? AND estagio_id = ?";

    private static void closeResource(Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }

    private static void closeResource(Statement ps, ResultSet rs) {
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

    static Aluno set(ResultSet rs)
            throws SQLException {
        Aluno vo = new Aluno();
        vo.setId(rs.getLong("id"));
        vo.setCpf(rs.getString("cpf"));
        vo.setNome(rs.getString("nome"));
        vo.setCurso(rs.getString("curso"));
        vo.setCampus(rs.getString("campus"));
        vo.setEmail(rs.getString("email"));
        vo.setPeriodo(rs.getString("periodo"));
        vo.setUsuario_id(rs.getLong("usuario_id"));
        vo.setTelefone(rs.getString("telefone"));
        vo.setFotoPerfil(rs.getBytes("fotoPerfil"));
        vo.setBannerPerfil(rs.getBytes("bannerPerfil"));
        vo.setDescricaoPerfil(rs.getString("descricaoPerfil"));
        return vo;
    }


    public static ArrayList<Aluno> listByCursoCampusPeriodo(Connection conn, String curso, String campus,
            String periodo)
            throws SQLException {
        String sql = "SELECT * FROM aluno ";

        ArrayList<String> sqladd = new ArrayList<>();

        if (curso != null && curso.trim().isEmpty() == false)
            sqladd.add(" curso = '" + curso + "'");
        if (campus != null && campus.trim().isEmpty() == false)
            sqladd.add(" campus = '" + campus + "'");
        if (periodo != null && periodo.trim().isEmpty() == false)
            sqladd.add(" periodo = '" + periodo + "'");

        if (sqladd.isEmpty() == false) {

            for (int i = 0; i < sqladd.size(); i++) {
                if (i == 0) {
                    sql = sql + " WHERE " + sqladd.get(i);
                } else {
                    sql = sql + " and " + sqladd.get(i);
                }
            }
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static ArrayList<String> listCursos(Connection conn)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listCursosSql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<String>();
            }
            ArrayList<String> list = new ArrayList<String>();
            do {
                String b = rs.getString("curso");
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

    public static ArrayList<String> listCampus(Connection conn)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listCampusSql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<String>();
            }
            ArrayList<String> list = new ArrayList<String>();
            do {
                String b = rs.getString("campus");
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

    public static ArrayList<String> listPeriodos(Connection conn)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listPeriodosSql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<String>();
            }
            ArrayList<String> list = new ArrayList<String>();
            do {
                String b = rs.getString("periodo");
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

    public static Aluno get(Connection conn, long id)
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
            Aluno b = set(rs);
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static Aluno getByCpf(Connection conn, String cpf)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByCpfSql);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found By [" + cpf + "]");
            }
            Aluno b = set(rs);
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static Aluno getByEmail(Connection conn, String email)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByEmailSql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found By [" + email + "]");
            }
            Aluno b = set(rs);
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static Aluno getByUsuario_id(Connection conn, long usuario_id)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByUsuario_idSql);
            ps.setLong(1, usuario_id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Object not found By [" + usuario_id + "]");
            }
            Aluno b = set(rs);
            return b;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static ArrayList<Aluno> list(Connection conn)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static ArrayList<Aluno> listByNome(Connection conn, String nome)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByNomeSql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static ArrayList<Aluno> listByCurso(Connection conn, String curso)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByCursoSql);
            ps.setString(1, curso);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static ArrayList<Aluno> listByCampus(Connection conn, String campus)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByCampusSql);
            ps.setString(1, campus);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static ArrayList<Aluno> listByPeriodo(Connection conn, String periodo)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByPeriodoSql);
            ps.setString(1, periodo);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Aluno>();
            }
            ArrayList<Aluno> list = new ArrayList<Aluno>();
            do {
                Aluno b = set(rs);
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

    public static void insert(Connection conn, Aluno vo)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(insertsql);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getCurso());
            ps.setString(4, vo.getCampus());
            ps.setString(5, vo.getEmail());
            ps.setString(6, vo.getPeriodo());
            ps.setLong(7, vo.getUsuario_id());
            ps.setString(8, vo.getTelefone());
            ps.setBytes(9, vo.getFotoPerfil());
            ps.setBytes(10, vo.getBannerPerfil());
            ps.setString(11, vo.getDescricaoPerfil());
            ps.executeUpdate();
            conn.commit();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                vo.setId(id);
            } else {
                throw new SQLException(
                        "Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados");
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static void update(Connection conn, Aluno vo)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getCurso());
            ps.setString(4, vo.getCampus());
            ps.setString(5, vo.getEmail());
            ps.setString(6, vo.getPeriodo());
            ps.setLong(7, vo.getUsuario_id());
            ps.setString(8, vo.getTelefone());
            ps.setBytes(9, vo.getFotoPerfil());
            ps.setBytes(10, vo.getBannerPerfil());
            ps.setString(11, vo.getDescricaoPerfil());
            ps.setLong(12, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + vo.getId() + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForCpf(Connection conn, long id, String cpf)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForCpfSql);
            ps.setString(1, cpf);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForNome(Connection conn, long id, String nome)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForNomeSql);
            ps.setString(1, nome);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForCurso(Connection conn, long id, String curso)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForCursoSql);
            ps.setString(1, curso);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForCampus(Connection conn, long id, String campus)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForCampusSql);
            ps.setString(1, campus);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForEmail(Connection conn, long id, String email)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForEmailSql);
            ps.setString(1, email);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForTelefone(Connection conn, long id, String telefone)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForTelefoneSql);
            ps.setString(1, telefone);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForDescricaoPerfil(Connection conn, long id, String descricaoPerfil)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForDescricaoPerfilSql);
            ps.setString(1, descricaoPerfil);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForFotoPerfil(Connection conn, long id, byte[] fotoPerfil)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForFotoPerfilSql);
            ps.setBytes(1, fotoPerfil);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForBannerPerfil(Connection conn, long id, byte[] bannerPerfil)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForBannerPerfilSql);
            ps.setBytes(1, bannerPerfil);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForPeriodo(Connection conn, long id, String periodo)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForPeriodoSql);
            ps.setString(1, periodo);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void updateForUsuario_id(Connection conn, long id, long usuario_id)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForUsuario_idSql);
            ps.setLong(1, usuario_id);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotFoundException("Object not found [" + id + "] .");
            }
            // SEM COMMIT
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static void delete(Connection conn, long id)
            throws NotFoundException, SQLException {
        Aluno a = new Aluno();
        Usuario u = new Usuario();
        String sql1 = "delete from aluno_has_projeto where aluno_id = ? ";
        String sql2 = "delete from aluno_has_estagio where aluno_id = ? ";
        String sql3 = "delete from candidatura where candidato_id = ? ";
        String sql4 = "delete from aluno where id = ? ";
        String sql5 = "delete from seguidores where seguidor_id = ? ";
        String sql6 = "delete from seguidores where seguindo_id = ? ";
        String sql7 = "delete from usuario where id = ? ";

        a = AlunoDao.get(conn, id);
        u = UsuarioDao.get(conn, a.getUsuario_id());

        deleteRelation(conn, sql1, id);
        deleteRelation(conn, sql2, id);
        deleteRelation(conn, sql3, id);
        deleteRelation(conn, sql4, id);
        deleteRelation(conn, sql5, u.getId());
        deleteRelation(conn, sql6, u.getId());
        deleteRelation(conn, sql7, u.getId());
    }

    private static void deleteRelation(Connection conn, String sql, long id)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

    public static ArrayList<Projeto> listProjetosByAlunoId(Connection conn, long idAluno)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listAlunoProjetosSql);
            ps.setLong(1, idAluno);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Projeto>();
            }
            ArrayList<Projeto> list = new ArrayList<Projeto>();
            do {
                Projeto b = ProjetoDao.set(rs);
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

    public static ArrayList<Long> listProjetosIdsByAlunoId(Connection conn, long idAluno) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listAlunoProjetosSql);
            ps.setLong(1, idAluno);
            rs = ps.executeQuery();
            ArrayList<Long> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getLong("id"));
            }
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
        }
    }

    public static ArrayList<Estagio> listEstagiosByAlunoId(Connection conn, long idAluno)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listAlunoEstagiosSql);
            ps.setLong(1, idAluno);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<Estagio>();
            }
            ArrayList<Estagio> list = new ArrayList<Estagio>();
            do {
                Estagio b = EstagioDao.set(rs);
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

    public static void updateImagens(Connection conn, Aluno a) throws SQLException {
        String sql = "UPDATE aluno SET foto_perfil = ?, banner_perfil = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBytes(1, a.getFotoPerfil()); // Foto de perfil
            ps.setBytes(2, a.getBannerPerfil()); // Banner
            ps.setLong(3, a.getId()); // ID do aluno
            ps.executeUpdate(); // Executa o update
        }
    }

    public static Progresso getProgressoEstagio(Connection conn, long id_aluno, long id_estagio)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getProgressoEstagioSql);
            ps.setLong(1, id_aluno);
            ps.setLong(2, id_estagio);
            rs = ps.executeQuery();
            if (!rs.next())
                return null;
            Progresso pro = Progresso.valueOf(rs.getString("progresso").toUpperCase());
            return pro;
        } catch (SQLException e) {
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static void setProgresso(Connection conn, long aluno_id, long estagio_id, Progresso progresso)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(setProgressoEstagioSql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, aluno_id);
            ps.setLong(2, estagio_id);
            ps.setString(3, progresso.toString().toUpperCase());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps, rs);
            ps = null;
            rs = null;
        }
    }

    public static boolean desinscreverEstagio(Connection conn, long id_aluno, long id_estagio)
            throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deleteHasEstagioSql);
            ps.setLong(1, id_aluno);
            ps.setLong(2, id_estagio);
            int count = ps.executeUpdate();
            if (count == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
            }
            ;
            throw e;
        } finally {
            closeResource(ps);
            ps = null;
        }
    }

}