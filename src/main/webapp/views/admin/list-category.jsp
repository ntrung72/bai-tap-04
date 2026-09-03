<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý danh mục</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp" />
<div class="container">
    <div class="page-header">
        <h1>Quản lý danh mục</h1>
        <p>Thêm, sửa và xóa các danh mục trong hệ thống.</p>
    </div>
    <c:if test="${not empty sessionScope.categoryDeleteError}">
        <div class="alert alert-error">${sessionScope.categoryDeleteError}</div>
        <c:remove var="categoryDeleteError" scope="session" />
    </c:if>
    <div class="category-toolbar">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/waiting">← Quay về trang chủ</a>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/add">+ Thêm danh mục</a>
    </div>
    <div class="card">
        <div class="table-wrapper">
            <table class="category-table">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Hình ảnh</th>
                        <th>Tên danh mục</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cateList}" var="cate" varStatus="STT">
                        <tr>
                            <td>${STT.index+1}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty cate.icon}">
                                        <c:url value="/image" var="imgUrl">
                                            <c:param name="fname" value="${cate.icon}" />
                                        </c:url>
                                        <img class="category-image" src="${imgUrl}" alt="${cate.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="image-placeholder">Không có ảnh</div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><strong>${cate.name}</strong></td>
                            <td>
                                <div class="action-group">
                                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/category/edit?id=${cate.id}">Sửa</a>
                                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/category/delete?id=${cate.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xóa</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty cateList}">
                        <tr>
                            <td colspan="4" class="empty-state">Chưa có danh mục nào.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>