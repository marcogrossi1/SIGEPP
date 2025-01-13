package proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
