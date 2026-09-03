<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="topbar">
    <a class="topbar-logo" href="${pageContext.request.contextPath}/waiting">BaiTap</a>
    <div class="topbar-menu">
        <c:choose>
            <c:when test="${sessionScope.account == null}">
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Đăng ký</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/member/myaccount">Profile</a>
                <span class="topbar-welcome">Xin chào, ${sessionScope.account.fullName}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Đăng xuất</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>