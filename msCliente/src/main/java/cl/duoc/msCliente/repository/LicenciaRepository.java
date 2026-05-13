package cl.duoc.msCliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msCliente.model.Licencia;
@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer>{

    Optional<Licencia> findByClienteId(Integer id);
}
