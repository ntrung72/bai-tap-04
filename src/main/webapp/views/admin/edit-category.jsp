<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa danh mục</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp" />
<div class="container">
    <div class="page-header">
        <h1>Sửa danh mục</h1>
        <p>Cập nhật thông tin danh mục.</p>
    </div>
    <div class="card category-form-card">
        <form action="${pageContext.request.contextPath}/admin/category/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${category.id}">
            <div class="form-group">
                <label for="name">Tên danh mục</label>
                <input type="text" id="name" name="name" value="${category.name}" required>
            </div>
            <c:if test="${not empty category.icon}">
                <c:url value="/image" var="imgUrl">
                    <c:param name="fname" value="${category.icon}" />
                </c:url>
                <div class="form-group">
                    <label>Ảnh hiện tại</label>
                    <div class="current-image-box">
                        <img class="category-edit-image" src="${imgUrl}" alt="${category.name}">
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <label for="icon">Chọn ảnh mới</label>
                <input type="file" id="icon" name="icon" accept="image/*">
                <p class="form-help">Nếu không chọn ảnh mới, hệ thống sẽ giữ nguyên ảnh hiện tại.</p>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>