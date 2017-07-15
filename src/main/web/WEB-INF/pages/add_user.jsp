<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="add.title" var="title" bundle="${bundle}"/>
<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <form method="post" action="/admin/add_user/action">
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="add.label.firstName" bundle="${bundle}"/></b></p></label></div>
            <div class="col-xs-4"><input type="text" class="form-control" name="firstName" required
                    <c:if test="${not empty param.firstName}"> value="${param.firstName}" </c:if>/></div>
        </div>
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="add.label.lastName" bundle="${bundle}"/></b></p></label></div>
            <div class="col-xs-4"><input type="text" class="form-control" name="lastName" required
                    <c:if test="${not empty param.lastName}"> value="${param.lastName}" </c:if>/></div>
        </div>
        <div class="row">
            <div class="col-xs-2"><label class="label-input"><p><b>
                <fmt:message key="add.label.login" bundle="${bundle}"/></b></p></label></div>
            <div class="col-xs-4"><input type="text" class="form-control" name="login" required
                    <c:if test="${not empty param.login}"> value="${param.login}" </c:if>/></div>
        </div>
        <div class="row">
            <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary" value="
                <fmt:message key="add.button.save" bundle="${bundle}"/>"></div>
        </div>
    </form>
</div>
</body>
</html>
