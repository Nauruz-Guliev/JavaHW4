<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/jsp/_header.jsp" %>

<div class="m-2">
    <h1>Articles</h1>
</div>

<c:forEach var="article" items="${articles}">
    <div class="card">

        <h5 class="card-header"><a
                href="${pageContext.request.contextPath}/article/update/${article.getSlug()}">${article.getTitle()}</a></h5>

    <div class="card-body">
        <h6 class="card-subtitle m-2 text-muted">
            Categories:
            <c:forEach var="category" items="${article.getCategories()}">
                ${category.getName()}
            </c:forEach>
        </h6>

        <div class="w-100 m-2">
            <div class="w-100">
                <p class="card-text">${article.getText()}</p>
            </div>
        </div>
    </div>
</c:forEach>


<nav>
    <ul class="pagination pagination-lg">

    <c:if test="${pageCount > 6}">
        <t:pagination_item selectedPage="${selectedPage}" index="${1}"/>
        <c:if test="${((selectedPage - 1) >= 3) && pageCount > 3}">

            <t:pagination_dots_item/>

            <c:forEach var="i" begin="${ ((selectedPage-1) <= 2)? 2 : (selectedPage-1)}"
                       end="${(selectedPage+1 > (pageCount -2))? (pageCount -2) : selectedPage+1}">
                <t:pagination_item selectedPage="${selectedPage}" index="${i}"/>
            </c:forEach>
        </c:if>

        <c:if test="${(selectedPage - 1) < 3 && pageCount > 3}">
            <c:forEach var="i" begin="${2}" end="${4}">
                <t:pagination_item selectedPage="${selectedPage}" index="${i}"/>
            </c:forEach>
            <c:if test="${pageCount > 8}">
                <t:pagination_dots_item/>
            </c:if>
        </c:if>

        <c:if test="${((pageCount - selectedPage) > 3) && ((selectedPage - 1) >= 3)}">

            <t:pagination_dots_item/>

        </c:if>
        <c:if test="${(pageCount > 5)}">

            <t:pagination_item selectedPage="${selectedPage}" index="${pageCount-1}"/>

        </c:if>
    </c:if>
    <c:if test="${pageCount <= 6}">
        <c:forEach var="i" begin="${1}" end="${pageCount-1}">
            <t:pagination_item selectedPage="${selectedPage}" index="${i}"/>
        </c:forEach>
    </c:if>
    </ul>
</nav>


<%@include file="/WEB-INF/jsp/_footer.jsp" %>
