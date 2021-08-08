package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CiudadanoTest {
    private static final String CEDULA_VALIDA = "000000000-0";
    private static final String NOMBRE_VALIDO = "Abcd Efg";
    private static final String APELLIDO_VALIDO = "Abcd Efg";
    private static final LocalDate FECHA = LocalDate.parse("1990-12-31", DateTimeFormatter.ISO_DATE);
    private static final String EMAIL_VALIDO = "falso@falso.com";

    @Test
    void rechazaCedulaNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(null, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaNombreNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA, null, APELLIDO_VALIDO, FECHA, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaApellidosNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA, NOMBRE_VALIDO, null, FECHA, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaFechaNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA, NOMBRE_VALIDO, APELLIDO_VALIDO, null, EMAIL_VALIDO, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaEmailNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA, null, Ciudadano.NivelEnfermedad.LEVE));
    }

    @Test
    void rechazaNivelEnfermedadNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Ciudadano(CEDULA_VALIDA, NOMBRE_VALIDO, APELLIDO_VALIDO, FECHA, EMAIL_VALIDO, null));
    }

}