package me.d3249.demo.krugervacunas.negocio;

import me.d3249.demo.krugervacunas.modelo.Ciudadano;

import java.util.List;
import java.util.Map;

public class AsignacionDeVacunasSupport {
    private AsignacionDeVacunasSupport() {
    }

    private static final int LIMITE_GRUPO_A = 25;
    private static final int LIMITE_GRUPO_B = 35;
    private static final int LIMITE_GRUPO_C = 50;

    private static final Map<String, List<String>> tabla = Map.of(
            "A", List.of("Sinovac", "Sputnik"),
            "B", List.of("AstraZeneca", "Sinovac"),
            "C", List.of("Moderna", "AstraZeneca"),
            "D", List.of("Pfizer")
    );

    public static Iterable<String> marcasPorEdad(int edad) {
        if (edad > LIMITE_GRUPO_C)
            return tabla.get("D");

        if (edad > LIMITE_GRUPO_B)
            return tabla.get("C");

        if (edad > LIMITE_GRUPO_A)
            return tabla.get("B");

        return tabla.get("A");
    }

    public static int prioridad(Ciudadano ciudadano) {
        return pesoEdad(ciudadano.edad()) + ciudadano.nivelEnfermedad().peso;
    }

    private static int pesoEdad(int edad) {
        if (edad > LIMITE_GRUPO_C)
            return 1;
        else if (edad > LIMITE_GRUPO_B)
            return 2;
        else if (edad > LIMITE_GRUPO_A)
            return 3;
        else
            return 4;
    }
}
