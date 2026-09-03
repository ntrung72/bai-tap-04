<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp" />
<div class="container">
    <div class="page-header">
        <h1>Sửa sản phẩm</h1>
        <p>Cập nhật thông tin sản phẩm.</p>
    </div>
    <div class="card category-form-card">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/admin/product/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}">
            <div class="form-group">
                <label for="name">Tên sản phẩm</label>
                <input type="text" id="name" name="name" value="${product.name}" required>
            </div>
            <div class="form-group">
                <label for="price">Giá</label>
                <input type="number" id="price" name="price" min="0" step="0.01" value="${product.price}" required>
            </div>
            <div class="form-group">
                <label for="categoryId">Danh mục</label>
                <select id="categoryId" name="categoryId" required>
                    <c:forEach items="${cateList}" var="cate">
                        <option value="${cate.id}" ${cate.id==product.category.id?'selected':''}>${cate.name}</option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty product.image}">
                <c:url value="/image" var="imgUrl">
                    <c:param name="fname" value="${product.image}" />
                </c:url>
                <div class="form-group">
                    <label>Ảnh hiện tại</label>
                    <div class="current-image-box">
                        <img class="category-edit-image" src="${imgUrl}" alt="${product.name}">
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <label for="image">Chọn ảnh mới</label>
                <input type="file" id="image" name="image" accept="image/*">
                <p class="form-help">Nếu không chọn ảnh mới, hệ thống sẽ giữ nguyên ảnh hiện tại.</p>
            </div>
            <div class="form-group">
                <label for="description">Mô tả</label>
                <textarea id="description" name="description" rows="5">${product.description}</textarea>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>