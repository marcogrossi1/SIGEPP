package proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proj.model.Perfil;

@Repository
public interface PerfilDao extends JpaRepository<Perfil, Long> {
    // Consultar perfil por ID do usuário
    Perfil findByUsuarioId(Long usuarioId);
}
