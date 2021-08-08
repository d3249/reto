package me.d3249.demo.krugervacunas.web;

import me.d3249.demo.krugervacunas.negocio.RegistroCiudadanos;
import me.d3249.demo.krugervacunas.web.dto.Registro;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    private final RegistroCiudadanos registroCiudadanos;

    public RegistroController(RegistroCiudadanos registroCiudadanos) {
        this.registroCiudadanos = registroCiudadanos;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registrar(@RequestBody Registro registro) {
        registroCiudadanos.registrar(registro.getCedula(), registro.getNombres(), registro.getApellidos(),
                registro.getFechaNacimiento(), registro.getCorreoElectronico(), registro.getNivelEnfermedad());
    }

}
