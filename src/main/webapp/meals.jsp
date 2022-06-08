<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<link rel="stylesheet" href="styles/w3.css">
<head>
    <title>Meals</title>
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-gray w3-opacity w3-left-align">
    <h3><a href="/topjava">Home</a></h3>
</div>

<hr>
<table class="w3-table-all">
    <tr class="w3-sans-serif">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach var="meal" items="${requestScope.listMeals}">
        <tr class="w3-border" style="color: ${meal.excess == true ? 'red' : 'green'}" >
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
