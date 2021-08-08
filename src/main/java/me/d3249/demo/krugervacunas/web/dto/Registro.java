package me.d3249.demo.krugervacunas.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.d3249.demo.krugervacunas.modelo.Ciudadano;

import java.time.LocalDate;
import java.util.Objects;

public class Registro {
    private final String cedula;
    private final String nombres;
    private final String apellidos;
    private final LocalDate fechaNacimiento;
    private final String correoElectronico;
    private final Ciudadano.NivelEnfermedad nivelEnfermedad;

    @JsonCreator
    public Registro(String cedula, String nombres, String apellidos, LocalDate fechaNacimiento, String correoElectronico, Ciudadano.NivelEnfermedad nivelEnfermedad) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.nivelEnfermedad = nivelEnfermedad;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Ciudadano.NivelEnfermedad getNivelEnfermedad() {
        return nivelEnfermedad;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", nivelEnfermedad=" + nivelEnfermedad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registro)) return false;
        Registro registro = (Registro) o;
        return Objects.equals(getCedula(), registro.getCedula()) && Objects.equals(getNombres(), registro.getNombres()) && Objects.equals(getApellidos(), registro.getApellidos()) && Objects.equals(getFechaNacimiento(), registro.getFechaNacimiento()) && Objects.equals(getCorreoElectronico(), registro.getCorreoElectronico()) && getNivelEnfermedad() == registro.getNivelEnfermedad();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCedula(), getNombres(), getApellidos(), getFechaNacimiento(), getCorreoElectronico(), getNivelEnfermedad());
    }
}
