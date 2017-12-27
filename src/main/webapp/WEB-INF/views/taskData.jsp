<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>

<a href="/projectPage">Back to Project main page</a>
<br/>
<br/>
<a href="/projectData">Back to Task main page</a>
<br/>
<br/>
Current project: <c:out value="${sessionScope.currentProject.name}"/>
<br/>
<br/>
Current task: <c:out value="${sessionScope.currentTask.name}"/>
<br/>
<br/>

<form:form action="/commentData/addComment" commandName="comment">
    <table>
        <c:if test="${!empty comment.text}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>

        <tr>
            <td>
                <form:label path="text">
                    <spring:message text="Comment"/>
                </form:label>
            </td>
            <td>
                <form:input type="textarea" row="3" maxlength="255" size="50" required="true" wrap="soft" path="text"/>
                <div class="has-error">
                    <form:errors path="text" class="help-inline"/>
                </div>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <c:if test="${!empty comment.text}">
                    <input type="submit"
                           value="<spring:message text="Edit comment"/>"/>
                </c:if>
                <c:if test="${empty comment.text}">
                    <input type="submit"
                           value="<spring:message text="Add comment"/>"/>
                </c:if>
            </td>
        </tr>

    </table>
</form:form>

<h1>Comment List</h1>

<c:if test="${!empty commentList}">
    <table class="table table-bordered" style="table-layout: fixed; border-color: #000000">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Text</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${commentList}" var="comment">
            <tr>
                <td>${comment.id}</td>
                <td>Comment${comment.id}</td>
                <td style="width: 20ch; overflow: hidden"><a href="/commentData/${comment.id}">${comment.text}</a></td>
                <td><a href="<c:url value='/edit/${comment.id}'/>">Edit</a> </td>
                <td><a href="<c:url value='/delete/${comment.id}'/>">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
