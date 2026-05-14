package com.floraandina.util;

import com.floraandina.model.EspecieVegetal;
import com.floraandina.model.Reporte;
import com.floraandina.model.Usuario;

import java.io.*;
import java.util.ArrayList;

/**
 * Sistema actúa como el estado global de la aplicación.
 * APO III – Persistencia con archivos de texto (.txt)
 * Mismo enfoque del profesor: leerArchivo() y guardarArchivo()
 */
public class Sistema {

    private String rutaEspecies;
    private String rutaUsuarios;

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<EspecieVegetal> especies = new ArrayList<>();
    private int contadorUsuario = 1;
    private int contadorEspecie = 1;

    /**
     * Constructor que recibe rutas absolutas de los archivos .txt
     * (iguales al del profe: la ruta viene desde el Servlet/Listener)
     */
    public Sistema(String rutaEspecies, String rutaUsuarios) {
        this.rutaEspecies = rutaEspecies;
        this.rutaUsuarios = rutaUsuarios;
        this.usuarios = leerUsuarios();
        this.especies = leerEspecies();

        // Ajustar contadores para no pisar IDs ya guardados
        for (Usuario u : usuarios)
            if (u.getId() >= contadorUsuario) contadorUsuario = u.getId() + 1;
        for (EspecieVegetal e : especies)
            if (e.getId() >= contadorEspecie) contadorEspecie = e.getId() + 1;
    }

    // ─── USUARIOS ────────────────────────────────────────────────────────────

    public void registrarUsuario(String nombre, String correo, String password, String rol) {
        usuarios.add(new Usuario(contadorUsuario++, nombre, correo, password, rol));
        guardarUsuarios();
    }

