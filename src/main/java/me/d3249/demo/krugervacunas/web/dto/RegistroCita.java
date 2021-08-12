package me.d3249.demo.krugervacunas.web.dto;

import me.d3249.demo.krugervacunas.modelo.Ciudadano;

import java.time.LocalDate;
import java.util.Objects;

public class RegistroCita extends Registro {

    private final String marca;

    public RegistroCita(String cedula, String nombres, String apellidos, LocalDate fechaNacimiento,
                        String correoElectronico, Ciudadano.NivelEnfermedad nivelEnfermedad, String marca) {
        super(cedula, nombres, apellidos, fechaNacimiento, correoElectronico, nivelEnfermedad);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroCita)) return false;
        if (!super.equals(o)) return false;
        RegistroCita that = (RegistroCita) o;
        return getMarca().equals(that.getMarca());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMarca());
    }
}
