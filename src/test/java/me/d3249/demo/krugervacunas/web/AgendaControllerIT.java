package me.d3249.demo.krugervacunas.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.web.dto.AgendaDiaria;
import me.d3249.demo.krugervacunas.web.dto.Inventario;
import me.d3249.demo.krugervacunas.web.dto.Registro;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendaControllerIT {
    private static final Logger log = LoggerFactory.getLogger(AgendaControllerIT.class);

    private static final Registro REGISTRO_1 = new Registro("000023401-0", "nombre", "apellidos", LocalDate.parse("1984-04-03"), "falso1@falso.com", Ciudadano.NivelEnfermedad.NO_TIENE);
    private static final Registro REGISTRO_2 = new Registro("000003451-0", "nombre", "apellidos", LocalDate.parse("2000-10-03"), "falso2@falso.com", Ciudadano.NivelEnfermedad.GRAVE);
    private static final Registro REGISTRO_3 = new Registro("002342301-0", "nombre", "apellidos", LocalDate.parse("1998-06-03"), "falso3@falso.com", Ciudadano.NivelEnfermedad.NO_TIENE);
    private static final Registro REGISTRO_4 = new Registro("004500001-0", "nombre", "apellidos", LocalDate.parse("1954-11-03"), "falso4@falso.com", Ciudadano.NivelEnfermedad.LEVE);
    private static final Registro REGISTRO_5 = new Registro("000003411-0", "nombre", "apellidos", LocalDate.parse("1988-01-03"), "falso5@falso.com", Ciudadano.NivelEnfermedad.NO_TIENE);
    private static final Registro REGISTRO_6 = new Registro("342000001-0", "nombre", "apellidos", LocalDate.parse("2001-08-03"), "falso6@falso.com", Ciudadano.NivelEnfermedad.GRAVE);
    private static final Registro REGISTRO_7 = new Registro("003453001-0", "nombre", "apellidos", LocalDate.parse("2000-05-03"), "falso7@falso.com", Ciudadano.NivelEnfermedad.GRAVE);

    private static final LocalDate FECHA = LocalDate.parse("2020-12-31", DateTimeFormatter.ISO_DATE);

    @LocalServerPort
    int port;

    @Value("${kruger.vacunas.usuario}")
    String usuario;

    @Value("${kruger.vacunas.password}")
    String password;

    @Value("${kruger.vacunas.capacidad_diaria}")
    int capacidadDiaria;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(0)
    void cargar() {

        log.info("Cargando datos iniciales");


        List.of(REGISTRO_1, REGISTRO_2, REGISTRO_3, REGISTRO_4, REGISTRO_5, REGISTRO_6, REGISTRO_7)
                .forEach(r ->
                        given()
                                .contentType(ContentType.JSON)
                                .body(r)
                                .post("/api/registro")
                                .then()
                                .assertThat()
                                .statusCode(HttpStatus.CREATED.value())
                );

    }


    @Test
    @Order(1)
    void laRutaEstaProtegida() {
        get("/api/agenda")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @Order(2)
    void inicialmenteLaListaEstaVacia() {
        var agenda = given()
                .auth().basic(usuario, password)
                .get("/api/agenda/{fecha}", FECHA.format(DateTimeFormatter.ISO_DATE))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(AgendaDiaria.class);

        assertThat(agenda.getFecha()).isEqualTo(FECHA);
        assertThat(agenda.getRegistros()).isEmpty();
    }

    @Test
    @Order(3)
    void generaNuevaLista() {
        var agenda = given()
                .auth().basic(usuario, password)
                .post("/api/agenda/{fecha}", FECHA.format(DateTimeFormatter.ISO_DATE))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(AgendaDiaria.class);

        assertThat(agenda.getFecha()).isEqualTo(FECHA);
        assertThat(agenda.getRegistros()).hasSize(capacidadDiaria);
    }

    @Test
    @Order(4)
    void laListaSePuedeConsultar() {
        var agenda = given()
                .auth().basic(usuario, password)
                .get("/api/agenda/{fecha}", FECHA.format(DateTimeFormatter.ISO_DATE))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(AgendaDiaria.class);

        assertThat(agenda.getFecha()).isEqualTo(FECHA);
        assertThat(agenda.getRegistros()).hasSize(capacidadDiaria);
    }
}