    public boolean login(String correo, String password) {
        for (Usuario u : usuarios)
            if (u.getCorreo().equals(correo) && u.getPassword().equals(password)) return true;
        return false;
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios)
            if (u.getCorreo().equals(correo)) return u;
        return null;
    }

    public ArrayList<Usuario> obtenerUsuarios() { return usuarios; }

    // ─── ESPECIES ─────────────────────────────────────────────────────────────

    public void registrarEspecie(String nombreCientifico, String nombreComun,
                                 int altitud, String estado, String ubicacion) {
        especies.add(new EspecieVegetal(contadorEspecie++, nombreCientifico,
                nombreComun, altitud, estado, ubicacion));
        guardarEspecies();
    }

    public void eliminarEspecie(int id) {
        especies.removeIf(e -> e.getId() == id);
        guardarEspecies();
    }

    public void editarEspecie(int id, String cientifico, String comun,
                              int altitud, String estado, String ubicacion) {
        for (EspecieVegetal e : especies) {
            if (e.getId() == id) {
                e.setNombreCientifico(cientifico);
                e.setNombreComun(comun);
                e.setAltitud(altitud);
                e.setEstado(estado);
                e.setUbicacion(ubicacion);
                break;
            }
        }
        guardarEspecies();
    }

    public EspecieVegetal buscarEspeciePorId(int id) {
        for (EspecieVegetal e : especies)
            if (e.getId() == id) return e;
        return null;
    }

    public ArrayList<EspecieVegetal> obtenerEspecies() { return especies; }

    public ArrayList<EspecieVegetal> filtrarPorNombre(String texto) {
        ArrayList<EspecieVegetal> resultado = new ArrayList<>();
        String t = texto.toLowerCase();
        for (EspecieVegetal e : especies)
            if (e.getNombreCientifico().toLowerCase().contains(t) ||
                e.getNombreComun().toLowerCase().contains(t))
                resultado.add(e);
        return resultado;
    }

    // ─── REPORTE ─────────────────────────────────────────────────────────────

    public Reporte generarReporte() {
        if (especies.isEmpty()) return new Reporte(0, "N/A", 0, "N/A", 0);
        EspecieVegetal mayor = especies.get(0);
        EspecieVegetal menor = especies.get(0);
        for (EspecieVegetal e : especies) {
            if (e.getAltitud() > mayor.getAltitud()) mayor = e;
            if (e.getAltitud() < menor.getAltitud()) menor = e;
        }
        return new Reporte(especies.size(),
                mayor.getNombreComun(), mayor.getAltitud(),
                menor.getNombreComun(), menor.getAltitud());
    }

    /**
     * Exporta el reporte a un archivo .txt (misma lógica que guardarArchivo del profe)
     */
    public void exportarReporteTxt(String rutaReporte) {
        File archivo = new File(rutaReporte);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();

        Reporte rep = generarReporte();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            bw.write("========================================");
            bw.newLine();
            bw.write("     REPORTE FLORA ANDINA - NARINO");
            bw.newLine();
            bw.write("========================================");
            bw.newLine();
            bw.write("Total de especies registradas : " + rep.getTotalEspecies());
            bw.newLine();
            bw.write("Especie a mayor altitud       : " + rep.getEspecieMayorAltitud()
                    + " (" + rep.getMayorAltitud() + " m.s.n.m.)");
            bw.newLine();
            bw.write("Especie a menor altitud       : " + rep.getEspecieMenorAltitud()
                    + " (" + rep.getMenorAltitud() + " m.s.n.m.)");
            bw.newLine();
            bw.write("----------------------------------------");
            bw.newLine();
            bw.write("LISTADO COMPLETO DE ESPECIES:");
            bw.newLine();
            bw.write("----------------------------------------");
            bw.newLine();
            for (EspecieVegetal e : especies) {
                bw.write(String.join(" | ",
                        "ID:" + e.getId(),
                        e.getNombreCientifico(),
                        e.getNombreComun(),
                        e.getAltitud() + "m",
                        e.getEstado(),
                        e.getUbicacion()
                ));
                bw.newLine();
            }
            bw.write("========================================");
            bw.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // ─── PERSISTENCIA – ESPECIES (como el profe) ──────────────────────────────

    /** Guarda todas las especies en CSV: id,cientifico,comun,altitud,estado,ubicacion */
    private void guardarEspecies() {
        File archivo = new File(rutaEspecies);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (EspecieVegetal e : especies) {
                bw.write(String.join(",",
                        String.valueOf(e.getId()),
                        e.getNombreCientifico(),
                        e.getNombreComun(),
                        String.valueOf(e.getAltitud()),
                        e.getEstado(),
                        e.getUbicacion()
                ));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Lee el archivo de especies y reconstruye los objetos */
    private ArrayList<EspecieVegetal> leerEspecies() {
        File archivo = new File(rutaEspecies);
        ArrayList<EspecieVegetal> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",", 6); // máx 6 partes
                if (campos.length >= 6) {
                    lista.add(new EspecieVegetal(
                            Integer.parseInt(campos[0]),
                            campos[1],
                            campos[2],
                            Integer.parseInt(campos[3]),
                            campos[4],
                            campos[5]
                    ));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // ─── PERSISTENCIA – USUARIOS ─────────────────────────────────────────────

    /** Guarda todos los usuarios en CSV: id,nombre,correo,password,rol */
    private void guardarUsuarios() {
        File archivo = new File(rutaUsuarios);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Usuario u : usuarios) {
                bw.write(String.join(",",
                        String.valueOf(u.getId()),
                        u.getNombre(),
                        u.getCorreo(),
                        u.getPassword(),
                        u.getRol()
                ));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Lee el archivo de usuarios y reconstruye los objetos */
    private ArrayList<Usuario> leerUsuarios() {
        File archivo = new File(rutaUsuarios);
        ArrayList<Usuario> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",", 5);
                if (campos.length >= 5) {
                    lista.add(new Usuario(
                            Integer.parseInt(campos[0]),
                            campos[1],
                            campos[2],
                            campos[3],
                            campos[4]
                    ));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
