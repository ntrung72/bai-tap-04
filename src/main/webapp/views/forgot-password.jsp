<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <h2>Quên mật khẩu</h2>
        <p class="auth-description">Nhập email đã đăng ký để nhận mã OTP</p>
        <c:if test="${not empty alert}">
            <div class="alert alert-error">${alert}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Nhập email đã đăng ký" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Gửi mã OTP</button>
        </form>
        <div class="auth-footer">
            <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
        </div>
    </div>
</div>
</body>
</html>