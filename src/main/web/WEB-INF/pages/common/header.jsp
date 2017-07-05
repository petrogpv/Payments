<header class="container-fluid header-login-signup">

    <div class="header-limiter">

        <h1><fmt:message key="app.appNamePayments" bundle="${bundle}"/>
            <span><fmt:message key="app.appNameSystem" bundle="${bundle}"/></span></h1>
        <nav>
<c:choose>
    <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
            <a href="/user/payment"><fmt:message key="main.button.payment" bundle="${bundle}"/></a>
            <a href="/user/deposit"><fmt:message key="main.button.deposit" bundle="${bundle}"/></a>
            <a href="/history"><fmt:message key="main.button.history" bundle="${bundle}"/></a>
            <a href="/user/lock"><fmt:message key="main.button.lock" bundle="${bundle}"/></a>
    </c:when>
    <c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
        <a href="/admin/search"><fmt:message key="main.button.search" bundle="${bundle}"/></a>
        <a href="/history"><fmt:message key="main.button.history" bundle="${bundle}"/></a>
        <a href="/admin/management"><fmt:message key="main.button.management" bundle="${bundle}"/></a>
    </c:when>
</c:choose>
        </nav>

        <ul>
            <input type="submit" class="submit-transparent img-uk" form="previousRequest" name="locale" value="en"/>

            <input type="submit" class="submit-transparent img-ua" form="previousRequest" name="locale" value="ua"/>


            <c:if test="${not empty user}">
                <li><fmt:message key="app.signed" bundle="${bundle}"/><br/> <b>${user.userAuth.login}</b></li>
                <li><a href="/auth/signout" class="lnk"><fmt:message key="app.button.signout" bundle="${bundle}"/></a></li>
            </c:if>
        </ul>
    </div>

    <c:set var="signin" value="/auth/signin" scope="request"/>
    <c:set var="signup" value="/auth/signup" scope="request"/>
    <c:set var="signout" value="/auth/signout" scope="request"/>


    <%--<c:choose>--%>
    <%--<c:when test="${previousPath ne signin && previousPath ne signup && previousPath ne signout}">--%>

    <%--<form id="previousRequest" method="post" action=${previousPath}>--%>
        <%--</c:when>--%>

        <%--<c:otherwise>--%>

            <form id="previousRequest" method="post" action=${previousPath}>
        <form id="previousRequest" method="post" action="/">
            <%--</c:otherwise>--%>
            <%--</c:choose>--%>

            <c:forEach items="${param}" var="par">
                <c:if test="${par.key ne 'password' && par.key ne 'paswordConfirm'}">
                    <input type="hidden" name="${par.key}" value="${par.value}">
                </c:if>
            </c:forEach>
        </form>


</header>
