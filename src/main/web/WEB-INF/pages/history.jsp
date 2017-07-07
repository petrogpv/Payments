<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="History"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <form method="post" action="/history/action">
        <div class="header-text"><p>History</p></div>
        <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
        <%@include file = "/WEB-INF/pages/common/message.jsp"%>
        <c:choose>
            <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
                <div class="row">
                    <div class=" col-xs-4 col-xs-offset-2">
                        <select class="selectpicker form-control form-group" required name="card">
                            <option value="" disabled selected>select card</option>
                            <c:forEach items="${cards}" var="card">
                                <option
                                        <c:if test="${not empty param.card && param.card eq card.id}">
                                            selected
                                        </c:if>
                                        value="${card.id}">${card.id}
                                    (<custom:convertLong value="${card.account.balance}"/>) -
                                    <fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </c:when>
            <c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
                <div class="row">
                    <div class=" col-xs-2"><label class="label-input"><p><b>Card number:</b></p></label></div>
                    <div class=" col-xs-2"><input type="number" class="form-control" required name="card"
                            <c:if test="${not empty param.card}"> value="${param.card}" </c:if>/></div>

                </div>
            </c:when>
        </c:choose>

        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>Dates (or leave blank): example - 15.07.2012</b></p>
            </label></div>
            <div class=" col-xs-2">
                <div><input type="text" class="form-control" placeholder="date from" name="dateFrom"
                        <c:if test="${not empty param.dateFrom}"> value="${param.dateFrom}" </c:if>/></div>
                <div><input type="text" class="form-control" placeholder="date to" name="dateTo"
                        <c:if test="${not empty param.dateTo}"> value="${param.dateTo}" </c:if>/></div>
            </div>

        </div>

        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>Transactions type:</b></p></label></div>
            <div class=" col-xs-2">
                <div><p><input type="radio" name="transactionType" value="ALL" checked>all</p></div>
            <div><p><input type="radio" name="transactionType" value="INCOME"
            <c:if test="${not empty param.transactionType && param.transactionType eq 'INCOME'}">
                           checked
            </c:if>
            >income</p></div>
            <div><p><input type="radio" name="transactionType" value="OUTCOME"
            <c:if test="${not empty param.transactionType && param.transactionType eq 'OUTCOME'}">
                           checked
            </c:if>
            >outcome</p>
            </div>
            <div><p><input type="radio" name="transactionType" value="DEPOSIT"
            <c:if test="${not empty param.transactionType && param.transactionType eq 'DEPOSIT'}">
                           checked
            </c:if>
            >replenishment
            </p></div>
            </div>
        </div>
        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>Sort by date::</b></p></label></div>
            <div class=" col-xs-2">
                <div><p><input type="radio" name="sortType" value="DESC" checked>descending</p></div>
                <div><p><input type="radio" name="sortType" value="ASC"
                <c:if test="${not empty param.sortType && param.sortType eq 'ASC'}">
                               checked
                </c:if>
                >ascending</p></div>
            </div>
        </div>
        <div><input type="submit" class="btn btn-primary" value="Search"></div>
    </form>

<c:if test="${not empty transactions}">
    <table class="table table-striped">
        <tr>
            <th>#</th>
            <th>Transaction type</th>
            <th>To/from card</th>
            <th>Balance</th>
            <th>Value</th>
            <th>Date</th>
        </tr>
        <c:forEach items="${transactions}" var="t">
        <tr>
        <td>${t.id}</td>
        <td><fmt:message key="transaction.type.${t.type}" bundle="${bundleEnums}"/></td>
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