
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="New payment"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>New payment</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <form method="post" action="/user/payment/action">
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>Select card from: </b></p></label></div>
            <div class=" col-xs-4">
                <select class="selectpicker form-control form-group" required name="cardFrom">
                    <option value="" disabled selected>select card</option>
                    <c:forEach items="${cards}" var="card">
                        <option
                                <c:if test="${not empty param.cardFrom && param.cardFrom eq card.id}">
                                    selected
                                </c:if>
                                <c:if test="${card.status ne 'ACTIVE'}">
                                    disabled
                                </c:if>
                                value="${card.id}">${card.id}
                            (<custom:formatMoney value="${card.account.balance}"/>) -
                            <fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>Enter card to: </b></p></label></div>
            <div class="col-xs-4"><input type="number" class="form-control" name="cardTo" required
                    <c:if test="${not empty param.cardTo}"> value="${param.cardTo}" </c:if>/></div>
        </div>

        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>Value: </b></p></label></div>
            <div class="col-xs-4"><input type="text" step="0.01" class="form-control" name="value" required
                    <c:if test="${not empty param.value}"> value="${param.value}" </c:if>/></div>
        </div>
        <div class="row">
            <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary" value="Pay"></div>
        </div>
    </form>

</div>
</body>
</html>
