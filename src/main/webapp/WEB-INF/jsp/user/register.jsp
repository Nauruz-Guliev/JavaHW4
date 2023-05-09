<%@include file="/WEB-INF/jsp/_header.jsp" %>

<section class="vh-100">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card text-black">
                    <div class="card-body p-md-5">
                        <div class="row justify-content-center">
                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>

                                <form:form method="POST" modelAttribute="userRegisterDto" class="mx-1 mx-md-4">



                                    <t:text_input_field type="text" placeholder="First name" fieldName="firstName"/>

                                    <t:text_input_field type="text" placeholder="Last name" fieldName="lastName"/>

                                    <t:text_input_field type="email" placeholder="Email" fieldName="email"/>


                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <form:input type="password" placeholder="Password" path="password"
                                                        id="password" class="form-control"/>
                                            <span class="error text-danger"><form:errors path="password"/></span>
                                            <span class="error text-danger">${passwordRepeatError}</span>
                                        </div>
                                    </div>


                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <form:input type="password" placeholder="Repeat password"
                                                        path="passwordRepeat" id="passwordRepeat" class="form-control"/>
                                            <span class="error text-danger"><form:errors path="password"/></span>
                                            <span class="error text-danger">${passwordRepeatError}</span>
                                        </div>
                                    </div>

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <form:select path="country" class="form-control" id="country">
                                                <form:option value="Russia" label="Russia"/>
                                                <form:option value="Ukraine" label="Ukraine"/>
                                                <form:option value="Belarus" label="Belarus"/>
                                                <form:option value="Kazakhstan" label="Kazakhstan"/>
                                            </form:select>
                                            <span class="error text-danger"><form:errors path="country"/></span>
                                        </div>
                                    </div>

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <div class="form-outline flex-fill mb-0">
                                            <form:label path="birthDate">Birth date</form:label>
                                            <form:input type="date" path="birthDate" class="form-control"/>
                                            <span class="error text-danger"><form:errors path="birthDate"/></span>

                                        </div>
                                    </div>

                                    <div class="d-flex flex-row mb-4">
                                        <div class="w-100">
                                            <form:label path="policyAgreement"
                                                        class="form-label">Policy agreement</form:label>
                                        </div>
                                        <div>
                                            <form:checkbox path="policyAgreement" class="form-control"
                                                           title="Policy agreement"/>
                                        </div>
                                    </div>
                                    <span class="error text-danger"><form:errors path="policyAgreement"/></span>
                                    <br>

                                    <div class="d-flex justify-content-center mb-4">
                                        <input type="submit" value="Register" class="btn btn-success btn-lg w-100 p-1"/>
                                    </div>
                                </form:form>
                                <t:success_error_message_field messageSuccess="${messageSuccess}"
                                                               messageError="${messageError}"/>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="/WEB-INF/jsp/_footer.jsp" %>

