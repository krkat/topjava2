<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<form action="meals" method="post">
    <input type="hidden" name="action" value="${meal.id != null ? 'update' : 'insert'}">
    <input type="hidden" name="mealId" value="${meal.id}">

    <label
            for="dateTime">DateTime:</label>
    <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}"> <br><br>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="${meal.description}">
    <br><br>

    <label for="calories">Calories:</label>
    <input type="number" id="calories" name="calories" value="${meal.calories}"> <br><br>

    <input type="submit" value="${meal.id != null ? 'Update' : 'Save'}">
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>