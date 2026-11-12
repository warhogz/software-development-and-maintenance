<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Учёт сотрудников</title>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/style.css">
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/views/header.jsp" />
  <main class="container py-5 flex-grow-1">
    <section class="text-center mb-4">
      <h1 class="display-6">Главное меню</h1>
      <p class="lead text-muted">Выберите раздел для работы с данными сотрудников.</p>
    </section>
    <div class="row justify-content-center g-4">
      <div class="col-md-5"><a class="menu-card card h-100 text-decoration-none" href="#employees"><div class="card-body"><h2 class="h4">Сотрудники</h2><p class="mb-0">Просмотр и ведение списка сотрудников.</p></div></a></div>
      <div class="col-md-5"><a class="menu-card card h-100 text-decoration-none" href="#departments"><div class="card-body"><h2 class="h4">Отделы</h2><p class="mb-0">Работа со структурой подразделений.</p></div></a></div>
    </div>
    <div class="alert alert-primary mt-5 text-center" role="alert">Интерфейс создан с применением Bootstrap и jQuery.</div>
  </main>
  <jsp:include page="/views/footer.jsp" />
  <script src="js/jquery-3.6.4.js"></script>
  <script src="js/bootstrap.bundle.min.js"></script>
  <script>$(function () { $('.menu-card').on('mouseenter', function () { $(this).addClass('shadow'); }).on('mouseleave', function () { $(this).removeClass('shadow'); }); });</script>
</body>
</html>
