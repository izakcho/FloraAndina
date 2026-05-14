package com.floraandina.servlet;

import com.floraandina.model.EspecieVegetal;
import com.floraandina.util.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Maneja todas las operaciones sobre especies vegetales.
 * Incluye exportación de reporte a .txt
 */
@WebServlet("/especies")
public class EspeciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String accion = req.getParameter("accion");
        Sistema sistema = getSistema();
        if (accion == null) accion = "listar";

        switch (accion) {

            case "nueva":
                req.getRequestDispatcher("/WEB-INF/views/nueva_especie.jsp").forward(req, resp);
                break;

            case "editar": {
                int id = Integer.parseInt(req.getParameter("id"));
                EspecieVegetal e = sistema.buscarEspeciePorId(id);
                req.setAttribute("especie", e);
                req.getRequestDispatcher("/WEB-INF/views/editar_especie.jsp").forward(req, resp);
                break;
            }

            case "eliminar": {
                int id = Integer.parseInt(req.getParameter("id"));
                sistema.eliminarEspecie(id);
                resp.sendRedirect(req.getContextPath() + "/especies?msg=eliminada");
                break;
            }

            case "reporte": {
                req.setAttribute("reporte", sistema.generarReporte());
                req.setAttribute("especies", sistema.obtenerEspecies());
                req.getRequestDispatcher("/WEB-INF/views/reporte.jsp").forward(req, resp);
                break;
            }

            // ── EXPORTAR REPORTE A .TXT ────────────────────────────────────
            case "exportarTxt": {
                // 1. Guardar el archivo en el servidor (igual que el profe)
                String rutaReporte = getServletContext().getRealPath("/data/reporte.txt");
                sistema.exportarReporteTxt(rutaReporte);

                // 2. Enviar el archivo al navegador para descarga
                File archivo = new File(rutaReporte);
                resp.setContentType("text/plain; charset=UTF-8");
                resp.setHeader("Content-Disposition", "attachment; filename=\"reporte_flora_andina.txt\"");
                resp.setContentLengthLong(archivo.length());

                try (BufferedReader br = new BufferedReader(new FileReader(archivo));
                     PrintWriter pw = resp.getWriter()) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        pw.println(linea);
                    }
                }
                break;
            }

            default: {
                String buscar = req.getParameter("buscar");
                ArrayList<EspecieVegetal> lista;
                if (buscar != null && !buscar.isBlank()) {
                    lista = sistema.filtrarPorNombre(buscar);
                    req.setAttribute("buscar", buscar);
                } else {
                    lista = sistema.obtenerEspecies();
                }
                req.setAttribute("especies", lista);
                req.getRequestDispatcher("/WEB-INF/views/especies.jsp").forward(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!estaAutenticado(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String accion = req.getParameter("accion");
        Sistema sistema = getSistema();

        if ("registrar".equals(accion)) {
            String cientifico = req.getParameter("nombreCientifico");
            String comun      = req.getParameter("nombreComun");
            int altitud       = Integer.parseInt(req.getParameter("altitud"));
            String estado     = req.getParameter("estado");
            String ubicacion  = req.getParameter("ubicacion");

            if (cientifico.isBlank() || comun.isBlank() || estado.isBlank() || ubicacion.isBlank()) {
                req.setAttribute("error", "Todos los campos son obligatorios.");
                req.getRequestDispatcher("/WEB-INF/views/nueva_especie.jsp").forward(req, resp);
                return;
            }
            sistema.registrarEspecie(cientifico, comun, altitud, estado, ubicacion);
            resp.sendRedirect(req.getContextPath() + "/especies?msg=registrada");

        } else if ("actualizar".equals(accion)) {
            int id            = Integer.parseInt(req.getParameter("id"));
            String cientifico = req.getParameter("nombreCientifico");
            String comun      = req.getParameter("nombreComun");
            int altitud       = Integer.parseInt(req.getParameter("altitud"));
            String estado     = req.getParameter("estado");
            String ubicacion  = req.getParameter("ubicacion");

            sistema.editarEspecie(id, cientifico, comun, altitud, estado, ubicacion);
            resp.sendRedirect(req.getContextPath() + "/especies?msg=actualizada");
        }
    }

    private boolean estaAutenticado(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null && s.getAttribute("usuario") != null;
    }

    private Sistema getSistema() {
        return (Sistema) getServletContext().getAttribute("sistema");
    }
}
