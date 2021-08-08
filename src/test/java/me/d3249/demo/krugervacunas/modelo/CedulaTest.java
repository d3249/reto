package me.d3249.demo.krugervacunas.modelo;


import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CedulaTest {
    private final String cedulaValida = "000000000-0";

    @Test
    void aceptaCedulaValida() {
        var cedula = new Cedula(cedulaValida);

        assertThat(cedula.getNumeroCedula()).isEqualTo(cedulaValida);
    }

    @ParameterizedTest
    @CsvSource({"00000000000", "00000000-0", "0000000000-0", "000000000-00", "A00000000-0", "0A0000000-0",
            "00A000000-0", "000A00000-0", "0000A0000-0", "00000A000-0", "000000A00-0", "0000000A0-0",
            "00000000A-0", "00A000000-A"})
    void noAceptaCedulasInvalidas(String cedula) {
        assertThrows(ValorInvalidoException.class, () -> new Cedula(cedula));
    }

    @Test
    void noAceptaNulos() {

        assertThrows(ValorInvalidoException.class, () -> new Cedula(null));
    }
}