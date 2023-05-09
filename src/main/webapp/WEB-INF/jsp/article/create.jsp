<%@include file="/WEB-INF/jsp/_header.jsp" %>

<script src="../../../assets/js/tinymce/tinymce.min.js" referrerpolicy="origin"></script>
<script>
    tinymce.init({
        selector: '#articleTextArea'
    });
</script>

<section class="vh-100">
    <div class="form-group">

        <p class="text-center h1 fw-bold">Create article</p>
        <form:form method="POST" modelAttribute="articleDto" class="mx-1 mx-md-4">

            <t:text_input_field fieldName="title" placeholder="Title" type="text"/>

            <div class="d-flex flex-row align-items-center mb-4">
                <div class="form-outline flex-fill mb-0">
                    <textarea id="articleTextArea" class="form-control" rows="5" name="text"
                              path="text">${articleDto.getText()}</textarea>
                    <span class="error text-danger"><form:errors path="text"/></span>
                </div>
            </div>

            <div class="d-flex justify-content-center mb-4">
                <input type="submit" value="Save" class="btn btn-success btn-lg w-100 p-1"/>
            </div>

        </form:form>


        <t:success_error_message_field messageSuccess="${messageSuccess}"
                                       messageError="${messageError}"/>
    </div>
</section>

<%@include file="/WEB-INF/jsp/_footer.jsp" %>
