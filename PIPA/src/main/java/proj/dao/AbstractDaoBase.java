package proj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoBase {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pipa?createDatabaseIfNotExist=true&characterEncoding=UTF-8"; // Altere para a URL do seu banco de dados
        String user = "root"; // Altere para seu usuário
        String password = "1234"; // Altere para sua senha
        return DriverManager.getConnection(url, user, password);
    }

    protected static void rollbackConnection(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (Exception e) {
            conn = null;
        }
    }

    protected static void closeResource(Statement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            rs = null;
        }

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }

    protected static void closeResource(Statement ps) {
        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }

}