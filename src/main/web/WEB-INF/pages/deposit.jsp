<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="deposit.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <form method="post" action="/user/deposit/action">
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="deposit.label.selectCard" bundle="${bundle}"/></b></p></label></div>
            <div class=" col-xs-5 col-md-4">
                <select class="selectpicker form-control form-group" required name="card">
                    <option value="" disabled selected>
                        <fmt:message key="app.label.selectCard" bundle="${bundle}"/></option>
                    <c:forEach items="${cards}" var="card">
                        <option
                                <c:if test="${not empty param.card && param.card eq card.id}">
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
            <div class="col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="deposit.label.value" bundle="${bundle}"/></b></p></label></div>
            <div class="col-xs-3 col-md-2"><input type="text" step="0.01" class="form-control" name="value" required
                    <c:if test="${not empty param.value}"> value="${param.value}" </c:if>/></div>
        </div>
        <div class="row">
            <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary"
                                                         value="<fmt:message key="deposit.button.replenish" bundle="${bundle}"/>">
            </div>
        </div>
    </form>
</div>
</body>
</html>

