<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}"/>
<fmt:setBundle basename="/localization/text" var="bundle" scope="session"/>
<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <link href="<c:url value="/resources/css/msg.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/header-login-signup.css">
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Payments</title>
    <%--<c:set var="userRole" value="${loggedUser.userType}"/>--%>
</head>

