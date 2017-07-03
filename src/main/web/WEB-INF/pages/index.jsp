<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container" style="width: 300px;">

    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>

    <%--<%@include file = "/WEB-INF/pages/common/message.jsp"%>--%>

    <h4 class="form-signin-heading"> <fmt:message key="signin.greating" bundle="${bundle}"/></h4>
    <form action="/auth/signin" method="post">

        <input type="text" class="form-control" name="username" placeholder=<fmt:message key="signin.email" bundle="${bundle}"/> required >
        <input type="password" class="form-control" name="password" placeholder=<fmt:message key="signin.pass" bundle="${bundle}"/> required >
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="app.signin" bundle="${bundle}"/></button>
    </form>
    <a href="/auth/getsignup" class="lnk"><fmt:message key="app.signup" bundle="${bundle}"/></a>
</div>
<h1>Request URI: ${pageContext.request.requestURI}</h1>
</body>
</html>