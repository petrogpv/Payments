<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Manage users"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <div class="header-text"><p>Manage users</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <div class="description-text"><p>Notice: Search info first to manage</p></div>
    <form method="post" action="/admin/manage/users/action">
        <label><p><b>Search by login(e-mail):</b></p></label>
        <div class="row">
            <div class=" col-xs-3"><input type="text" class="form-control" required name="login"
                    <c:if test="${not empty param.login}"> value="${param.login}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>
    </form>
    
    <a class="btn btn-primary" href="/admin/add_user"><fmt:message key="main.button.add" bundle="${bundle}"/></a>

    <c:if test="${not empty cards}">
        <table class="table table-striped">
            <tr>
                <th>#</th>
                <th>User login</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Value</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${cards}" var="c">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.user.userAuth.login}</td>
                    <td>${c.user.firstName}</td>
                    <td>${c.user.lastName}</td>
                    <td><custom:formatMoney value="${c.account.balance}"/></td>
                    <td><fmt:message key="card.status.${c.status}" bundle="${bundleEnums}"/></td>
                    <td></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


</div>
</body>
</html>

