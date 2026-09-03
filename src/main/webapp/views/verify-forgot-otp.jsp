<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác thực OTP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <h2>Xác thực OTP</h2>
        <p class="auth-description">Mã OTP đã được gửi đến ${email}. Mã có hiệu lực trong 5 phút.</p>
        <c:if test="${not empty alert}">
            <div class="alert alert-error">${alert}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/verify-forgot-otp" method="post">
            <div class="form-group">
                <label for="otp">Mã OTP</label>
                <input type="text" id="otp" name="otp" placeholder="Nhập 6 chữ số OTP" maxlength="6" inputmode="numeric" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Xác nhận OTP</button>
        </form>
        <div class="auth-footer">
            <a href="${pageContext.request.contextPath}/forgot-password">Gửi yêu cầu lại</a>
        </div>
    </div>
</div>
</body>
</html>