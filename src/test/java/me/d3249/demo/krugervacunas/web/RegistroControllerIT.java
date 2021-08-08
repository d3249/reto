package me.d3249.demo.krugervacunas.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.web.dto.Registro;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistroControllerIT {

    private static final String CEDULA_1 = "000000001-0";
    private static final String NOMBRES_1 = "Nombre A";
    private static final String APELLIDOS_1 = "Apellido A";
    private static final LocalDate FECHA_1 = LocalDate.now();
    private static final String EMAIL_1 = "nombre@servidor.com";
    private static final Registro REGISTRO_1 = new Registro(CEDULA_1, NOMBRES_1, APELLIDOS_1, FECHA_1, EMAIL_1, Ciudadano.NivelEnfermedad.GRAVE);

    @LocalServerPort
    int port;

    @Value("${kruger.vacunas.usuario}")
    String usuario;

    @Value("${kruger.vacunas.password}")
    String password;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @CsvSource({" ,nombre, apellidos, algo@algo.com",
            "000000000-0, , apellidos, algo@algo.com",
            "000000000-0, nombre, , algo@algo.com",
            "000000000-0, nombre, apellidos, ",
            "0000000000-0, nombre, apellidos, algo@algo.com",
            "000000000-0, nombre12, apellidos, algo@algo.com",
            "000000000-0, nombre, apellidos#$, algo@algo.com",
            "000000000-0, nombre, apellidos#$, algo@algo",
    })
    @Order(1)
    void siMandaValoresInvalidosONulosRegresaError400(String cedula, String nombres, String apellidos, String email) {

        given()
                .contentType(ContentType.JSON)
                .body(new Registro(cedula, nombres, apellidos, LocalDate.now(), email, Ciudadano.NivelEnfermedad.GRAVE))
                .post("/api/registro")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(2)
    void registraCorrectamente() {
        given()
                .contentType(ContentType.JSON)
                .body(new Registro(CEDULA_1, NOMBRES_1, APELLIDOS_1, FECHA_1, EMAIL_1, Ciudadano.NivelEnfermedad.GRAVE))
                .post("/api/registro")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

    }
}