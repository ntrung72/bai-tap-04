<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <h2>Tạo tài khoản mới</h2>
        <p class="auth-description">Điền thông tin bên dưới để đăng ký tài khoản</p>
        <c:if test="${alert != null}">
            <div class="alert alert-error">${alert}</div>
        </c:if>
        <form action="register" method="post">
            <div class="form-group">
                <label for="username">Tài khoản</label>
                <input type="text" id="username" placeholder="Nhập tài khoản" name="username" required>
            </div>
            <div class="form-group">
                <label for="fullname">Họ tên</label>
                <input type="text" id="fullname" placeholder="Nhập họ tên" name="fullname" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" placeholder="Nhập email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <input type="text" id="phone" placeholder="Nhập số điện thoại" name="phone" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" placeholder="Nhập mật khẩu" name="password" required>
            </div>
            <div class="form-group">
                <label for="repassword">Nhập lại mật khẩu</label>
                <input type="password" id="repassword" placeholder="Nhập lại mật khẩu" name="repassword" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Tạo tài khoản</button>
        </form>
        <div class="auth-footer">
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        </div>
    </div>
</div>
</body>
</html>