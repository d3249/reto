package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class ComponenteNombre {
    private static final Pattern PATTERN = Pattern.compile("[A-Za-z áéíóúÁÉÍÓÚÑüÜ]+");

    private String contenido;

    protected ComponenteNombre() {
    }

    public ComponenteNombre(String contenido) {
        setContenido(contenido);
    }

    public String contenido() {
        return getContenido();
    }

    protected String getContenido() {
        return contenido;
    }

    protected void setContenido(String contenido) {
        if (contenido == null)
            throw new ValorInvalidoException("El contenido no puede ser nulo");

        if (!PATTERN.matcher(contenido).matches())
            throw new ValorInvalidoException("El nombre y apellido no puede tener números ni caracteres especiales");

        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "ComponenteNombre{" +
                "contenido='" + contenido + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponenteNombre)) return false;
        ComponenteNombre that = (ComponenteNombre) o;
        return getContenido().equals(that.getContenido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContenido());
    }
}
