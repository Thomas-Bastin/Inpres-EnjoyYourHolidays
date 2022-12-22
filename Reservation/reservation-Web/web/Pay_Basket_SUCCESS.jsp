<%-- 
    Document   : Store
    Created on : 20 déc. 2022, 21:42:56
    Author     : Arkios
--%>

<%@page import="Entities.Chambres"%>
<%@page import="Entities.BasketRow"%>
<%@page import="java.util.LinkedList"%>
<%@page import="DataBase.db"%>
<%@page import="Entities.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

        <link href="./styles/Store.css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store - Reservation</title>
    </head>
    <body>
        <header>
            <form action="Store" method="POST" id="returnForm">
                <input class="btn btn-primary" type="submit" id="ReturnButton" name="return" value="<---" required>
            </form>
            
            <form action="Order" method="POST" id="OrderForm">
                <input class="btn btn-primary" type="submit" id="OrderButton" name="Commands" value="Commandes" required>
            </form> 
            
            <div id="ButtonList">
                <form action="Logout" method="POST" id="LogoutForm">
                    <input class="btn btn-primary" type="submit" id="LogoutButton" name="Logout" value="Logout" required>
                </form> 
            </div>
        </header>


        <h1 class ="h1">Store - INPRES Enjoy Your Holidays</h1>
        <div class="container">
            <label id="success"> Bravo, vos réservations on été payées !!! </label>
            <form action="Store" method="POST" id="successForm">
                <input class="btn btn-primary" type="submit" id="ReturnButton" name="return" value="Revenir au Magasin" required >
            </form>            
        </div>
    </body>
</html>
