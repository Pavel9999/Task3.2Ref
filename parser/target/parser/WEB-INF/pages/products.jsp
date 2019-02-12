<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="UTF-8">
    <title>Меню ${parser_name}</title>

    <style>
        <%@include file="css/products.css"%>
    </style>

    <%@ page errorPage="errorpage.jsp"%>
</head>

<body>

<ul id="main_view">
    <li id="left_side">
        <ul id="buttons">
            <li><a href="http://localhost:8081" class="button">${home_text}</a></li>
            <li><a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=cold_snack&page_num=1&lang=${lang}" class="button">${cold_snack_text}</a></li>
            <li><a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=hot_snack&page_num=1&lang=${lang}" class="button">${hot_snack_text}</a></li>
            <li><a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=breakfast&page_num=1&lang=${lang}" class="button">${breakfast_text}</a></li>

            <li id="language_li"><a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=${pr_type}&page_num=${page_num}&lang=ru" class="button">Русский </a>
                <a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=${pr_type}&page_num=${page_num}&lang=en" class="button"> English</a>
            </li>

        </ul>
    </li>
    <li id="right_side">
        <table id="products">
            <tr id="header">
                <td><p>${photo_text}</p></td>
                <td><p>${title_text}</p></td>
                <td><p>${portion_text}</p></td>
                <td><p>${description_text}</p></td>
                <td><p>${price_text}</p></td>
            </tr>

        <c:forEach varStatus="i" var="product" items="${products}" >
            <tr class="product">
                <td><p>${product.imagePath}</p></td>
                <td><p>${product.title}</p></td>
                <td><p>${product.portion}</p></td>
                <td>
                    <p>${product.description}</p>
                    <c:forEach varStatus="j" var="composition" items="${product.composition}" >
                        <p><c:out value="${composition.description}"/></p>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach varStatus="j" var="composition" items="${product.composition}" >
                        <p><c:out value="${composition.number}"/>    <c:out value="${composition.price}"/></p>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>

        </table>

    </li>
</ul>

<ul id="pages">
    <c:forEach varStatus="i" begin="1" end="${pages_count}"  >
        <li><a href="http://localhost:8081/menu?parser_type=${parser_type}&pr_type=${pr_type}&page_num=${i.count}&lang=${lang}" class="button">${i.count}</a></li>

    </c:forEach>
</ul>


</body>

</html>>