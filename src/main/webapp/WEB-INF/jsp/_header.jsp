<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registration</title>

  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


</head>
<body>

<nav class="navbar sticky-top navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="<c:url value="/"/>">Home</a><br><br>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">

        <security:authorize access="!isAuthenticated()">
        <a class="nav-link" href="${s:mvcUrl('UC#newUser').build()}">Register</a>
        <a class="nav-link" href="${s:mvcUrl('UC#login').build()}">Login</a>

        </security:authorize>

        <!-- Почему-то не работает метод hasRole('ADMIN'), хотя isAuthenticated() работает прекрасно
         -->

        <security:authorize access="isAuthenticated()">
          <a class="nav-link" href="${s:mvcUrl('AC#newArticle').build()}">Create article</a>

          <a class="nav-link" href="${s:mvcUrl('AC#getArticles').build()}">Read articles</a>
          <p> Authenticated as <security:authentication property="principal.username" /> <a href='${s:mvcUrl("UC#logout").build()}'>Logout</a></p>

        </security:authorize>
      </div>
    </div>
  </div>
</nav>
