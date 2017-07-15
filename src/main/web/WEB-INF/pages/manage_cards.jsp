<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="manage.cards.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <div class="description-text"><p>
        <fmt:message key="app.search.notice" bundle="${bundle}"/>
    </p></div>
    <form method="post" action="/admin/manage/cards/action">
        <p><b><fmt:message key="app.label.chooseSearch" bundle="${bundle}"/></b></p>
        <p><input type="radio" name="searchType" value="cardByNumber" checked>
            <fmt:message key="manage.cards.label.cardByNumber" bundle="${bundle}"/></p>
        <p><input type="radio" name="searchType" value="cardByLogin"
        <c:if test="${not empty param.searchType && param.searchType eq 'cardByLogin'}">
                  checked
        </c:if>
        ><fmt:message key="manage.cards.label.cardByLogin" bundle="${bundle}"/></p>

        <label><p><b><fmt:message key="app.label.searchParam" bundle="${bundle}"/></b></p></label>
        <div class="row">
            <div class=" col-xs-4 col-md-3"><input type="text" class="form-control" required name="searchParameter"
                    <c:if test="${not empty param.searchParameter}"> value="${param.searchParameter}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary" value="
            <fmt:message key="app.button.search" bundle="${bundle}"/>"></div>
        </div>
    </form>

    <c:if test="${not empty cards}">
        <table class="table table-striped">
            <tr>
                <th>#</th>
                <th><fmt:message key="app.table.login" bundle="${bundle}"/></th>
                <th><fmt:message key="app.table.firstName" bundle="${bundle}"/></th>
                <th><fmt:message key="app.table.lastName" bundle="${bundle}"/></th>
                <th><fmt:message key="app.table.value" bundle="${bundle}"/></th>
                <th><fmt:message key="manage.cards.table.status" bundle="${bundle}"/></th>
                <th><fmt:message key="manage.cards.table.action" bundle="${bundle}"/></th>
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
                                <input type="hidden" name="card" value="${c.id}">
                                <input type="submit" name="action"
                                       value="<fmt:message key="manage.cards.button.unlock" bundle="${bundle}"/>">
                                <input type="submit" name="action"
                                       value="<fmt:message key="manage.cards.button.deactivate" bundle="${bundle}"/>">
                            </form>
                        </c:if>
                        <c:if test="${c.status eq 'ACTIVE'}">
                            <form method="post" action="/admin/manage/cards/change">
                                <input type="hidden" name="card" value="${c.id}">
                                <input type="submit" name="action"
                                       value="<fmt:message key="manage.cards.button.lock" bundle="${bundle}"/>">
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