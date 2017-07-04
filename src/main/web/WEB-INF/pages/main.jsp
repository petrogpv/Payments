<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <span class="label label-info"><fmt:message key="main.button.menu" bundle="${bundle}"/></span>

    <c:choose>
    <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
    <div class="btn-group btn-group-justified" role="group">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/payment';">
                <fmt:message key="main.button.payment" bundle="${bundle}"/></button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/deposit';">
                <fmt:message key="main.button.deposit" bundle="${bundle}"/></button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/history';">
                <fmt:message key="main.button.history" bundle="${bundle}"/></button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/lock';">
                <fmt:message key="main.button.lock" bundle="${bundle}"/></button>
        </div>
    </div>
</div>
</c:when>
<c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
    <div class="btn-group btn-group-justified" role="group" aria-label="...">

        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/search_admin';">
                <fmt:message key="main.button.search" bundle="${bundle}"/></button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/history_admin';">
                <fmt:message key="main.button.history" bundle="${bundle}"/></button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" onclick="location.href='/user/management';">
                <fmt:message key="main.button.management" bundle="${bundle}"/></button>
        </div>
    </div>
</c:when>
</c:choose>


<div class="container">
    <h3><fmt:message key="main.pleaseChoose" bundle="${bundle}"/></h3>
</div>


</body>