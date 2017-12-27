<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<style>

    .mycontainer{
        display: flex;
        justify-content: space-around;
    }
    .MenuContainer{
        background-color: #b3d7ff;
        border-top: 1px solid #007bff;
        border-bottom: 1px solid #007bff;
        text-align: right;
    }
    .MenuContainer h2{
        padding-right: 10px;
    }
    .mycontainer td{
        border: 1px solid #000000;
        padding: 10px;
    }
    .mycontainer1 h1{
        text-align: center;
    }

</style>
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

<div>
    <h2>Welcome ${pageContext.request.userPrincipal.name} | <a href="/logout">Logout</a></h2>
</div>
<br/>
<a href="/projectPage">Back to Project main page</a>

<h3>Current project:   ${currentProject.name}</h3>

<br/>

<div class="mycontainer">
    <div class="mycontainer1">

        <form:form action="/projectData/addTask" commandName="task">
            <table>
                <c:if test="${!empty task.name}">
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
                        <form:label path="taskStatus">
                            <spring:message text="Status"/>
                        </form:label>
                    </td>
                    <td>
                        <form:select path="taskStatus" id="taskStatus" class="form-control input-sm">
                            <form:option value="">Select Status</form:option>
                            <form:options items="${statusList}" />
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td>
                        <form:label path="user">
                            <spring:message text="Developer"/>
                        </form:label>
                    </td>
                    <td>
                        <form:select path="user" id="user" class="form-control input-sm">
                            <form:option value="">Select Developer</form:option>
                            <form:options items="${allExistedUsers}" itemLabel="firstName" itemValue="username"/>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center">
                        <c:if test="${!empty task.name}">
                            <input type="submit"
                                   value="<spring:message text="Edit Task"/>"/>
                        </c:if>
                        <c:if test="${empty task.name}">
                            <input type="submit"
                                   value="<spring:message text="Add Task"/>"/>
                        </c:if>
                    </td>
                </tr>

            </table>
        </form:form>
<br/>

<h3>Task List</h3>
<br/>
        Show my tasks
        <form action="/taskListByUser" method="post">
            <input type="checkbox" name="checkbox1" value="on"/>
            <input type="submit" value="Show"/>
            <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
        </form>
<br/>

<c:if test="${!empty taskList}">
    <table class="table table-bordered">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Status</th>
            <th>User</th>
            <th>Edit</th>
        </tr>
        <c:forEach items="${taskList}" var="task">
            <tr>
                <td>${task.id}</td>
                <td><a href="/taskData/${task.id}">${task.name}</a></td>
                <td>${task.taskStatus}</td>
                <td>${task.user.firstName}</td>
                <td><a href="<c:url value='/editTask/${task.id}'/>">Edit Task</a> </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</div>

    <div class="mycontainer2">

        <form:form action="/projectData/addUser" commandName="userListForm">
            <table>

                <tr>
                    <td>
                        <form:label path="userForProject">
                            <spring:message text="Developer"/>
                        </form:label>
                    </td>
                    <td>
                        <form:select path="userForProject" id="userForProject" class="form-control input-sm">
                            <form:option value="">Select Developer</form:option>
                            <form:options items="${allExistedUsers}" itemLabel="firstName" itemValue="username"/>
                        </form:select>
                        <form:errors path="userForProject"/>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center">
                            <input type="submit"
                                   value="<spring:message text="Add Developer to Project"/>"/>
                    </td>
                </tr>

            </table>
        </form:form>

        <br/>

        <h3>Users of current project: ${currentProject.name}</h3>

        <c:if test="${!empty userListForProject}">
            <table class="table table-bordered">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Second Name</th>
                </tr>
                <c:forEach items="${userListForProject}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>

</body>
</html>
