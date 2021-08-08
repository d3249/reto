package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Cedula {

    private static final Pattern PATRON = Pattern.compile("\\d{9}-\\d");

    private String numeroCedula;

    protected Cedula() {
    }


    public Cedula(String numeroCedula) {
        setNumeroCedula(numeroCedula);
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    protected void setNumeroCedula(String cedula) {
        if (cedula == null)
            throw new ValorInvalidoException("El contenido no puede ser nulo");

        if (!PATRON.matcher(cedula.toUpperCase()).matches())
            throw new ValorInvalidoException("No se cumple con el formato de la cédula");

        this.numeroCedula = cedula.toUpperCase();
    }

    @Override
    public String toString() {
        return "Cedula{" +
                "numeroCedula='" + numeroCedula + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cedula)) return false;
        Cedula cedula1 = (Cedula) o;
        return getNumeroCedula().equals(cedula1.getNumeroCedula());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroCedula());
    }
}
