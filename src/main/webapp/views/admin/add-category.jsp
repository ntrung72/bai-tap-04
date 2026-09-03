<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm danh mục</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/decorator/web/topbar.jsp" />
<div class="container">
    <div class="page-header">
        <h1>Thêm danh mục</h1>
        <p>Tạo một danh mục mới trong hệ thống.</p>
    </div>
    <div class="card category-form-card">
        <form action="${pageContext.request.contextPath}/admin/category/add" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Tên danh mục</label>
                <input type="text" id="name" name="name" placeholder="Nhập tên danh mục" required>
            </div>
            <div class="form-group">
                <label for="icon">Ảnh đại diện</label>
                <input type="file" id="icon" name="icon" accept="image/*">
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Thêm danh mục</button>
                <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>