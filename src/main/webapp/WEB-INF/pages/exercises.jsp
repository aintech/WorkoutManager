<%-- 
    Document   : index
    Created on : Dec 21, 2017
    Author     : Yaremchuk E.N. (aka Aintech)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Exercises</title>
    </head>
    <body>
        <div align="center">
            <h1>Exercises</h1>
            <table align="center">
                <tr>
                    <td>
                        <c:forEach items="${exerciseList}" var="exercise">
                            <li id="exercise_<c:out value="exercise.id" />">
                                <c:out value="${exercise.name}"/>
                            </li>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        ${exercise.bodyPart.description} - ${exercise.name}
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>