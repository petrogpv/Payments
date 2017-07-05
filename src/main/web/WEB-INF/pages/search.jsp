<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Search"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p>Search/Unlock</p></div>
    <%@include file = "/WEB-INF/pages/common/messageError.jsp"%>
    <%@include file = "/WEB-INF/pages/common/message.jsp"%>

    <form method="post" action="/admin/search">
        <p><b>Choose search type:</b></p>
        <p><input type="radio"  name="searchType" value="card" checked>by card number</p>
        <p><input type="radio"  name="searchType" value="login">by user login (e-mail) </p>
        <label><p><b>Search parameter:</b></p></label>
        <div class="row">
            <div class=" col-xs-3"> <input type="text" class="form-control" required name="searchParameter"/></div>
            <div><input type="submit" class="btn btn-primary" value="Search"></div>
        </div>
    </form>




</div>
</body>
</html>