
<header>
    <div  class="container">
        <div class="greeting">
            <h5>
                <fmt:message key="app.language" bundle="${bundle}"/>
                <a href="/locale?locale=en">EN</a>
                <a href="/locale?locale=ua">UA</a>
            </h5>
        </div>
        <div class="headers">
            <h1><fmt:message key="app.appName" bundle="${bundle}"/></h1>
        </div>
        <c:set var = "user" scope="page" value="${sessionScope.user}"/>
        <c:if test="${not empty user}">
            <p class="greeting navbar-text navbar-right"><fmt:message key="app.signed" bundle="${bundle}"/>
                 ${user.firstName} ${user.lastName}  <a href="/logout" class="lnk">
                    <fmt:message key="app.signout" bundle="${bundle}"/></a></p>
        </c:if>


    </div>
</header>



