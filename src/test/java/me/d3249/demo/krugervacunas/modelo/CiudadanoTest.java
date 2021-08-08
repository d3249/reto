package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static me.d3249.demo.krugervacunas.negocio.DatosFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CiudadanoTest {

    @Test
    void rechazaCedulaNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(null, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA_1, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaNombreNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA_1, null, APELLIDO_VALIDO, FECHA_1, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaApellidosNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, null, FECHA_1, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaFechaNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, null, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaEmailNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA_1, null, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaNivelEnfermedadNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA_1, EMAIL_VALIDO, null));
    }


    @ParameterizedTest
    @CsvSource({"1999-12-31, 21 ",
            "1989-12-31, 31 ",
            "1979-12-31, 41",
            "1969-12-31, 51",})
        // Buscar la forma de hacer un mock para LocalDate. Mockito.staticMock no lo hace correctamente (tal vez el método
        // LocalDate::now sea final)
    void calculaEdad(LocalDate fecha, int edadEsperada) {

        var edad = (new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, fecha, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.NO_TIENE)).edad();

        assertThat(edad).isEqualTo(edadEsperada);
    }
}