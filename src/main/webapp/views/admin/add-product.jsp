<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp" />
<div class="container">
    <div class="page-header">
        <h1>Thêm sản phẩm</h1>
        <p>Tạo một sản phẩm mới trong hệ thống.</p>
    </div>
    <div class="card category-form-card">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Tên sản phẩm</label>
                <input type="text" id="name" name="name" placeholder="Nhập tên sản phẩm" required>
            </div>
            <div class="form-group">
                <label for="price">Giá</label>
                <input type="number" id="price" name="price" min="0" step="0.01" placeholder="Nhập giá sản phẩm" required>
            </div>
            <div class="form-group">
                <label for="categoryId">Danh mục</label>
                <select id="categoryId" name="categoryId" required>
                    <option value="">-- Chọn danh mục --</option>
                    <c:forEach items="${cateList}" var="cate">
                        <option value="${cate.id}">${cate.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="image">Hình ảnh</label>
                <input type="file" id="image" name="image" accept="image/*">
            </div>
            <div class="form-group">
                <label for="description">Mô tả</label>
                <textarea id="description" name="description" rows="5" placeholder="Nhập mô tả sản phẩm"></textarea>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
                <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>