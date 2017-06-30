<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container" style="width: 300px;">

    <c:if test="${not empty wrongUsername}">
        <div class="error">  <fmt:message key="login.wrong.username" bundle="${bundle}"/> - ${wrongUsername}</div>
    </c:if>
    <c:if test="${not empty wrongPassword}">
        <div class="error">  <fmt:message key="login.wrong.password" bundle="${bundle}"/>- ${wrongPassword}</div>
    </c:if>

    <%@include file = "/WEB-INF/pages/common/message.jsp"%>

    <h4 class="form-signin-heading"> <fmt:message key="login.greating" bundle="${bundle}"/></h4>
    <form action="/auth/login" method="post">

        <input type="text" class="form-control" name="username" placeholder=<fmt:message key="login.email" bundle="${bundle}"/> required >
        <input type="password" class="form-control" name="password" placeholder=<fmt:message key="login.pass" bundle="${bundle}"/> required >
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.logIn" bundle="${bundle}"/></button>
    </form>
    <a href="/register" class="lnk"><fmt:message key="app.signup" bundle="${bundle}"/></a>
</div>

</body>
</html>