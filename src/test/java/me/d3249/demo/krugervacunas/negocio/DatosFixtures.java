package me.d3249.demo.krugervacunas.negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatosFixtures {
    private DatosFixtures() {
    }

    public static final String CEDULA_VALIDA_1 = "000000001-0";
    public static final String CEDULA_VALIDA_2 = "000000002-0";
    public static final String CEDULA_VALIDA_3 = "000000003-0";
    public static final String CEDULA_VALIDA_4 = "000000004-0";
    public static final String CEDULA_VALIDA_5 = "000000005-0";
    public static final String CEDULA_VALIDA_6 = "000000006-0";
    public static final String CEDULA_VALIDA_7 = "000000007-0";
    public static final String CEDULA_VALIDA_8 = "000000008-0";

    public static final LocalDate FECHA_4 = LocalDate.parse("1945-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_7 = LocalDate.parse("1977-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_5 = LocalDate.parse("1986-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_6 = LocalDate.parse("1986-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_1 = LocalDate.parse("1990-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_2 = LocalDate.parse("1990-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_8 = LocalDate.parse("1991-12-31", DateTimeFormatter.ISO_DATE);
    public static final LocalDate FECHA_3 = LocalDate.parse("2000-12-31", DateTimeFormatter.ISO_DATE);

    public static final String NOMBRE_VALIDO = "Abcd Efg";
    public static final String APELLIDO_VALIDO = "Abcd Efg";
    public static final String EMAIL_VALIDO = "falso@falso.com";
}
