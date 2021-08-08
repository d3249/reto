package me.d3249.demo.krugervacunas.web;

import me.d3249.demo.krugervacunas.negocio.RegistroCiudadanos;
import me.d3249.demo.krugervacunas.web.dto.Registro;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    private final RegistroCiudadanos registroCiudadanos;

    public RegistroController(RegistroCiudadanos registroCiudadanos) {
        this.registroCiudadanos = registroCiudadanos;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody Registro registro) {
        registroCiudadanos.registrar(registro.getCedula(), registro.getNombres(), registro.getApellidos(),
                registro.getFechaNacimiento(), registro.getCorreoElectronico(), registro.getNivelEnfermedad());
    }

}
