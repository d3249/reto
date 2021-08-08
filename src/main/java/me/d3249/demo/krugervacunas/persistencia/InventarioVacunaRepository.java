package me.d3249.demo.krugervacunas.persistencia;

import me.d3249.demo.krugervacunas.modelo.InventarioVacuna;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventarioVacunaRepository extends CrudRepository<InventarioVacuna, UUID> {

    Optional<InventarioVacuna> findByMarca(String marca);

    boolean existsByMarca(String marca);

    List<InventarioVacuna> findByMarcaIn(List<String> marcas);
}
