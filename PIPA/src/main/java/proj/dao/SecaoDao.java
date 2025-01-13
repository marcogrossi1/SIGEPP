import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long> {
    // Consultar seções por ID do usuário
    List<Secao> findByUsuarioId(Long usuarioId);
}
