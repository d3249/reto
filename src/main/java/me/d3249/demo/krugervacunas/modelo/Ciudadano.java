package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Ciudadano {

    public enum NivelEnfermedad {
        NO_TIENE, LEVE, GRAVE
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    @AttributeOverride(name = "numeroCedula", column = @Column(name = "cedula"))
    private Cedula cedula;

    @Embedded
    @AttributeOverride(name = "contenido", column = @Column(name = "nombres"))
    private ComponenteNombre nombres;

    @Embedded
    @AttributeOverride(name = "contenido", column = @Column(name = "apellidos"))
    private ComponenteNombre apellidos;

    private LocalDate fechaNacimiento;

    @Embedded
    @AttributeOverride(name = "direccion", column = @Column(name = "correo_electronico"))
    private CorreoElectronico correoElectronico;

    @Enumerated(EnumType.STRING)
    private NivelEnfermedad nivelEnfermedad;

    protected Ciudadano() {
    }

    public Ciudadano(String cedula, String nombres, String apellidos,
                     LocalDate fechaNacimiento, String correoElectronico,
                     NivelEnfermedad nivelEnfermedad) {

        setCedula(cedula);
        setNombres(nombres);
        setApellidos(apellidos);
        setFechaNacimiento(fechaNacimiento);
        setCorreoElectronico(correoElectronico);
        setNivelEnfermedad(nivelEnfermedad);

    }


    public String cedula() {
        return getCedula().getNumeroCedula();
    }

    public String nombres() {
        return getNombres().contenido();
    }

    public String apellidos() {
        return getApellidos().contenido();
    }

    public LocalDate fechaNacimiento() {
        return getFechaNacimiento();
    }

    public String correoElectronico() {
        return getCorreoElectronico().direccion();
    }

    public NivelEnfermedad nivelEnfermedad() {
        return getNivelEnfermedad();
    }

    protected NivelEnfermedad getNivelEnfermedad() {
        return nivelEnfermedad;
    }

    protected void setNivelEnfermedad(NivelEnfermedad nivelEnfermedad) {
        if (nivelEnfermedad == null)
            throw new ValorInvalidoException("El nivel de enfermedad es requerido");

        this.nivelEnfermedad = nivelEnfermedad;
    }

    protected Cedula getCedula() {
        return cedula;
    }

    protected void setCedula(Cedula cedula) {
        this.cedula = cedula;
    }


    protected ComponenteNombre getNombres() {
        return nombres;
    }

    protected void setNombres(ComponenteNombre nombres) {
        this.nombres = nombres;
    }

    protected ComponenteNombre getApellidos() {
        return apellidos;
    }

    protected void setApellidos(ComponenteNombre apellidos) {
        this.apellidos = apellidos;
    }

    protected LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    protected void setFechaNacimiento(LocalDate fechaNacimiento) {

        if (fechaNacimiento == null)
            throw new ValorInvalidoException("La fecha de nacimiento es requerida");

        this.fechaNacimiento = fechaNacimiento;
    }

    protected CorreoElectronico getCorreoElectronico() {
        return correoElectronico;
    }

    protected void setCorreoElectronico(CorreoElectronico correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    private void setCedula(String cedula) {

        setCedula(new Cedula(cedula));
    }

    private void setNombres(String nombres) {
        setNombres(new ComponenteNombre(nombres));
    }

    private void setApellidos(String apellidos) {
        setApellidos(new ComponenteNombre(apellidos));

    }

    private void setCorreoElectronico(String correoElectronico) {
        setCorreoElectronico(new CorreoElectronico(correoElectronico));
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "id=" + id +
                ", cedula=" + cedula +
                ", nombres=" + nombres +
                ", apellidos=" + apellidos +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correoElectronico=" + correoElectronico +
                ", nivelEnfermedad=" + nivelEnfermedad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudadano)) return false;
        Ciudadano ciudadano = (Ciudadano) o;
        return getCedula().equals(ciudadano.getCedula());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCedula());
    }
}
