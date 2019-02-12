<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Парсер</title>
    <%@ page errorPage="errorpage.jsp"%>
</head>
<body>

    <a href="http://localhost:8081/menu?parser_type=sax&pr_type=cold_snack&page_num=1" class="button">SAX</a>
    <a href="http://localhost:8081/menu?parser_type=dom&pr_type=cold_snack&page_num=1" class="button">DOM</a>
    <a href="http://localhost:8081/menu?parser_type=stax&pr_type=cold_snack&page_num=1" class="button">StAX</a>




</body>
</html>