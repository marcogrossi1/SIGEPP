package proj.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import proj.service.NotificacaoService;
import java.security.Principal;
import java.sql.Connection;

import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Usuario;

@ControllerAdvice
public class NotificacaoControllerAdvice {
	
	@Autowired
    private NotificacaoService notificacaoService;
	
	@Autowired
    private HDataSource ds;
	
    public NotificacaoControllerAdvice(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }
    @ModelAttribute("temNotificacoesNaoLidas")
    public boolean verificarNotificacoes(HttpServletRequest request, Principal principal) {
        if (principal == null) {
            return false;
        }

        try (Connection conn = ds.getConnection()) {
            Usuario u = UsuarioDao.getByNome(conn, principal.getName());
            return notificacaoService.temNotificacaoNaoLida(u.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
}
