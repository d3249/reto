package me.d3249.demo.krugervacunas.persistencia;

import me.d3249.demo.krugervacunas.modelo.Cita;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface CitaRepository extends CrudRepository<Cita, UUID> {
    Iterable<Cita> findByFecha(LocalDate fecha);
}
