<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Manage cards"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <div class="header-text"><p>Manage cards</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <div class="description-text"><p>Notice: Search info first to manage</p></div>
    <form method="post" action="/admin/manage/cards/action">
        <p><b>Choose search type:</b></p>
        <p><input type="radio" name="searchType" value="cardByNumber" checked>card by card number</p>
        <p><input type="radio" name="searchType" value="cardByLogin"
        <c:if test="${not empty param.searchType && param.searchType eq 'cardByLogin'}">
                  checked
        </c:if>
        >card by user login (e-mail)</p>

        <label><p><b>Search parameter:</b></p></label>
        <div class="row">
            <div class=" col-xs-3"><input type="text" class="form-control" required name="searchParameter"
                    <c:if test="${not empty param.searchParameter}"> value="${param.searchParameter}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
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
                <td><custom:formatMoney value="${c.account.balance}"/></td>
                <td><fmt:message key="card.status.${c.status}" bundle="${bundleEnums}"/></td>
                <td>
                    <c:if test="${c.status eq 'LOCKED'}">
                        <form method="post" action="/admin/manage/cards/change">
                            <input type="hidden" name="card" value="${c.id}" >
                            <input type="submit" name="action" value="Unlock">
                            <input type="submit" name="action" value="Deactivate">

                        </form>
                    </c:if>
                    <c:if test="${c.status eq 'ACTIVE'}">
                        <form method="post" action="/admin/manage/cards/change">
                            <input type="hidden" name="card" value="${c.id}" >
                            <input type="submit" name="action" value="Lock">

                        </form>
                    </c:if>
                    
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>


</div>
</body>
</html>