<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><tiles:insertAttribute name="title" /></title>
</head>
<body>
<tiles:importAttribute name="currentPage" scope="request" />

<!-- Main -->
<tiles:insertAttribute name="content" />

</body>
</html>
