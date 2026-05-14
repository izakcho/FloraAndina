<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error – FloraAndina</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-card" style="text-align:center">
        <div class="logo">
            <h2>Error</h2>
            <p>Algo salió mal</p>
        </div>
        <p style="color:#666;margin:1rem 0 1.5rem;font-size:.95rem">
            La página que buscas no existe o ocurrió un error en el servidor.
        </p>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-verde">Ir al inicio</a>
    </div>
</div>
</body>
</html>
