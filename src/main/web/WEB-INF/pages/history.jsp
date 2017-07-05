<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="History"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

    <form method="post" action="/history">
        <div class="header-text"><p>History</p></div>
        <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
        <%@include file = "/WEB-INF/pages/common/message.jsp"%>
        <c:choose>
            <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
                <div class="row">
                    <div class=" col-xs-5 col-xs-offset-2">
                        <select class="selectpicker form-control form-group" required name="card">
                            <option value="" disabled selected>select card</option>
                            <c:forEach items="${cards}" var="card">
                                <option
                                        <c:if test="${not empty param.card && param.card eq card.id}">
                                            selected
                                        </c:if>
                                        <c:if test="${card.status ne 'ACTIVE'}">
                                            disabled
                                        </c:if>
                                        value="${card.id}">${card.id} (${card.account.balance}) -
                                    <fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </c:when>
            <c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
                <div class="row">
                    <div class=" col-xs-2"><label class="label-input"><p><b>Card number:</b></p></label></div>
                    <div class=" col-xs-2"><input type="number" class="form-control" required name="cardId"/></div>

                </div>
            </c:when>
        </c:choose>

        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>Dates (or leave blank): example - 15.07.2012</b></p>
            </label></div>
            <div class=" col-xs-2">
                <div><input type="text" class="form-control" placeholder="date from" name="dateFrom"/></div>
                <div><input type="text" class="form-control" placeholder="date to" name="dateTo"/></div>
            </div>

        </div>

        <div class="row">
            <div class=" col-xs-2"><label class="label-input"><p><b>Transactions type:</b></p></label></div>
            <div class=" col-xs-2">
                <div><p><input type="radio" name="transactionType" value="all" checked>all</p></div>
            <div><p><input type="radio" name="transactionType" value="income" checked>income</p></div>
            <div><p><input type="radio" name="transactionType" value="outcome" checked>outcome</p>
            </div>
            <div><p><input type="radio" name="transactionType" value="replenishment" checked>replenishment
            </p></div>
            </div>
        </div>
        <div><input type="submit" class="btn btn-primary" value="Search"></div>
    </form>
</div>


</body>
</html>