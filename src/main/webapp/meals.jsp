<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h4><a href="meals?action=insert">Create Meal</a></h4>
<table border="2px" style="font-size: 20px" align="center">
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <c:forEach items="${requestScope.tos}" var="meal">
        <c:choose>
            <c:when test="${meal.excess==true}">
                <tr style="color: red; font-weight: bold">
            </c:when>
            <c:otherwise>
                <tr style="color: green; font-weight: bold">
            </c:otherwise>
        </c:choose>
        <td><c:out value="${meal.dateTime.format( DateTimeFormatter.ofPattern(\"dd.MM.yyyy HH:mm\"))}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><a href="meals?action=update&mealId=${meal.id}">Update</a></td>
        <td>
            <form style=" margin: 3px; padding: 3px;" action="meals" method="post" onsubmit="return confirm('Are you sure you want to delete this meal?');">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="mealId" value="${meal.id}">
                <input type="submit" value="Delete">
            </form>
        </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>