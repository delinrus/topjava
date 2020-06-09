<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>

<html>
<head>
    <title>Meal form</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/mealcrud" name="frmAddMeal">
    <label for="mealId">Meal id:</label><br>
    <input type="number" readonly="readonly" id="mealId" name="mealId" value="<c:out value="${meal.id}" />"/> <br/>

    <label for="dateTime">Date/Time</label><br>
    <input type="datetime-local" id="dateTime" name="dateTime" value="<c:out value="${meal.dateTime}" />"/> <br/>

    <label for="description">Description</label><br>
    <input type="text" id="description" name="description" value="<c:out value="${meal.description}" />"/> <br/>

    <label for="calories">Calories</label><br>
    <input type="number" id="calories" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>

    <br>
    <input type="submit" value="Submit">
</form>

<a href="meals"></a>
</body>
</html>
