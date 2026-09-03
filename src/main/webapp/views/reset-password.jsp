<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <h2>Đặt lại mật khẩu</h2>
        <p class="auth-description">Nhập mật khẩu mới cho tài khoản của bạn</p>
        <c:if test="${not empty alert}">
            <div class="alert alert-error">${alert}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <div class="form-group">
                <label for="password">Mật khẩu mới</label>
                <input type="password" id="password" name="password" placeholder="Nhập mật khẩu mới" required>
            </div>
            <div class="form-group">
                <label for="repassword">Nhập lại mật khẩu mới</label>
                <input type="password" id="repassword" name="repassword" placeholder="Nhập lại mật khẩu mới" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Đổi mật khẩu</button>
        </form>
    </div>
</div>
</body>
</html>