<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.floraandina.model.EspecieVegetal, com.floraandina.model.Reporte, java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
    <span class="brand">Flora<span>Andina</span></span>
    <nav>
        <a href="${pageContext.request.contextPath}/especies">Especies</a>
        <a href="${pageContext.request.contextPath}/especies?accion=nueva">Nueva Especie</a>
        <a href="${pageContext.request.contextPath}/especies?accion=reporte" class="active">Reporte</a>
        <a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
    </nav>
    <span class="user-info">${sessionScope.usuario.nombre}</span>
</div>

<div class="container">
    <div class="page-header">
        <h1>Reporte General</h1>
        <a href="${pageContext.request.contextPath}/especies" class="btn btn-claro">← Volver</a>
    </div>

    <%
        Reporte rep = (Reporte) request.getAttribute("reporte");
        ArrayList<EspecieVegetal> especies = (ArrayList<EspecieVegetal>) request.getAttribute("especies");
    %>

    <!-- Tarjetas de estadísticas -->
    <div class="reporte-stats">
        <div class="stat-card">
            <div class="numero"><%= rep.getTotalEspecies() %></div>
            <div class="label">Total de Especies Registradas</div>
        </div>
        <div class="stat-card">
            <div class="numero" style="font-size:1.2rem"><%= rep.getEspecieMayorAltitud() %></div>
            <div class="label">Mayor Altitud: <%= rep.getMayorAltitud() %> m.s.n.m.</div>
        </div>
        <div class="stat-card">
            <div class="numero" style="font-size:1.2rem"><%= rep.getEspecieMenorAltitud() %></div>
            <div class="label">Menor Altitud: <%= rep.getMenorAltitud() %> m.s.n.m.</div>
        </div>
    </div>

    <!-- Botón exportar a .txt -->
    <div class="reporte-actions">
        <a href="${pageContext.request.contextPath}/especies?accion=exportarTxt"
           class="btn btn-txt">⬇ Exportar reporte (.txt)</a>
    </div>

    <!-- Tabla completa -->
    <div class="card">
        <h2 style="color:#1a5c2a;margin-bottom:1rem;font-size:1.1rem;font-weight:700;text-align:left;">
            Listado Completo de Especies
        </h2>

        <% if (especies == null || especies.isEmpty()) { %>
            <p style="text-align:center;color:#777;padding:1.5rem">No hay especies registradas aún.</p>
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
                    </tr>
                </thead>
                <tbody>
                <% for (EspecieVegetal e : especies) {
                    String est = e.getEstado();
                    String badge = "badge-vulnerable";
                    if ("En peligro".equalsIgnoreCase(est))  badge = "badge-peligro";
                    else if ("Amenazada".equalsIgnoreCase(est)) badge = "badge-amenazada";
                %>
                    <tr>
                        <td><%= e.getId() %></td>
                        <td><em><%= e.getNombreCientifico() %></em></td>
                        <td><%= e.getNombreComun() %></td>
                        <td><strong><%= e.getAltitud() %> m</strong></td>
                        <td><span class="badge <%= badge %>"><%= est %></span></td>
                        <td><%= e.getUbicacion() %></td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>
