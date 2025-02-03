package proj.controller;

import proj.model.Notificacao;
import proj.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;


    @GetMapping("/notificacoes")
    public String exibirNotificacoes(Model model, Principal principal) {
        Long idUsuario = Long.parseLong(principal.getName());
      List<Notificacao> notificacoes = notificacaoService.buscarNotificacoesPorUsuario(idUsuario);

        model.addAttribute("notificacoes", notificacoes);
        return "notificacoes";
    }
    
    @PostMapping("/notificacoes/mark-as-read/{idNotificacao}")
    public void marcarNotificacaoComoLida(@PathVariable Long idNotificacao) {
        notificacaoService.marcarNotificacaoComoLida(idNotificacao);
    }
    
    @PostMapping("/notificacoes/delete/{idNotificacao}")
    public void deletarNotificacao(@PathVariable Long idNotificacao) {
        notificacaoService.deletarNotificacao(idNotificacao);
    }
}