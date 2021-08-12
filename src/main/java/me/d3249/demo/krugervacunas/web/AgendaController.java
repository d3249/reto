package me.d3249.demo.krugervacunas.web;

import me.d3249.demo.krugervacunas.negocio.AgendaVacunacion;
import me.d3249.demo.krugervacunas.web.dto.AgendaDiaria;
import me.d3249.demo.krugervacunas.web.dto.RegistroCita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
    private static final Logger log = LoggerFactory.getLogger(AgendaController.class);

    private final AgendaVacunacion agenda;

    public AgendaController(AgendaVacunacion agenda) {
        this.agenda = agenda;
    }

    @GetMapping(value = "/{fechaString}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgendaDiaria consultarAgenda(@PathVariable String fechaString) {

        var fecha = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);

        var registros = agenda.consultar(fecha).stream()
                .map(c -> new RegistroCita(c.ciudadano().cedula(), c.ciudadano().nombres(),
                        c.ciudadano().apellidos(), c.ciudadano().fechaNacimiento(), c.ciudadano().correoElectronico(),
                        c.ciudadano().nivelEnfermedad(), c.marca()))
                .collect(Collectors.toList());

        return new AgendaDiaria(fecha, registros);

    }

    @PostMapping(value = "/{fechaString}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgendaDiaria generarAgenda(@PathVariable String fechaString) {

        log.debug("Agendando citas para la fecha {}", fechaString);

        var fecha = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);

        var registros = agenda.agendar(fecha).stream()
                .map(c -> new RegistroCita(c.ciudadano().cedula(), c.ciudadano().nombres(),
                        c.ciudadano().apellidos(), c.ciudadano().fechaNacimiento(), c.ciudadano().correoElectronico(),
                        c.ciudadano().nivelEnfermedad(), c.marca()))
                .collect(Collectors.toList());

        log.debug("{} citas generadas", registros.size());

        return new AgendaDiaria(fecha, registros);

    }
}
