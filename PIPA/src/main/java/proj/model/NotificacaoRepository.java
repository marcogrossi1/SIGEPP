package proj.model;
import proj.model.Notificacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {	
	@Query("SELECT n FROM Notificacao n WHERE n.idUsuario = :idUsuario ORDER BY n.dataCriacao DESC")
    List<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(Long idUsuario);
}