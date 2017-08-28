<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Table -->
<table class="example-table" >
    <tr>
        <th>Filename</th>
        <th>Uploader</th>
        <th>Date</th>
    </tr>
    <c:forEach var="doc" items="${documents}">
        <tr>
            <td><a title="Download file" href="${contextPath}/files/${doc.title}">${doc.title}</a></td>
            <td>${doc.uploader}</td>
            <td>${doc.date}</td>
        </tr>
    </c:forEach>
</table>