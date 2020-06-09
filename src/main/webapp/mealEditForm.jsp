<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>

<html>
<head>
    <title>Meal form</title>
    <link rel="stylesheet" type="text/css" href="./mini-default.min.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="card">
            <form
                    method="POST"
                    action="${pageContext.request.contextPath}/mealcrud"
                    name="frmAddMeal"
            >
                <label for="mealId">Meal id:</label><br/>
                <input type="number" readonly="readonly" id="mealId" name="mealId"
                       value="<c:out value="${meal.id}" />"/>
                <br/>
                <label for="dateTime">Date/Time:</label><br/>
                <input type="datetime-local" id="dateTime" name="dateTime" value="<c:out value="${meal.dateTime}" />"/>
                <br/>
                <label for="description">Description:</label><br/>
                <input type="text" id="description" name="description" value="<c:out value="${meal.description}"/>"/>
                <br/>
                <label for="calories">Calories:</label><br/>
                <input type="number" id="calories" name="calories" value="<c:out value="${meal.calories}"/>"/>
                <br/>
                <br/>
                <input class="button primary" type="submit" value="Submit"/>
            </form>
        </div>
        <div class="col-md-4"></div>
        <a href="meals"></a>
    </div>
</div>
</body>
</html>
