<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-card">
        <div class="logo">
            <h2>FloraAndina</h2>
            <p>Crear nueva cuenta</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/registro">
            <div class="form-group">
                <label for="nombre">Nombre completo</label>
                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Tu nombre" required autofocus>
            </div>
            <div class="form-group">
                <label for="correo">Correo electrónico</label>
                <input type="email" class="form-control" id="correo" name="correo" placeholder="ejemplo@flora.co" required>
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Mínimo 4 caracteres" required>
            </div>
            <div class="form-group">
                <label for="rol">Rol</label>
                <select id="rol" class="form-control" name="rol" required>
                    <option value="">-- Selecciona un rol --</option>
                    <option value="Administrador">Administrador</option>
                    <option value="Investigador">Investigador</option>
                    <option value="Visitante">Visitante</option>
                </select>
            </div>
            <button type="submit" class="btn btn-verde" style="width:100%;margin-top:.5rem">Registrarse</button>
        </form>

        <div class="footer-link">
            ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
        </div>
    </div>
</div>
</body>
</html>
