<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Lock card"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>Lock card</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>
    <form method="post" action="/user/lock">
        <div class="row">
            <div class=" col-xs-4">
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
                                value="${card.id}">${card.id}
                            (<custom:convertLong value="${card.account.balance}"/>) -
                            <fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div><input type="submit" class="btn btn-primary" value="Lock"></div>
        </div>
    </form>
</div>
</body>
</html>
