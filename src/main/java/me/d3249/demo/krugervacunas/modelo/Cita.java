package me.d3249.demo.krugervacunas.modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Cita {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "fk_ciudadano")
    private Ciudadano ciudadano;
    private LocalDate fecha;
    private String marca;

    protected Cita() {
    }

    public Cita(Ciudadano ciudadano, LocalDate fecha, String marca) {
        this.ciudadano = ciudadano;
        this.fecha = fecha;
        this.marca = marca;
    }

    public Ciudadano ciudadano(){
        return getCiudadano();
    }

    public String marca(){
        return getMarca();
    }

    protected UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    protected Ciudadano getCiudadano() {
        return ciudadano;
    }

    protected void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    protected LocalDate getFecha() {
        return fecha;
    }

    protected void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    protected String getMarca() {
        return marca;
    }

    protected void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", ciudadano=" + ciudadano +
                ", fecha=" + fecha +
                ", marca='" + marca + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return getCiudadano().equals(cita.getCiudadano()) && getFecha().equals(cita.getFecha());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCiudadano(), getFecha());
    }
}
