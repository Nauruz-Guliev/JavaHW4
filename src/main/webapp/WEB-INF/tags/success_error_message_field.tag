<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@attribute name="messageSuccess" required="true" type="java.lang.String" %>
<%@attribute name="messageError" required="true" type="java.lang.String" %>

<c:if test="${not empty messageError}">
    <span class="error text-danger">${messageError}</span>
</c:if>

<c:if test="${not empty messageSuccess}">
    <p class="text-success">${messageSuccess}</p>
</c:if>


<jsp:doBody/>