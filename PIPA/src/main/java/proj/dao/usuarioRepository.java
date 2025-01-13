package proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.model.Usuario;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}
