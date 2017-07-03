<html>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<body>
<%@include file="/WEB-INF/pages/common/header.jsp" %>

<h1>Request URI: ${pageContext.request.requestURI}</h1>
${pageContext.request.servletPath}
</body>