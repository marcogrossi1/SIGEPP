package proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.model.Alteracao;

import java.util.List;

public interface AlteracaoDao extends JpaRepository<Alteracao, Long> {
    List<Alteracao> findByUsuarioId(Long usuarioId);
}
