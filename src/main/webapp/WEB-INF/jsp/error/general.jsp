<%@include file="/WEB-INF/jsp/_header.jsp" %>

<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">Error</h1>
        <p class="lead">
            ${message}
        </p>
        <a href="<c:url value="/"/>" class="btn btn-primary">Go Home</a>
    </div>
</div>

<%@include file="/WEB-INF/jsp/_footer.jsp" %>
