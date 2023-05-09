<%@include file="/WEB-INF/jsp/_header.jsp" %>

<div class="d-flex align-items-center justify-content-center vh-100">
  <div class="text-center">
    <h1 class="display-1 fw-bold">Access denied!</h1>
    <p class="lead">
      You need to be authorized!
    </p>
    <a href="<c:url value="/user/login"/>" class="btn btn-primary">Login</a>
  </div>
</div>

<%@include file="/WEB-INF/jsp/_footer.jsp" %>
