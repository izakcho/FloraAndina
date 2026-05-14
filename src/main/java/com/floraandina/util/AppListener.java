package com.floraandina.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Inicializa el Sistema al arrancar la aplicación.
 * Pasa las rutas reales de los archivos .txt (igual que el profe con getRealPath).
 */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        // Rutas absolutas de los archivos de datos (como hizo el profe)
        String rutaEspecies = ctx.getRealPath("/data/especies.txt");
        String rutaUsuarios = ctx.getRealPath("/data/usuarios.txt");

        Sistema sistema = new Sistema(rutaEspecies, rutaUsuarios);

        // Datos demo solo si los archivos están vacíos (primera vez)
        if (sistema.obtenerUsuarios().isEmpty()) {
            sistema.registrarUsuario("Admin", "admin@flora.co", "1234", "Administrador");
        }
        if (sistema.obtenerEspecies().isEmpty()) {
            sistema.registrarEspecie("Espeletia grandiflora", "Frailejón",  3800, "Vulnerable",   "Páramo de Pasto");
            sistema.registrarEspecie("Polylepis quadrijuga",  "Árbol de papel", 3500, "En peligro", "Cordillera Central");
            sistema.registrarEspecie("Puya raimondii",        "Puya",       4200, "Amenazada",    "Altiplano Nariñense");
        }

        ctx.setAttribute("sistema", sistema);
        System.out.println("FloraAndina iniciado – datos en: " + rutaEspecies);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
