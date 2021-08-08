package me.d3249.demo.krugervacunas.modelo;


import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComponenteNombreTest {

    @Test
    void aceptaContenidoValido() {
        var contenido = "Abc Def áéíóúü";
        var sut = new ComponenteNombre(contenido);

        assertThat(sut.contenido()).isEqualTo(contenido);
    }

    @ParameterizedTest
    @CsvSource({"Abc3", "'Abc,'", "Abc.", "Abc$"})
    void rechazaContenidoInvalido(String contenido) {
        assertThrows(ValorInvalidoException.class, () -> new ComponenteNombre(contenido));
    }

    @Test
    void noAceptaCadenaVacia() {
        assertThrows(ValorInvalidoException.class, () -> new ComponenteNombre(""));
    }

    @Test
    void noAceptaNulo() {
        assertThrows(ValorInvalidoException.class, () -> new ComponenteNombre(null));
    }
}