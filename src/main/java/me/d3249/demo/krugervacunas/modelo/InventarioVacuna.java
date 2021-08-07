package me.d3249.demo.krugervacunas.modelo;

import me.d3249.demo.krugervacunas.excepcion.ValorInvalidoException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class InventarioVacuna {

    @Id
    @GeneratedValue
    private UUID id;
    private String marca;
    private Integer existencias;

    protected InventarioVacuna() {
        //Constructor vacío para Hibernate
    }

    public InventarioVacuna(String marca, Integer existencias) {
        setMarca(marca);
        setExistencias(existencias);
    }


    public Integer existencias() {
        return existencias;
    }

    public String marca() {
        return marca;
    }


    protected UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    protected String getMarca() {
        return marca;
    }

    protected void setMarca(String marca) {
        if (marca.isBlank())
            throw new ValorInvalidoException("Se necesita una marca");

        this.marca = marca;
    }

    protected Integer getExistencias() {
        return existencias;
    }

    protected void setExistencias(Integer existencias) {
        if (existencias < 0)
            throw new ValorInvalidoException("Las existencia no puede ser negativa");

        this.existencias = existencias;
    }

    public void actualizarExistencias(int existencias) {
        setExistencias(existencias);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioVacuna)) return false;
        InventarioVacuna that = (InventarioVacuna) o;
        return Objects.equals(getId(), that.getId()) && getMarca().equals(that.getMarca());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMarca());
    }
}
