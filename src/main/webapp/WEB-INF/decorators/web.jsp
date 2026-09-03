<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title" default="BaiTap"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <sitemesh:write property="head"/>
</head>
<body>
    <%@ include file="/common/web/header.jsp"%>
    <main>
        <sitemesh:write property="body"/>
    </main>
    <%@ include file="/common/web/footer.jsp"%>
</body>
</html>