package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;
import org.apache.commons.validator.EmailValidator;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CorreoElectronico {
    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private String direccion;

    protected CorreoElectronico(){}

    public CorreoElectronico(String direccion) {

        if(!EMAIL_VALIDATOR.isValid(direccion))
            throw new ValorInvalidoException("Dirección de correo electrónico inválida");

        this.direccion = direccion;

    }

    public String direccion(){
        return getDireccion();
    }

    protected String getDireccion() {
        return direccion;
    }

    protected void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "CorreoElectronico{" +
                "direccion='" + direccion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CorreoElectronico)) return false;
        CorreoElectronico that = (CorreoElectronico) o;
        return getDireccion().equals(that.getDireccion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDireccion());
    }
}
