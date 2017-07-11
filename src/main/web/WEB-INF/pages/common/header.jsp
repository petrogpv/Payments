<div class="container header-background">
    <div class="row header-padding">
        <div class="col-sm-3">
            <div class="header-element header-main-label">
                <fmt:message key="app.appNamePayments" bundle="${bundle}"/>
                <span><fmt:message key="app.appNameSystem" bundle="${bundle}"/></span>
            </div>
        </div>
        <div class="col-sm-6 hover">
            <c:choose>
                <c:when test="${sessionScope.user.userAuth.role eq 'USER'}">
                    <a class="header-element" href="/user/payment"><fmt:message key="main.button.payment" bundle="${bundle}"/></a>
                    <a class="header-element" href="/user/deposit"><fmt:message key="main.button.deposit" bundle="${bundle}"/></a>
                    <a class="header-element" href="/history"><fmt:message key="main.button.history" bundle="${bundle}"/></a>
                    <a class="header-element" href="/user/lock"><fmt:message key="main.button.lock" bundle="${bundle}"/></a>
                </c:when>
                <c:when test="${sessionScope.user.userAuth.role eq 'ADMIN'}">
                    <a class="header-element" href="/history"><fmt:message key="main.button.history" bundle="${bundle}"/></a>
                    <a class="header-element" href="/admin/manage/cards"><fmt:message key="main.button.manage.cards" bundle="${bundle}"/></a>
                    <a class="header-element" href="/admin/manage/users"><fmt:message key="main.button.manage.users" bundle="${bundle}"/></a>
                </c:when>
            </c:choose>
        </div>
        <c:if test="${empty user}">
        <div class="col-sm-1 col-xs-offset-6 col-sm-offset-3 align-center-right">
            </c:if>
        <c:if test="${not empty user}">
        <div class="col-sm-1 align-center-right">
            </c:if>
                <span class="padding-left"><input type="submit" class="submit-transparent img-uk" form="previousRequest"
                                                  name="locale" value="en"/> </span>
                <span class="padding-left"><input type="submit" class="submit-transparent img-ua padding-left" form="previousRequest" name="locale" value="ua"/></span>
            </div>
            <c:if test="${not empty user}">
                <div class="col-sm-2 col-md-2 col-lg-2">
                <span class="header-element align-center">
                    <fmt:message key="app.signed" bundle="${bundle}"/>
                </span>
                    <span class="header-element align-center">
                    <b>${user.userAuth.login}</b>
                </span>
                </div>
                <div class="col-sm-2 col-md-2 hover align-center-left ">
                    <a class="header-element" href="/auth/signout" class="lnk"><fmt:message key="app.button.signout" bundle="${bundle}"/></a>
                </div>
            </c:if>
        </div>
    </div>

    <form id="previousRequest" method="post" action=${previousPath}>
        <c:forEach items="${param}" var="par">
            <c:if test="${par.key ne 'password' && par.key ne 'paswordConfirm'}">
                <input type="hidden" name="${par.key}" value="${par.value}">
            </c:if>
        </c:forEach>
    </form>



