package me.d3249.demo.krugervacunas.web.dto;

import java.time.LocalDate;
import java.util.List;

public class AgendaDiaria {
    private final LocalDate fecha;
    private final List<Registro> registros;

    public AgendaDiaria(LocalDate fecha, List<Registro> registros) {
        this.fecha = fecha;
        this.registros = registros;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Registro> getRegistros() {
        return registros;
    }
}
