package proj.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class AbstractDaoBase
{
    protected static void rollbackConnection(Connection conn)
    {
        try
        {
            if (conn != null) conn.rollback();
        }
        catch (Exception e)
        {
            conn = null;
        }
    }
    
    protected static void closeResource(Statement ps, ResultSet rs)
    {
        try
        {
            if (rs != null) rs.close();
        }
        catch (Exception e)
        {
            rs = null;
        }

        try
        {
            if (ps != null) ps.close();
        }
        catch (Exception e)
        {
            ps = null;
        }
    }

    protected static void closeResource(Statement ps)
    {
        try
        {
            if (ps != null) ps.close();
        }
        catch (Exception e)
        {
            ps = null;
        }
    }

}