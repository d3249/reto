package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.persistencia.CiudadanoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RegistroCiudadanosTest {
    private static final String CEDULA_1 = "000010000-0";
    private static final String CEDULA_2 = "000000010-0";
    private static final String CEDULA_3 = "000000001-0";

    private static final String NOMBRES = "Antonio Bernarndo";
    private static final String APELLIDOS = "Contreras Diaz";
    private static final LocalDate FECHA_NACIMIENTO = LocalDate.parse("1980-12-31", DateTimeFormatter.ISO_DATE);
    private static final String EMAIL = "antonio@servidor.com";
    private static final Ciudadano.NivelEnfermedad GRAVE = Ciudadano.NivelEnfermedad.GRAVE;

    private static final Ciudadano CIUDADANO_1 = new Ciudadano(CEDULA_1, NOMBRES, APELLIDOS, FECHA_NACIMIENTO, EMAIL, GRAVE);
    private static final Ciudadano CIUDADANO_2 = new Ciudadano(CEDULA_2, NOMBRES, APELLIDOS, FECHA_NACIMIENTO, EMAIL, GRAVE);
    private static final Ciudadano CIUDADANO_3 = new Ciudadano(CEDULA_3, NOMBRES, APELLIDOS, FECHA_NACIMIENTO, EMAIL, GRAVE);

    @Mock
    private CiudadanoRepository repository;

    @InjectMocks
    private RegistroCiudadanos sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void registraCiudadanoNuevo() {
        given(repository.existsByCedulaNumeroCedula(CEDULA_1)).willReturn(false);

        sut.registrar(CEDULA_1, NOMBRES, APELLIDOS, FECHA_NACIMIENTO, EMAIL, GRAVE);

        verify(repository).save(CIUDADANO_1);

    }

    @Test
    void noRegistraCiudadanoDuplicado() {
        given(repository.existsByCedulaNumeroCedula(CEDULA_1)).willReturn(true);

        Assertions.assertThrows(ValorInvalidoException.class, () -> sut.registrar(CEDULA_1, NOMBRES, APELLIDOS, FECHA_NACIMIENTO, EMAIL, GRAVE));
    }

    @Test
    void regresaListaOrdenadaPorCedula() {
        given(repository.findAll()).willReturn(List.of(CIUDADANO_1, CIUDADANO_2, CIUDADANO_3));

        var resultado = sut.lista();

        assertThat(resultado).containsExactly(CIUDADANO_3, CIUDADANO_2, CIUDADANO_1);
    }
}