package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.SinDisponibilidadException;
import me.d3249.demo.krugervacunas.modelo.Cita;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.persistencia.CitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
public class AgendaVacunacion {
    private static final Logger log = LoggerFactory.getLogger(AgendaVacunacion.class);

    private final CitaRepository repository;
    private final RegistroCiudadanos registro;
    private final AdministradorInventario inventario;
    private final int vacunasPorDia;

    public AgendaVacunacion(CitaRepository repository, RegistroCiudadanos registro, AdministradorInventario inventario,
                            @Value("${kruger.vacunas.capacidad_diaria}") int vacunasPorDia) {
        this.repository = repository;
        this.registro = registro;
        this.inventario = inventario;
        this.vacunasPorDia = vacunasPorDia;
    }

    @Transactional
    public List<Cita> agendar(LocalDate fecha) {

        List<Ciudadano> listaPendientes = registro.listaPendientes();

        log.debug("Hay {} ciudadanos pendientes de agenda", listaPendientes.size());


        var citas = listaPendientes.stream()
                .sorted(Comparator.comparingInt(AsignacionDeVacunasSupport::prioridad).thenComparing(Ciudadano::fechaRegistroUTC))
                .map(c -> crearCita(c, fecha))
                .filter(Objects::nonNull)
                .limit(vacunasPorDia)
                .collect(Collectors.toList());

        log.debug("Se pueden generar {} citas con las condiciones existentes", citas.size());

        repository.saveAll(citas);
        registro.marcarProgramados(citas.stream().map(Cita::ciudadano).collect(Collectors.toList()));

        return citas;
    }


    private Cita crearCita(Ciudadano ciudadano, LocalDate fecha) {
        try {

            var marcas = StreamSupport.stream(AsignacionDeVacunasSupport.marcasPorEdad(ciudadano.edad()).spliterator(), true)
                    .collect(Collectors.toList());

            var marcaAsignada = inventario.asignar(marcas);

            return new Cita(ciudadano, fecha, marcaAsignada);
        } catch (SinDisponibilidadException e) {
            return null;
        }
    }

    public List<Cita> consultar(LocalDate fecha) {
        return StreamSupport.stream(repository.findByFecha(fecha).spliterator(), true).collect(Collectors.toList());
    }
}
