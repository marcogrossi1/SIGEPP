package proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.model.Topico;

import java.util.List;

@Repository
public interface TopicoDao extends JpaRepository<Topico, Long> {
    List<Topico> findBySecaoId(Long secaoId);
}
