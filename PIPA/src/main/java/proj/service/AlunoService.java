package proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import proj.dao.AlunoDao;
import proj.model.Aluno;
import proj.dao.NotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class AlunoService {

    @Autowired
    private DataSource dataSource;

    public Aluno autenticarAluno(String email, String senha) throws NotFoundException, SQLException {
        Aluno aluno = getAlunoByEmail(email);
        
        if (aluno != null && aluno.getSenha().equals(senha)) {
            return aluno;
        } else {
            throw new NotFoundException("Email ou senha inválidos");
        }
    }

    public Aluno getAlunoByEmail(String email) throws NotFoundException, SQLException {
        try (Connection conn = DataSourceUtils.getConnection(dataSource)) {
            return AlunoDao.getByEmail(conn, email);  
        }
    }
    
    
}


