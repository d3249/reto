package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.SinDisponibilidadException;
import me.d3249.demo.krugervacunas.modelo.Cita;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.persistencia.CitaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static me.d3249.demo.krugervacunas.modelo.Ciudadano.NivelEnfermedad.*;
import static me.d3249.demo.krugervacunas.negocio.DatosFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class AgendaVacunacionTest {
    private static final Ciudadano CIUDADANO_1 = new Ciudadano(CEDULA_VALIDA_1, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_1, EMAIL_VALIDO, GRAVE);    // prioridad 4 = 3 + 1
    private static final Ciudadano CIUDADANO_2 = new Ciudadano(CEDULA_VALIDA_2, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_2, EMAIL_VALIDO, NO_TIENE); // prioridad 6 = 3 + 3
    private static final Ciudadano CIUDADANO_3 = new Ciudadano(CEDULA_VALIDA_3, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_3, EMAIL_VALIDO, LEVE);     // prioridad 6 = 4 + 2
    private static final Ciudadano CIUDADANO_4 = new Ciudadano(CEDULA_VALIDA_4, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_4, EMAIL_VALIDO, NO_TIENE); // prioridad 4 = 1 + 3
    private static final Ciudadano CIUDADANO_5 = new Ciudadano(CEDULA_VALIDA_5, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_5, EMAIL_VALIDO, GRAVE);    // prioridad 4 = 3 + 1
    private static final Ciudadano CIUDADANO_6 = new Ciudadano(CEDULA_VALIDA_6, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_6, EMAIL_VALIDO, LEVE);     // prioridad 5 = 3 + 2
    private static final Ciudadano CIUDADANO_7 = new Ciudadano(CEDULA_VALIDA_7, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_7, EMAIL_VALIDO, NO_TIENE); // prioridad 5 = 2 + 3
    private static final Ciudadano CIUDADANO_8 = new Ciudadano(CEDULA_VALIDA_8, NOMBRE_VALIDO, APELLIDO_VALIDO, DatosFixtures.FECHA_8, EMAIL_VALIDO, NO_TIENE); // prioridad 7 = 4 + 3

    private static final List<Ciudadano> LISTA_CIUDADANOS = List.of(CIUDADANO_1, CIUDADANO_2, CIUDADANO_3, CIUDADANO_4, CIUDADANO_5, CIUDADANO_6, CIUDADANO_7, CIUDADANO_8);

    private static final List<Cita> LISTA_CITAS = List.of(
            new Cita(CIUDADANO_1, FECHA_1, "AstraZeneca"),
            new Cita(CIUDADANO_4, FECHA_1, "AstraZeneca"),
            new Cita(CIUDADANO_5, FECHA_1, "AstraZeneca"),
            new Cita(CIUDADANO_6, FECHA_1, "AstraZeneca"),
            new Cita(CIUDADANO_7, FECHA_1, "AstraZeneca")
    );

    private static final LocalDate FECHA = LocalDate.now();

    int vacunasPorDia = 5;

    @Mock
    CitaRepository repository;

    @Mock
    RegistroCiudadanos registro;

    @Mock
    AdministradorInventario inventario;

    AgendaVacunacion sut;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sut = new AgendaVacunacion(repository, registro, inventario, vacunasPorDia);
    }

    @Test
    void agendaMaximoCincoPorFecha() throws SinDisponibilidadException {
        given(registro.listaPendientes()).willReturn(LISTA_CIUDADANOS);
        given(inventario.asignar(any())).willReturn("AstraZeneca");

        var resultado = sut.agendar(FECHA);

        assertThat(resultado).containsExactlyInAnyOrder(
                new Cita(CIUDADANO_1, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_4, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_5, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_6, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_7, FECHA, "AstraZeneca")
        );
    }

    @Test
    void omiteSiNoHayExistencia() throws Exception {

        comportamientoDefaultAsignacion();

        var resultado = sut.agendar(FECHA);

        assertThat(resultado).containsExactlyInAnyOrder(
                new Cita(CIUDADANO_1, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_2, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_5, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_6, FECHA, "AstraZeneca"),
                new Cita(CIUDADANO_7, FECHA, "AstraZeneca")
        );

    }

    @Test
    void guardaLasCitasGeneradas() throws Exception {

        comportamientoDefaultAsignacion();

        sut.agendar(FECHA);

        verify(repository).saveAll(any());

    }

    @Test
    void actualizaElEstatusDeLosCiudadanos() throws Exception {

        comportamientoDefaultAsignacion();

        sut.agendar(FECHA);

        verify(registro).marcarProgramados(any());
    }

    private void comportamientoDefaultAsignacion() throws SinDisponibilidadException {
        given(registro.listaPendientes()).willReturn(LISTA_CIUDADANOS);
        given(inventario.asignar(List.of("AstraZeneca", "Sinovac"))).willReturn("AstraZeneca");
        given(inventario.asignar(List.of("Moderna", "AstraZeneca"))).willReturn("AstraZeneca");
        given(inventario.asignar(List.of("Pfizer"))).willThrow(new SinDisponibilidadException(""));
    }

    @Test
    void consultaPorFecha() {
        given(repository.findByFecha(FECHA)).willReturn(LISTA_CITAS);

        var lista = sut.consultar(FECHA);

        assertThat(lista ).isEqualTo(LISTA_CITAS);
    }

    @Test
    void regresaListaVaciaSiNoHayRegistros(){
        given(repository.findByFecha(any())).willReturn(List.of());

        var lista = sut.consultar(FECHA);

        assertThat(lista).isEmpty();
    }
}