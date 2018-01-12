<%-- 
    Document   : registerform
    Created on : Dec 26, 2017
    Author     : Yaremchuk E.N. (aka Aintech)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Registration Form</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <h1>Register</h1>
        <sf:form methid="POST" commandName="user">
            <sf:errors path="*" element="div" cssClass="errors"/>
            <sf:label path="username" cssErrorClass="error">Username</sf:label>: <sf:input path="username" cssErrorClass="error"/><br/>
            <sf:label path="email" cssErrorClass="error">E-mail</sf:label>: <sf:input path="email" cssErrorClass="error"/><br/>
            <sf:label path="password" cssErrorClass="error">Password</sf:label> <sf:password path="password" cssErrorClass="error"/><br/>
            <input type="submit" value="Register"/>
        </sf:form>
    </body>
</html>