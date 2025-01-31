<%-- 
    Document   : admin
    Created on : Jun 25, 2024, 10:20:44 PM
    Author     : FPTSHOP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <link href="css/create.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container" id="container" >
                <form action="DispatchServlet" method="post">
                    <h1>User Information</h1>
                    <img src="photo/profile2.jpg" class="bd-placeholder-img" width="30%" height="20%">
                    <h4>${user.getUserId()}</h4>            
                <input type="text" placeholder="Full Name" name="name" value="${user.getFullname()}"/>      
                <input type="password" name="changePass" placeholder="Change Password"/> 
                <input type="password" name="confirm" placeholder="Confirm Password"/>
                <c:if test="${message != null}" >
                    <div class="alert alert-danger" role="alert">${message}</div>
                </c:if>
                <button name="btAction" value="Change" type="submit">Change</button>
            </form>
        </div>
    </body>
</html>
