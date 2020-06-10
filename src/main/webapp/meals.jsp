<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="mealsList" scope="request" type="java.util.List"/>
<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="meals.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${mealsList}">
        <tr class="${meal.excess ? 'excessStyle' : 'nonExcessStyle'}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Edit</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<hr/>
<form>
    <input type="button" value="Add new" onClick='location.href="meals?action=add"'>
</form>
</body>
</html>
