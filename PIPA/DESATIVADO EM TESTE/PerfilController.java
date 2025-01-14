package proj.controller;
/*EM TESTES / DESATIVADO


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import proj.dao.HDataSource;
import proj.dao.SecaoDAO;
import proj.dao.TopicoDAO;
import proj.dao.UsuarioDao;
import proj.model.Secao;
import proj.model.Usuario;

@WebServlet("/perfil")
public class PerfilController extends HttpServlet {
	
	@Autowired
    private HDataSource ds;
	
    private UsuarioDao usuarioDAO;
    private SecaoDAO secaoDAO;
    private TopicoDAO topicoDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ds.getConnection();
            usuarioDAO = new UsuarioDao(connection);
            secaoDAO = new SecaoDAO(connection);
            topicoDAO = new TopicoDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erro ao inicializar DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        Usuario usuario = null;
        List<Secao> secoes = null;

        try {
            usuario = usuarioDAO.buscarPorId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar usuário.");
            return;
        }

        try {
            secoes = secaoDAO.buscarPorUsuarioId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar seções.");
            return;
        }

        request.setAttribute("usuario", usuario);
        request.setAttribute("secoes", secoes);
        request.getRequestDispatcher("/perfil").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Salvar alterações no banco
    }
}*/
