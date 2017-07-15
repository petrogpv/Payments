<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="lock.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <form method="post" action="/user/lock">
        <div class="row">
            <div class=" col-xs-5 col-md-4">
                <select class="selectpicker form-control form-group" required name="card">
                    <option value="" disabled selected>
                        <fmt:message key="app.label.selectCard" bundle="${bundle}"/>
                    </option>
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
            <div><input type="submit" class="btn btn-primary"
                        value="<fmt:message key="app.button.lock" bundle="${bundle}"/>"></div>
        </div>
    </form>
</div>
</body>
</html>
