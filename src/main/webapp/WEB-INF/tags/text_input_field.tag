<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@attribute name="fieldName" required="true" type="java.lang.String"%>
<%@attribute name="placeholder" required="true" type="java.lang.String"%>
<%@attribute name="type" required="true" type="java.lang.String"%>



<div class="d-flex flex-row align-items-center mb-4">
    <div class="form-outline flex-fill mb-0">
        <form:input type="${type}" placeholder="${placeholder}" path="${fieldName}" id="${fieldName}" class="form-control"/>
        <span class="error text-danger"><form:errors path="${fieldName}"/></span>
    </div>
</div>

<jsp:doBody/>
