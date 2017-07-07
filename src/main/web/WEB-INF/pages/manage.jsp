<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Manage"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <div class="header-text"><p>Manage</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <div class="description-text"><p>Notice: Search info first to manage</p></div>
    <form method="post" action="/admin/manage/action">
        <p><b>Choose search type:</b></p>
        <p><input type="radio" name="searchType" value="cardByNumber" checked>card by card number</p>
        <p><input type="radio" name="searchType" value="cardByLogin"
        <c:if test="${not empty param.searchType && param.searchType eq 'cardByLogin'}">
                  checked
        </c:if>
        >card by user login (e-mail)</p>
        <p><input type="radio" name="searchType" value="userByLogin"
        <c:if test="${not empty param.searchType && param.searchType eq 'userByLogin'}">
                  checked
        </c:if>
        >user by user login (e-mail) </p>
        <label><p><b>Search parameter:</b></p></label>
        <div class="row">
            <div class=" col-xs-3"><input type="text" class="form-control" required name="searchParameter"
                    <c:if test="${not empty param.searchParameter}"> value="${param.searchParameter}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>
    </form>

    <form method="post" action="/admin/manage/action">
        <div class="row">
            <%--<input type="text" hidden name="searchType" value="usersWithoutTransactions">--%>
            <div class=" col-xs-3"><label><p><b>Search users without transactions:</b></p></label></div>
            <div><input type="submit" class="btn btn-primary" name="searchWithoutTransactions" value="Search"></div>
        </div>

    </form>

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
                <td><custom:convertLong value="${c.account.balance}"/></td>
                <td><fmt:message key="card.status.${c.status}" bundle="${bundleEnums}"/></td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</c:if>


</div>
</body>
</html>