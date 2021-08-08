package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static me.d3249.demo.krugervacunas.negocio.DatosFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class AsignacionDeVacunasSupportTest {

    @Test
    void asignaGrupoA() {
        Stream.of(18,19,20, 21, 22, 23, 24, 25)
                .map(AsignacionDeVacunasSupport::marcasPorEdad)
                .forEach(lista -> assertThat(lista).containsExactlyInAnyOrder("Sinovac", "Sputnik"));
    }

    @Test
    void asignaGrupoB() {
        Stream.of(26, 27, 28, 29, 30, 31, 32, 33, 34, 35)
                .map(AsignacionDeVacunasSupport::marcasPorEdad)
                .forEach(lista -> assertThat(lista).containsExactlyInAnyOrder("AstraZeneca", "Sinovac"));
    }

    @Test
    void asignaGrupoC() {
        Stream.of(36,37,38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50)
                .map(AsignacionDeVacunasSupport::marcasPorEdad)
                .forEach(lista -> assertThat(lista).containsExactlyInAnyOrder("AstraZeneca", "Moderna"));
    }

    @Test
    void asignaGrupoD() {
        List.of(51, 61, 72, 81, 91).stream()
                .map(AsignacionDeVacunasSupport::marcasPorEdad)
                .forEach(lista -> assertThat(lista).containsExactlyInAnyOrder("Pfizer"));
    }


    @Test
    void calculaPrioridad() {
        var fecha50 = LocalDate.parse("1969-12-31", DateTimeFormatter.ISO_DATE);

        var ciudadano = new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, fecha50, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.GRAVE);
        assertThat(AsignacionDeVacunasSupport.prioridad(ciudadano)).isEqualTo(2);

    }

    @ParameterizedTest
    @CsvSource({"1999-12-31, GRAVE, 5",
            "1999-12-31, LEVE, 6",
            "1999-12-31, NO_TIENE, 7",
            "1989-12-31, GRAVE, 4",
            "1989-12-31, LEVE, 5",
            "1989-12-31, NO_TIENE, 6",
            "1979-12-31, GRAVE, 3",
            "1979-12-31, LEVE, 4",
            "1979-12-31, NO_TIENE, 5",
            "1969-12-31, GRAVE, 2",
            "1969-12-31, LEVE, 3",
            "1969-12-31, NO_TIENE, 4",})
    void calculaPrioridad(LocalDate fecha, Ciudadano.NivelEnfermedad nivelEnfermedad, int prioridad) {
        var ciudadano = new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, fecha, EMAIL_VALIDO, nivelEnfermedad);
        assertThat(AsignacionDeVacunasSupport.prioridad(ciudadano)).isEqualTo(prioridad);

    }
}