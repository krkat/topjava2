<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.LocalTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <tbody>
    <% for (MealTo to : (List<MealTo>) request.getAttribute("tos")) {
        if (to.isExcess()) {%>
    <tr style="color: red; font-weight: bold">
            <%} else {%>
    <tr style="color: green; font-weight: bold">
        <%}%>
        <td><%=to.getDateTime()%></td>
        <td><%=to.getDescription()%></td>
        <td><%=to.getCalories()%></td>
    </tr>
    </tbody>
    <%}%>
</table>
</body>
</html>