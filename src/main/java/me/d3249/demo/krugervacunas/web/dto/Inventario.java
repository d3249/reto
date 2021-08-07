package me.d3249.demo.krugervacunas.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class Inventario {
    private final String marca;
    private final int existencias;

    @JsonCreator
    public Inventario(String marca, int existencias) {
        this.marca = marca;
        this.existencias = existencias;
    }

    public String getMarca() {
        return marca;
    }

    public int getExistencias() {
        return existencias;
    }

    @Override
    public String toString() {
        return "InventarioDTO{" +
                "marca='" + marca + '\'' +
                ", existencias=" + existencias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventario)) return false;
        Inventario that = (Inventario) o;
        return getExistencias() == that.getExistencias() && getMarca().equals(that.getMarca());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMarca(), getExistencias());
    }
}
