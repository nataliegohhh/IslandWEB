<%-- 
    Document   : mrp
    Created on : Aug 27, 2014, 1:22:33 PM
    Author     : Owner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello MRP!</h1>
        <form action="saleshistory" method="GET">
            <input type="text" name="year" placeholder="Year"/><br>
            <input type="text" name="month" placeholder="Month"/><br>
            <input type="submit" value="View" class="button" />
        </form>
    </body>
</html>
