<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Add user"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">

        <div class="header-text"><p>Add user</p></div>
        <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
        <%@include file = "/WEB-INF/pages/common/message.jsp"%>
        <form method="post" action="/admin/add_user/action">
            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>First name</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="firstName" required/></div>
            </div>

            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>Last name</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="lastName" required/></div>
            </div>

            <div class="row">
                <div class="col-xs-2"><label class="label-input"><p><b>Login (e-mail)</b></p></label></div>
                <div class="col-xs-4"><input type="text" class="form-control" name="login" required/></div>
            </div>
            <div class="row">
                <div class="col-xs-1 col-xs-offset-2"><input type="submit" class="btn btn-primary" value="Save"></div>
            </div>
        </form>
</div>
</body>
</html>
