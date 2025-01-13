import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlteracaoRepository extends JpaRepository<Alteracao, Long> {
    // Consultar alterações por ID do usuário
    List<Alteracao> findByUsuarioId(Long usuarioId);
}
