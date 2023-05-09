<%@include file="/WEB-INF/jsp/_header.jsp" %>

<section class="vh-100">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card text-black">
                    <div class="card-body p-md-5">
                        <div class="row justify-content-center">
                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Login</p>
                                <form method="post" action="${action}">

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <input name="username" type="email" class="form-control"/>
                                        </div>
                                    </div>


                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <input name="password" type="password" class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-center mb-4">
                                        <input type="submit" value="Save" class="btn btn-success btn-lg w-100 p-1"/>
                                    </div>

                                </form>

                                <t:success_error_message_field messageSuccess="${messageSuccess}"
                                                               messageError="${messageError}"/>
                                <c:if test="${message !=null}">
                                    <div class="alert alert-danger" role="alert">
                                            ${message}
                                    </div>
                                </c:if>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="/WEB-INF/jsp/_footer.jsp" %>

