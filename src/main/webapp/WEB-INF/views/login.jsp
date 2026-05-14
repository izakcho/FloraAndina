<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-card">
        <div class="logo">
            <h2>FloraAndina</h2>
            <p>Sistema de Gestión de Especies Vegetales</p>
        </div>

        <% if (request.getParameter("registrado") != null) { %>
            <div class="alert alert-success">Usuario registrado. Ya puedes iniciar sesión.</div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="correo">Correo electrónico</label>
                <input type="email" class="form-control" id="correo" name="correo" placeholder="ejemplo@flora.co" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-verde" style="width:100%;margin-top:.5rem">Ingresar</button>
        </form>

        <div class="footer-link">
            ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/registro">Regístrate aquí</a>
        </div>
    </div>
</div>
</body>
</html>
