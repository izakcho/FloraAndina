<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList, com.floraandina.model.EspecieVegetal" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Especies – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
    <span class="brand">Flora<span>Andina</span></span>
    <nav>
        <a href="${pageContext.request.contextPath}/especies" class="active">Especies</a>
        <a href="${pageContext.request.contextPath}/especies?accion=nueva">Nueva Especie</a>
        <a href="${pageContext.request.contextPath}/especies?accion=reporte">Reporte</a>
        <a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
    </nav>
    <span class="user-info">${sessionScope.usuario.nombre} (${sessionScope.usuario.rol})</span>
</div>

<div class="container">
    <div class="page-header">
        <h1>Especies Registradas</h1>
        <a href="${pageContext.request.contextPath}/especies?accion=nueva" class="btn btn-verde">+ Nueva Especie</a>
    </div>

    <% String msg = request.getParameter("msg"); %>
    <% if ("registrada".equals(msg)) { %><div class="alert alert-success">Especie registrada correctamente.</div><% } %>
    <% if ("actualizada".equals(msg)) { %><div class="alert alert-success">Especie actualizada correctamente.</div><% } %>
    <% if ("eliminada".equals(msg)) { %><div class="alert alert-success">Especie eliminada.</div><% } %>

    <div class="card">
        <form method="get" action="${pageContext.request.contextPath}/especies" class="search-bar">
            <input type="text" name="buscar" placeholder="Buscar por nombre científico o común..."
                   value="${buscar != null ? buscar : ''}">
            <button type="submit" class="btn btn-verde">Buscar</button>
            <% if (request.getAttribute("buscar") != null) { %>
                <a href="${pageContext.request.contextPath}/especies" class="btn btn-claro">Limpiar</a>
            <% } %>
        </form>

        <%
            ArrayList<EspecieVegetal> lista = (ArrayList<EspecieVegetal>) request.getAttribute("especies");
        %>

        <% if (lista == null || lista.isEmpty()) { %>
            <p style="text-align:center;color:#777;padding:2rem 0;">
                No se encontraron especies. <a href="${pageContext.request.contextPath}/especies?accion=nueva">Registra la primera.</a>
            </p>
        <% } else { %>
        <div class="tabla-wrap">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre Científico</th>
                        <th>Nombre Común</th>
                        <th>Altitud (m)</th>
                        <th>Estado</th>
                        <th>Ubicación</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for (EspecieVegetal e : lista) { %>
                    <tr>
                        <td><strong><%= e.getId() %></strong></td>
                        <td><em><%= e.getNombreCientifico() %></em></td>
                        <td><%= e.getNombreComun() %></td>
                        <td><%= e.getAltitud() %> m</td>
                        <td>
                            <%
                                String est = e.getEstado();
                                String badge = "badge-vulnerable";
                                if ("En peligro".equalsIgnoreCase(est)) badge = "badge-peligro";
                                else if ("Amenazada".equalsIgnoreCase(est)) badge = "badge-amenazada";
                            %>
                            <span class="badge <%= badge %>"><%= est %></span>
                        </td>
                        <td><%= e.getUbicacion() %></td>
                        <td style="display:flex;gap:.4rem;flex-wrap:wrap">
                            <a href="${pageContext.request.contextPath}/especies?accion=editar&id=<%= e.getId() %>"
                               class="btn btn-amarillo btn-sm">Editar</a>
                            <a href="${pageContext.request.contextPath}/especies?accion=eliminar&id=<%= e.getId() %>"
                               class="btn btn-rojo btn-sm"
                               onclick="return confirm('¿Eliminar esta especie?')">Eliminar</a>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <p style="margin-top:.8rem;font-size:.85rem;color:#777;">Total: <%= lista.size() %> especie(s)</p>
        <% } %>
    </div>
</div>

</body>
</html>
