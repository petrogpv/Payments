<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Search"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>Search/Unlock</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>

    <form method="post" action="/admin/search_unlock/action">
        <p><b>Choose search type:</b></p>
        <p><input type="radio"  name="searchType" value="card" checked>by card number</p>
        <p><input type="radio"  name="searchType" value="login"
        <c:if test="${not empty param.searchType && param.searchType eq 'login'}">
                  checked
        </c:if>
        >by user login (e-mail) </p>
        <label><p><b>Search parameter:</b></p></label>
        <div class="row">
            <div class=" col-xs-3"> <input type="text" class="form-control" required name="searchParameter"
                    <c:if test="${not empty param.searchParameter}"> value="${param.searchParameter}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>
    </form>


    <c:if test="${not empty transactions}">
        <table class="table table-striped">
            <th>
                <tr>
                    <td>#</td>
                    <td><b>Login</b></td>
                    <td><b>First name</b></td>
                    <td><b>Last name</b></td>
                    <td><b>Value</b></td>
                    <td><b>Status</b></td>
                </tr>
            </th>
            <c:forEach items="${transactions}" var="t">
                <tr>
                    <td>${t.id}</td>
                    <td> <fmt:message key="transaction.type.${t.type}" bundle="${bundleEnums}"/></td>
                    <td>${t.relativeTransaction.card.id}</td>
                    <td><custom:convertLong value="${t.balanceAfter}"/></td>
                    <td><custom:convertLong value="${t.value}"/></td>
                    <td><fmt:formatDate type = "both" value = "${t.date}" /></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


</div>
</body>
</html>