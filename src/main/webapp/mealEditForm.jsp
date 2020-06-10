<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

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
                    action="${pageContext.request.contextPath}/meals"
                    name="frmAddMeal"
            >
                <input type="hidden" readonly="readonly" name="mealId"  value="${meal.id}"/>
                <br/>
                <label for="dateTime">Date/Time:</label><br/>
                <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}"/>
                <br/>
                <label for="description">Description:</label><br/>
                <input type="text" id="description" name="description" value="${meal.description}"/>
                <br/>
                <label for="calories">Calories:</label><br/>
                <input type="number" id="calories" name="calories" value="${meal.calories}"/>
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
