
<%@include file="/WEB-INF/jsp/_header.jsp" %>

<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">404</h1>
        <p class="fs-3"> <span class="text-danger">Opps!</span> Page not found.</p>
        <p class="lead">
            The page does not exist.
        </p>
        <a href="<c:url value="/"/>" class="btn btn-primary">Go Home</a>
    </div>
</div>
<%@include file="/WEB-INF/jsp/_footer.jsp" %>
