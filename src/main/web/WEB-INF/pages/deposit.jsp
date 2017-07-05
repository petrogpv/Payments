<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Replenish card"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>Replenish card</p></div>

    <form method="post" action="/user/deposit">
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>Select card: </b></p></label></div>
            <div class=" col-xs-4">
                <select class="selectpicker form-control form-group" required name="card">
                    <option value="" disabled selected>select card</option>
                    <c:forEach items="${cards}" var="card">
                        <option
                                <c:if test="${not empty param.cardFrom && param.cardFrom eq card.id}">
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
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>Value: </b></p></label></div>
            <div class="col-xs-4"><input type="number" step="0.01" class="form-control" name="value" required/></div>
        </div>
        <div class="row">
            <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary" value="Replenish"></div>
        </div>
    </form>
</div>
</body>
</html>

