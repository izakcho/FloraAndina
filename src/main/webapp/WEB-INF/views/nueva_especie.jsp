<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Especie – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
    <span class="brand">Flora<span>Andina</span></span>
    <nav>
        <a href="${pageContext.request.contextPath}/especies">Especies</a>
        <a href="${pageContext.request.contextPath}/especies?accion=nueva" class="active">Nueva Especie</a>
        <a href="${pageContext.request.contextPath}/especies?accion=reporte">Reporte</a>
        <a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
    </nav>
    <span class="user-info">${sessionScope.usuario.nombre}</span>
</div>

<div class="container">
    <div class="page-header">
        <h1>Registrar Nueva Especie</h1>
        <a href="${pageContext.request.contextPath}/especies" class="btn btn-claro">Volver</a>
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">${error}</div>
    <% } %>

    <div class="card">
        <form method="post" action="${pageContext.request.contextPath}/especies">
            <input type="hidden" name="accion" value="registrar">

            <div class="form-grid">
                <div class="form-group">
                    <label for="nombreCientifico">Nombre Científico *</label>
                    <input type="text" class="form-control" id="nombreCientifico" name="nombreCientifico"
                           placeholder="Ej: Espeletia grandiflora" required autofocus>
                </div>
                <div class="form-group">
                    <label for="nombreComun">Nombre Común *</label>
                    <input type="text" class="form-control" id="nombreComun" name="nombreComun"
                           placeholder="Ej: Frailejón" required>
                </div>
                <div class="form-group">
                    <label for="altitud">Altitud (m.s.n.m.) *</label>
                    <input type="number" class="form-control" id="altitud" name="altitud"
                           placeholder="Ej: 3800" min="0" max="6000" required>
                </div>
                <div class="form-group">
                    <label for="estado">Estado de Conservación *</label>
                    <select id="estado" class="form-control" name="estado" required>
                        <option value="">-- Seleccionar --</option>
                        <option value="Vulnerable">Vulnerable</option>
                        <option value="Amenazada">Amenazada</option>
                        <option value="En peligro">En peligro</option>
                        <option value="Extinta en la naturaleza">Extinta en la naturaleza</option>
                        <option value="Preocupación menor">Preocupación menor</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="ubicacion">Ubicación *</label>
                <input type="text" class="form-control" id="ubicacion" name="ubicacion"
                       placeholder="Ej: Páramo de Pasto, Nariño" required>
            </div>

            <div style="display:flex;gap:1rem;margin-top:.5rem">
                <button type="submit" class="btn btn-verde">Guardar Especie</button>
                <a href="${pageContext.request.contextPath}/especies" class="btn btn-claro">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
