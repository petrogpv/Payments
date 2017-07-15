<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="manage.users.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>${title}</p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>
    <%@include file="/WEB-INF/pages/common/message.jsp" %>

    <p>
        <a class="btn btn-primary" href="/admin/add_user"><fmt:message key="main.button.addUser"
                                                                       bundle="${bundle}"/></a>
    </p>
    <div class="description-text"><p>
        <fmt:message key="app.search.notice" bundle="${bundle}"/></p></div>
    <form method="post" action="/admin/manage/users/action">
        <label><p><b>
            <fmt:message key="manage.users.label.searchByLogin" bundle="${bundle}"/></b></p></label>
        <div class="row">
            <div class=" col-xs-4 col-md-3"><input type="text" class="form-control" required name="login"
                    <c:if test="${not empty param.login}"> value="${param.login}" </c:if>/></div>
            <div><input type="submit" class="btn btn-primary"
                        value="<fmt:message key="app.button.search" bundle="${bundle}"/>"></div>
        </div>
    </form>

    <c:if test="${not empty userManage}">
        <hr>
        <div class="row">
            <div class="col-xs-12 col-md-10">
                <table class="table table-striped">
                    <tr>
                        <th><fmt:message key="manage.users.table.id" bundle="${bundle}"/></th>
                        <th><fmt:message key="manage.users.table.login" bundle="${bundle}"/></th>
                        <th><fmt:message key="manage.users.table.firstName" bundle="${bundle}"/></th>
                        <th><fmt:message key="manage.users.table.lastName" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.button.delete" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${userManage.id}</td>
                        <td>${userManage.userAuth.login}</td>
                        <td>${userManage.firstName}</td>
                        <td>${userManage.lastName}</td>
                        <td>
                            <c:if test="${not empty deleteUser}">
                                <form method="post" action="/admin/delete_user">
                                    <input type="hidden" name="user" value="${userManage.id}">
                                    <input type="submit" name="action"
                                           value="<fmt:message key="app.button.delete" bundle="${bundle}"/>">
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <form method="post" action="/admin/add_account">
            <input type="hidden" name="user" value="${userManage.id}">
            <input type="submit" name="action"
                   value="<fmt:message key="manage.users.button.addAccount" bundle="${bundle}"/>">
        </form>
        <hr>
    </c:if>
    <c:if test="${not empty accounts}">
        <c:forEach items="${accounts}" var="account">
            <div class="row">
                <div class="col-xs-12">
                    <hr>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-5 col-md-4">
                    <div class="row">
                        <ul class="list-inline">
                            <li>
                                <div class="description-text"><h4>
                                    <fmt:message key="manage.users.label.account" bundle="${bundle}"/>
                                </h4></div>
                            </li>
                            <c:if test="${not empty accountsToDelete}">
                                <c:forEach items="${accountsToDelete}" var="accountToDelete">
                                    <c:if test="${account.id eq accountToDelete}">
                                        <li>
                                            <form method="post" action="/admin/delete_account">
                                                <input type="hidden" name="account" value="${account.id}">
                                                <input type="submit" name="action"
                                                       value="<fmt:message key="app.button.delete" bundle="${bundle}"/>">
                                            </form>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty accountsToAddCard}">
                                <c:forEach items="${accountsToAddCard}" var="accountToAddCard">
                                    <c:if test="${account.id eq accountToAddCard}">
                                        <li>
                                            <form method="post" action="/admin/add_card">
                                                <input type="hidden" name="account" value="${account.id}">
                                                <input type="hidden" name="user" value="${userManage.id}">
                                                <input type="submit" name="action"
                                                       value="<fmt:message key="manage.users.button.addCard"
                                                                          bundle="${bundle}"/>">
                                            </form>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:if>

                        </ul>
                    </div>
                </div>
                <c:if test="${not empty account.cards}">
                    <div class="col-xs-7 col-md-6">
                        <div class="description-text">
                            <h4><fmt:message key="manage.users.label.cards" bundle="${bundle}"/></h4></div>
                    </div>
                </c:if>
            </div>

            <div class="row>">
            <div class="col-xs-5 col-md-4">
                <table class="table table-striped">
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="manage.users.table.balance" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${account.id}</td>
                        <td><custom:formatMoney value="${account.balance}"/></td>
                    </tr>
                </table>
            </div>
            <c:if test="${not empty account.cards}">
                <div class="col-xs-7 col-md-6">
                    <table class="table table-striped">
                        <tr>
                            <th>#</th>
                            <th><fmt:message key="app.table.status" bundle="${bundle}"/></th>
                            <th><fmt:message key="app.button.delete" bundle="${bundle}"/></th>
                        </tr>
                        <c:forEach items="${account.cards}" var="card">
                            <tr>
                                <td>${card.id}</td>
                                <td><fmt:message key="card.status.${card.status}" bundle="${bundleEnums}"/></td>
                                <td>
                                    <c:if test="${not empty cardsToDelete}">
                                        <c:forEach items="${cardsToDelete}" var="cardToDelete">
                                            <c:if test="${card.id eq cardToDelete}">
                                                <form method="post" action="/admin/delete_card">
                                                    <input type="hidden" name="card" value="${card.id}">
                                                    <input type="submit" name="action"
                                                           value="<fmt:message key="app.button.delete"
                                                                              bundle="${bundle}"/>">
                                                </form>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                </div>
            </c:if>
        </c:forEach>
    </c:if>
</div>
</body>
</html>

