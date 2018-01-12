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
        <title>Workout Manager Home Page</title>
    </head>
    <body>
        <div align="center">
            <h1>Workout Manager Home Page</h1>
            <table align="center">
                <tr>
                    <td>
                        <a href="<c:url value="/exercises" />">
                            <div align="center" style="height: 100px; width: 100px; background-color: bisque;">
                                EXERCISES
                            </div>
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/perform" />">
                            <div align="center" style="height: 100px; width: 100px; background-color: aqua;">
                                PERFORM
                            </div>
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/workout/schedule" />">
                            <div align="center" style="height: 100px; width: 100px; background-color: bisque;">
                                SCHEDULE
                            </div>
                        </a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>