package com.floraandina.servlet;

import com.floraandina.util.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombre   = req.getParameter("nombre");
        String correo   = req.getParameter("correo");
        String password = req.getParameter("password");
        String rol      = req.getParameter("rol");

        if (nombre == null || nombre.isBlank() ||
            correo == null || correo.isBlank() ||
            password == null || password.isBlank() ||
            rol == null || rol.isBlank()) {

            req.setAttribute("error", "Todos los campos son obligatorios.");
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }

        Sistema sistema = (Sistema) getServletContext().getAttribute("sistema");
        sistema.registrarUsuario(nombre, correo, password, rol);

        resp.sendRedirect(req.getContextPath() + "/login?registrado=1");
    }
}
