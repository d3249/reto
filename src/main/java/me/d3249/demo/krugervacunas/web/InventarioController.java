package me.d3249.demo.krugervacunas.web;

import me.d3249.demo.krugervacunas.negocio.AdministradorInventario;
import me.d3249.demo.krugervacunas.web.dto.Inventario;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final AdministradorInventario administradorInventario;

    public InventarioController(AdministradorInventario administradorInventario) {
        this.administradorInventario = administradorInventario;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Inventario> lista() {
        return administradorInventario.inventarioCompleto()
                .stream()
                .map(it -> new Inventario(it.marca(), it.existencias()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Inventario> nuevo(@RequestBody Inventario nuevaMarca) {
        administradorInventario.registrarMarca(nuevaMarca.getMarca(), nuevaMarca.getExistencias());

        return lista();
    }

    @PatchMapping(value = "/{marca}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Inventario> actualizar(@PathVariable String marca, @RequestBody Inventario nuevoValor) {
        administradorInventario.actualizar(marca, nuevoValor.getExistencias());

        return lista();
    }

}
