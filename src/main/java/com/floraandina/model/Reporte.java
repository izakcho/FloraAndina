package com.floraandina.model;

public class Reporte {
    private int totalEspecies;
    private String especieMayorAltitud;
    private String especieMenorAltitud;
    private int mayorAltitud;
    private int menorAltitud;

    public Reporte(int totalEspecies, String especieMayorAltitud, int mayorAltitud,
                   String especieMenorAltitud, int menorAltitud) {
        this.totalEspecies = totalEspecies;
        this.especieMayorAltitud = especieMayorAltitud;
        this.mayorAltitud = mayorAltitud;
        this.especieMenorAltitud = especieMenorAltitud;
        this.menorAltitud = menorAltitud;
    }

    public int getTotalEspecies() { return totalEspecies; }
    public String getEspecieMayorAltitud() { return especieMayorAltitud; }
    public String getEspecieMenorAltitud() { return especieMenorAltitud; }
    public int getMayorAltitud() { return mayorAltitud; }
    public int getMenorAltitud() { return menorAltitud; }
}
