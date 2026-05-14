package com.floraandina.servlet;

import com.floraandina.model.Usuario;
import com.floraandina.util.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Si ya tiene sesión, redirigir al panel
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            resp.sendRedirect(req.getContextPath() + "/especies");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String correo   = req.getParameter("correo");
        String password = req.getParameter("password");

        Sistema sistema = (Sistema) getServletContext().getAttribute("sistema");

        if (sistema.login(correo, password)) {
            Usuario u = sistema.buscarUsuarioPorCorreo(correo);
            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", u);
            resp.sendRedirect(req.getContextPath() + "/especies");
        } else {
            req.setAttribute("error", "Correo o contraseña incorrectos.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
