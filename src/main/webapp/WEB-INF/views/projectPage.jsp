<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <sec:authorize access="hasRole('ROLE_ADMIN')"></sec:authorize>
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

<div>
    <h2>Welcome ${pageContext.request.userPrincipal.name} | <a href="/logout">Logout</a></h2>
</div>

<br/>
<sec:authorize access="hasRole('ROLE_ADMIN')">

<h3>Add Project</h3>

<c:url var="addProject" value="/projectPage/add" />

<form:form action="${addProject}" commandName="project">
    <table>
        <c:if test="${!empty project.name}">
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
                <form:label path="name">
                    <spring:message text="Name"/>
                </form:label>
            </td>
            <td>
                <form:input path="name"/>
                <div class="has-error">
                    <form:errors path="name" class="help-inline"/>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <c:if test="${empty project.name}">
                    <input type="submit"
                           value="<spring:message text="Add Project"/>"/>
                </c:if>
            </td>
        </tr>

    </table>
</form:form>
</sec:authorize>

<h3>Project List</h3>

<c:if test="${!empty projectList}">
    <table class="table table-bordered border-dark" ">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Number of Tasks</th>
        </tr>
        <c:forEach items="${projectList}" var="project">
            <tr>
                <td>${project.id}</td>
                <td><a href="/projectData/${project.id}">${project.name}</a></td>
                <td>${project.getTaskList().size()}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>