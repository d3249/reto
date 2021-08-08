package me.d3249.demo.krugervacunas.persistencia;

import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CiudadanoRepository extends CrudRepository<Ciudadano, UUID> {
    boolean existsByCedulaNumeroCedula(String numeroCedula);

    Iterable<Ciudadano> findByEstatus(Ciudadano.Estatus estatus);
}
