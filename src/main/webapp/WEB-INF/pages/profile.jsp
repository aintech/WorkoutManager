<%-- 
    Document   : profile
    Created on : Dec 26, 2017
    Author     : Yaremchuk E.N. (aka Aintech)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>User Profile</title>
    </head>
    <body>
        <h1>User Profile - ${user.username}</h1>
        <c:out value="${user.username}" /><br/>
        <c:out value="${user.email}" /><br/>
    </body>
</html>