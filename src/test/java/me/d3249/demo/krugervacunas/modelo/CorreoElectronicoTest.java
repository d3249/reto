package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CorreoElectronicoTest {

    @ParameterizedTest
    @CsvSource({"falso@fake.com", "falso@fake.com.ec", "falso@fake.es"})
    void aceptaCorreoElectronicoValido(String email) {
        var sut = new CorreoElectronico(email);

        Assertions.assertThat(sut.direccion()).isEqualTo(email);
    }

    @ParameterizedTest
    @CsvSource({"@fake.com", "falso@", "falso@fake"})
    void rechazaCorreoElectronicoValido(String email) {
        assertThrows(ValorInvalidoException.class, () -> new CorreoElectronico(email));
    }

    @Test
    void noAceptaNulo() {
        assertThrows(ValorInvalidoException.class, () -> new CorreoElectronico(null));
    }
}