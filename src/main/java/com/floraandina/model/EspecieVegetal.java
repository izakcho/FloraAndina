package com.floraandina.model;

public class EspecieVegetal {

    private int id;
    private int altitud;
    private String nombreCientifico;
    private String nombreComun;
    private String estado;
    private String ubicacion;

    public EspecieVegetal(int id, String nombreCientifico, String nombreComun,
                          int altitud, String estado, String ubicacion) {
        this.id = id;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.altitud = altitud;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public int getId() { return id; }
    public int getAltitud() { return altitud; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public String getEstado() { return estado; }
    public String getUbicacion() { return ubicacion; }

    public void setNombreCientifico(String nombreCientifico) { this.nombreCientifico = nombreCientifico; }
    public void setNombreComun(String nombreComun) { this.nombreComun = nombreComun; }
    public void setAltitud(int altitud) { this.altitud = altitud; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}
