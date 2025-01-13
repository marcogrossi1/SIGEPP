import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Consultar tópicos por ID da seção
    List<Topico> findBySecaoId(Long secaoId);
}