<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp"/>
<div class="dashboard-layout">
    <aside class="sidebar">
        <div class="sidebar-profile">
            <c:choose>
                <c:when test="${not empty sessionScope.account.avatar}">
                    <c:url value="/image" var="userAvatarUrl">
                        <c:param name="fname" value="${sessionScope.account.avatar}"/>
                    </c:url>
                    <img class="sidebar-avatar sidebar-avatar-image" src="${userAvatarUrl}" alt="Avatar của ${sessionScope.account.fullName}">
                </c:when>
                <c:otherwise>
                    <div class="sidebar-avatar">
                        <c:choose>
                            <c:when test="${not empty sessionScope.account.fullName}">
                                ${sessionScope.account.fullName.substring(0,1)}
                            </c:when>
                            <c:otherwise>
                                U
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="sidebar-profile-name">${sessionScope.account.fullName}</div>
        </div>
        <div class="sidebar-divider"></div>
        <div class="sidebar-title">MENU</div>
        <c:if test="${sessionScope.account.roleid == 1}">
            <a href="${pageContext.request.contextPath}/admin/category/list" class="sidebar-item">
                <span class="sidebar-icon">▦</span>
                <span>Quản lý danh mục</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/product/list" class="sidebar-item">
                <span class="sidebar-icon">▣</span>
                <span>Quản lý sản phẩm</span>
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/product" class="sidebar-item">
            <span class="sidebar-icon">◫</span>
            <span>Tất cả sản phẩm</span>
        </a>
        <a href="${pageContext.request.contextPath}/member/myaccount" class="sidebar-item">
            <span class="sidebar-icon">♙</span>
            <span>Thông tin cá nhân</span>
        </a>
    </aside>
    <main class="dashboard-content">
        <div class="page-header">
            <h1>10 sản phẩm mới nhất</h1>
            <p>Các sản phẩm được thêm gần đây nhất vào hệ thống.</p>
        </div>
        <div class="card">
            <div class="category-toolbar">
                <div></div>
                <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Xem tất cả sản phẩm</a>
            </div>
            <div class="table-wrapper">
                <table class="category-table">
                    <thead>
                        <tr>
                            <th>Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Danh mục</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${latestProducts}" var="product">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty product.image}">
                                            <c:url value="/image" var="imgUrl">
                                                <c:param name="fname" value="${product.image}"/>
                                            </c:url>
                                            <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">
                                                <img class="category-image" src="${imgUrl}" alt="${product.name}">
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="image-placeholder">Không có ảnh</div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">
                                        <strong>${product.name}</strong>
                                    </a>
                                </td>
                                <td>${product.price} VNĐ</td>
                                <td>${product.category.name}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty latestProducts}">
                            <tr>
                                <td colspan="4" class="empty-state">Chưa có sản phẩm nào.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>