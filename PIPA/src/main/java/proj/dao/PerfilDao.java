import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // Consultar perfil por ID do usuário
    Perfil findByUsuarioId(Long usuarioId);
}
