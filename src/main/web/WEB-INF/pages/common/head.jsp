<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/WEB-INF/custom.tld"%>
<fmt:setLocale value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}"/>
<fmt:setBundle basename="/localization/text" var="bundle" scope="session"/>
<fmt:setBundle basename="/localization/entityEnums" var="bundleEnums" scope="session"/>


<%--<fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/>--%>
<%--<fmt:message key="app.signed" bundle="${bundle}"/>--%>

<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/resources/css/header-login-signup.css">
    <%--<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>--%>
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Cookie'>
    <link rel="stylesheet" href="<c:url value="/resources/css/msg.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/custom.css" />">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
</head>

