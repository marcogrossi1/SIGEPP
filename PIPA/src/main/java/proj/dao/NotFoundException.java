package proj.dao;

import java.sql.SQLException;

public class NotFoundException extends SQLException 
{
    private static final long serialVersionUID = 1L;

    public NotFoundException()
    {
        super();
    }

    public NotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotFoundException(String message)
    {
        super(message);
    }

    public NotFoundException(Throwable cause)
    {
        super(cause);
    }
}
