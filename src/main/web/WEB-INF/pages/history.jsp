<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="history.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <form method="post" action="/history/action">
        <div class="header-text"><p>${title}</p></div>

        <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
        <%@include file="/WEB-INF/pages/common/message.jsp" %>

        <c:choose>
            <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
                <div class="row">
                    <div class=" col-xs-5 col-md-4 col-xs-offset-2">
                        <select class="selectpicker form-control form-group" required name="card">
                            <option value="" disabled selected>
                                <fmt:message key="app.label.selectCard" bundle="${bundle}"/>
                            </option>
                            <c:forEach items="${cards}" var="card">
                                <option
                                        <c:if test="${not empty param.card && param.card eq card.id}">
                                            selected
                                        </c:if>
                                        value="${card.id}">${card.id}
                                    (<custom:formatMoney value="${card.account.balance}"/>) -
                                    <fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:when>
            <c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
                <div class="row">
                    <div class=" col-xs-2"><label class="label-input"><p><b>
                        <fmt:message key="history.label.cardNumber" bundle="${bundle}"/>
                    </b></p></label></div>
                    <div class=" col-xs-4 col-md-3"><input type="number" class="form-control no-spin"
                                                           required name="card" max="550599999999"
                            <c:if test="${not empty param.card}"> value="${param.card}" </c:if>/></div>
                </div>
            </c:when>
        </c:choose>

        <div class="row">
            <div class=" col-xs-2"><label class="label-input">
                <p><b><fmt:message key="history.label.dates" bundle="${bundle}"/></b></p>
            </label></div>
            <div class=" col-xs-3 col-md-2">
                <div><input type="text" class="form-control"
                            placeholder="<fmt:message key="history.label.dateFrom" bundle="${bundle}"/>" name="dateFrom"
                        <c:if test="${not empty param.dateFrom}"> value="${param.dateFrom}" </c:if>/></div>
                <div><input type="text" class="form-control"
                            placeholder="<fmt:message key="history.label.dateTo" bundle="${bundle}"/>" name="dateTo"
                        <c:if test="${not empty param.dateTo}"> value="${param.dateTo}" </c:if>/></div>
            </div>
        </div>
        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="history.label.transactionsType" bundle="${bundle}"/></b></p></label></div>
            <div class=" col-xs-4 col-md-3">
                <div><p><input type="radio" name="transactionType" value="ALL" checked>
                    <fmt:message key="history.label.all" bundle="${bundle}"/></p></div>
                <div><p><input type="radio" name="transactionType" value="INCOME"
                <c:if test="${not empty param.transactionType && param.transactionType eq 'INCOME'}">
                               checked
                </c:if>
                ><fmt:message key="history.label.income" bundle="${bundle}"/></p></div>
                <div><p><input type="radio" name="transactionType" value="OUTCOME"
                <c:if test="${not empty param.transactionType && param.transactionType eq 'OUTCOME'}">
                               checked
                </c:if>
                ><fmt:message key="history.label.outcome" bundle="${bundle}"/></p>
                </div>
                <div><p><input type="radio" name="transactionType" value="DEPOSIT"
                <c:if test="${not empty param.transactionType && param.transactionType eq 'DEPOSIT'}">
                               checked
                </c:if>
                ><fmt:message key="history.label.deposit" bundle="${bundle}"/>
                </p></div>
            </div>
        </div>
        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="history.label.sortByDate" bundle="${bundle}"/></b></p></label></div>
            <div class=" col-xs-4 col-md-3">
                <div><p><input type="radio" name="sortType" value="DESC" checked>
                    <fmt:message key="history.label.descending" bundle="${bundle}"/></p></div>
                <div><p><input type="radio" name="sortType" value="ASC"
                <c:if test="${not empty param.sortType && param.sortType eq 'ASC'}">
                               checked
                </c:if>
                ><fmt:message key="history.label.ascending" bundle="${bundle}"/></p></div>
            </div>
        </div>
        <div><input type="submit" class="btn btn-primary"
                    value="<fmt:message key="app.button.search" bundle="${bundle}"/>"></div>
    </form>
    <c:if test="${not empty transactions}">
        <table class="table table-striped">
            <tr>
                <th>#</th>
                <th><fmt:message key="history.table.transactionType" bundle="${bundle}"/></th>
                <th><fmt:message key="history.table.fromTo" bundle="${bundle}"/></th>
                <th><fmt:message key="history.table.balance" bundle="${bundle}"/></th>
                <th><fmt:message key="history.table.value" bundle="${bundle}"/></th>
                <th><fmt:message key="history.table.date" bundle="${bundle}"/></th>
            </tr>
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