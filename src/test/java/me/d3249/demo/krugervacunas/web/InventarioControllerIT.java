package me.d3249.demo.krugervacunas.web;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.d3249.demo.krugervacunas.web.dto.Inventario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventarioControllerIT {

    private static final TypeRef<List<Inventario>> tipoLista = new TypeRef<>() {
    };

    private static final Inventario vacuna1 = new Inventario("AstraZeneca", 25);
    private static final Inventario vacuna1B = new Inventario("AstraZeneca", 50);
    private static final Inventario vacuna2 = new Inventario("Moderna", 12);
    private static final Inventario vacuna3 = new Inventario("Pfizer", 32);
    private static final Inventario vacuna4 = new Inventario("Sinovac", 20);
    private static final Inventario vacuna5 = new Inventario("Sputnik", 10);
    private static final Inventario vacuna6 = new Inventario("Cansino", 50);

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

    @Test
    @Order(1)
    void unaPeticionNoAutenticadaDevuelveEstatus401() {
        get("/api/inventario")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    @Order(2)
    void recuperaListaDeVacunas()  {

        var resultado = getRequest()
                .get("/api/inventario")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(tipoLista);


        assertThat(resultado).containsExactly(vacuna1, vacuna2, vacuna3, vacuna4, vacuna5);
    }

    private RequestSpecification getRequest() {
        return given()
                .auth()
                .basic(usuario, password)
                .when();
    }

    @Test
    @Order(3)
    void registraNuevaVacuna() {

        var resultado = getRequest()
                .contentType(ContentType.JSON)
                .body(vacuna6)
                .post("/api/inventario")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response()
                .as(tipoLista);

        assertThat(resultado).containsExactly(vacuna1, vacuna6, vacuna2, vacuna3, vacuna4, vacuna5);

    }

    @Test
    @Order(4)
    void registrarUnaMarcaDuplicadaDaError400() {
        getRequest()
                .contentType(ContentType.JSON)
                .body(vacuna1)
                .post("/api/inventario")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @Order(5)
    void actualizaInventario() {
        var resultado = getRequest()
                .contentType(ContentType.JSON)
                .body(new Inventario(null, 50))
                .patch("/api/inventario/{marca}", vacuna1.getMarca())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(tipoLista);

        assertThat(resultado).containsExactly(vacuna1B, vacuna6, vacuna2, vacuna3, vacuna4, vacuna5);
    }

    @Test
    @Order(6)
    void actualizarConValorNegativoDaError400() {

         getRequest()
                .contentType(ContentType.JSON)
                .body(new Inventario(null, -50))
                .patch("/api/inventario/{marca}", vacuna1.getMarca())
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}