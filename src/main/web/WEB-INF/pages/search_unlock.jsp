<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="search.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <form method="post" action="/admin/search_unlock/action">
        <p><b>
            <fmt:message key="app.label.chooseSearch" bundle="${bundle}"/></b></p>
        <p><input type="radio" name="searchType" value="card" checked>
            <fmt:message key="search.label.byCard" bundle="${bundle}"/></p>
        <p><input type="radio" name="searchType" value="login"
        <c:if test="${not empty param.searchType && param.searchType eq 'login'}">
                  checked
        </c:if>
        ><fmt:message key="search.label.byLogin" bundle="${bundle}"/></p>
        <label><p><b>
            <fmt:message key="app.label.searchParam" bundle="${bundle}"/>
        </b></p></label>
        <div class="row">
            <div class=" col-xs-3"><input type="text" class="form-control" required name="searchParameter"
                    <c:if test="${not empty param.searchParameter}"> value="${param.searchParameter}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary"
                        value="<fmt:message key="app.button.search"  bundle="${bundle}"/>"></div>
        </div>
    </form>


    <c:if test="${not empty transactions}">
        <table class="table table-striped">
            <th>
                <tr>
                    <td>#</td>
                    <td><b><fmt:message key="app.table.login" bundle="${bundle}"/></b></td>
                    <td><b><fmt:message key="app.table.firstName" bundle="${bundle}"/></b></td>
                    <td><b><fmt:message key="app.table.lastName" bundle="${bundle}"/></b></td>
                    <td><b><fmt:message key="app.table.value" bundle="${bundle}"/></b></td>
                    <td><b><fmt:message key="app.table.status" bundle="${bundle}"/></b></td>
                </tr>
            </th>
            <c:forEach items="${transactions}" var="t">
                <tr>
                    <td>${t.id}</td>
                    <td><fmt:message key="transaction.type.${t.type}" bundle="${bundleEnums}"/></td>
                    <td>${t.relativeTransaction.card.id}</td>
                    <td><custom:formatMoney value="${t.balanceAfter}"/></td>
                    <td><custom:formatMoney value="${t.value}"/></td>
                    <td><fmt:formatDate type="both" value="${t.date}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>