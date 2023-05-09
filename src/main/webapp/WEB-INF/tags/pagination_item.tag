<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@attribute name="selectedPage" required="true" type="java.lang.Integer" %>
<%@attribute name="index" required="true" type="java.lang.Integer" %>


<c:if test="${selectedPage == index}">
    <li class="page-item active" aria-current="page">
        <span class="page-link">${index}</span>
    </li>
</c:if>
<c:if test="${selectedPage != index}">
    <li class="page-item"><a class="page-link"
                             href="<c:url value="${pageContext.request.contextPath}?page_number=${index}&page_size=${5}"/>">${index}</a></li>
</c:if>

<jsp:doBody/>