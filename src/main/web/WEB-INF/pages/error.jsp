<%@include file="/WEB-INF/pages/common/taglib.jsp" %>
<fmt:message key="error.title" var="title" bundle="${bundle}"/>

<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<div class="container">
    <div class="header-text"><p><fmt:message key="error.titleExpand" bundle="${bundle}"/></p></div>

    <%@include file="/WEB-INF/pages/common/messageError.jsp" %>

    <p style="text-indent: 1.5em;"><fmt:message key="error.message.first" bundle="${bundle}"/></p>
    <p style="text-indent: 1.5em;"><fmt:message key="error.message.second" bundle="${bundle}"/></p>
    <p style="text-indent: 1.5em;"><fmt:message key="error.message.third" bundle="${bundle}"/></p>
</div>
</body>
</html>