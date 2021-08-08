package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;
import me.d3249.demo.krugervacunas.persistencia.CiudadanoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RegistroCiudadanos {
    private final CiudadanoRepository repository;

    public RegistroCiudadanos(CiudadanoRepository repository) {
        this.repository = repository;
    }

    public void registrar(String cedula, String nombres, String apellidos, LocalDate fechaNacimiento, String correoElectronico, Ciudadano.NivelEnfermedad grave) {

        if (repository.existsByCedulaNumeroCedula(cedula))
            throw new ValorInvalidoException("El ciudadano ya ha sido registrado");

        repository.save(new Ciudadano(cedula, nombres, apellidos, fechaNacimiento, correoElectronico, grave));
    }


    public List<Ciudadano> lista() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Ciudadano::cedula))
                .collect(Collectors.toList());
    }
}
