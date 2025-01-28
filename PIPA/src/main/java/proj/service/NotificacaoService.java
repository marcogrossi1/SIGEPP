package proj.service;

import proj.model.Notificacao;
import proj.model.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public void salvarNotificacao(Long idUsuario, String notificacaoTexto) {
        Notificacao notificacao = new Notificacao();
        notificacao.setIdUsuario(idUsuario);
        notificacao.setNotificacao(notificacaoTexto);
        notificacao.setLida(false);
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacaoRepository.save(notificacao);
    }
    public List<Notificacao> buscarNotificacoesPorUsuario(Long idUsuario) {
        return notificacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(idUsuario);
    }
    public void marcarNotificacaoComoLida(Long idNotificacao) {
        Notificacao notificacao = notificacaoRepository.findById(idNotificacao)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setLida(true); 
        notificacaoRepository.save(notificacao);
    }
    
}