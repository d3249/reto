package me.d3249.demo.krugervacunas.modelo;


import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventarioVacunaTest {
    private final String marca = "marca";

    private InventarioVacuna sut;

    @BeforeEach
    void setUp() {
        int existenciaInicial = 0;
        sut = new InventarioVacuna(marca, existenciaInicial);
    }

    @Test
    void podemosActualizarLasExistencias() {
        int nuevaExistencia = 30;

        sut.actualizarExistencias(nuevaExistencia);

        assertThat(sut.existencias()).isEqualTo(nuevaExistencia);
    }

    @Test
    void noSePuedeCrearConExistenciaNegativa() {
        assertThrows(ValorInvalidoException.class, () -> new InventarioVacuna(marca, -1));
    }

    @Test
    void noSePuedeActualizarAExistenciaNegativa() {
        assertThrows(ValorInvalidoException.class, () -> sut.actualizarExistencias(-1));
    }
}