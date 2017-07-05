<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Management"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <div class="btn-group" role="group" aria-label="...">
        <button type="button" onclick="location.href='/admin/management?managementType=manage';"
                class="btn btn-default">Manage users
        </button>
        <button type="button" onclick="location.href='/admin/management?managementType=add';" class="btn btn-default">
            Add user
        </button>
    </div>

    <c:choose>
    <c:when test="${param.managementType eq 'add'}">
        <div class="header-text"><p>Management/Add user</p></div>
        <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
        <%@include file = "/WEB-INF/pages/common/message.jsp"%>
        <form method="post" action="/admin/add">
            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>First name</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="firstName" required/></div>
            </div>

            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>Last name</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="lastName" required/></div>
            </div>

            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>Login (e-mail)</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="login" required/></div>
            </div>
            <div class="row">
                <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary" value="Save"></div>
            </div>
        </form>
    </c:when>
    <c:otherwise>
    <div class="header-text"><p>Management/Manage users</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <div class="description-text"><p>Notice: Search info first to manage</p></div>
    <form method="post" action="/admin/manage">
        <p><b>Choose search type:</b></p>
        <p><input type="radio" name="searchType" value="cardNumber" checked>card by card number</p>
        <p><input type="radio" name="searchType" value="login">user by user login (e-mail) </p>
        <label><p><b>Search parameter:</b></p></label>
        <div class="row">
            <div class=" col-xs-3"><input type="text" class="form-control" required name="searchParameter"/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>
    </form>

    <form method="post" action="/admin/manage">
        <div class="row">
            <input type="text" hidden name="searchType" value="usersWithoutTransactions">
            <div class=" col-xs-3"><label><p><b>Search users without transactions:</b></p></label></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>

    </form>
</div>
</c:otherwise>
</c:choose>
</div>
</body>
</html>

