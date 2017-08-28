<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Table -->
<table class="table" style="display: inline-block;">
    <tr>
        <th>Header Name</th>
    </tr>
    <c:forEach var="head" items="${headersok}">
        <tr>
            <td>${head.key}</td>
        </tr>
    </c:forEach>
</table>
<table class="table" style="display: inline-block;">
    <tr>
        <th>Null Values In Column</th>
    </tr>
    <c:forEach var="nullVals" items="${nullListVals}">
        <tr>
            <td>${nullVals}</td>
        </tr>
    </c:forEach>
</table>
<table class="table" style="display: inline-block;">
    <tr>
        <th>Suggested Data Type</th>
    </tr>
    <c:forEach var="dataType" items="${suggestedSchema}">
        <tr>
            <td>${dataType}</td>
        </tr>
    </c:forEach>
</table>

<h2>Total amount of data rows: ${lines}</h2>
