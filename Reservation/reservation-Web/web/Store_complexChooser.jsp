<%-- 
    Document   : Store
    Created on : 20 déc. 2022, 21:42:56
    Author     : Arkios
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="DataBase.db"%>
<%@page import="Entities.*"%>
<%
    if (session == null) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
        return;
    }

    Voyageurs user = (Voyageurs) session.getAttribute("user");
    if (user == null)
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
%>

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
        
        <div id="ButtonList" class="specialListButtons">
                <form action="Basket" method="POST" id="LogoutForm">
                    <input class="btn btn-primary" type="submit" id="BasketButton" name="backet" value="Pannier" required>
                </form> 
                
                <form action="Order" method="POST" id="OrderForm">
                    <input class="btn btn-primary" type="submit" id="OrderButton" name="Commands" value="Commandes" required>
                </form> 
            
                <form action="Logout" method="POST" id="LogoutForm">
                    <input class="btn btn-primary" type="submit" id="LogoutButton" name="Logout" value="Logout" required>
                </form> 
        </div>

        <h1 class ="h1">Store - INPRES Enjoy Your Holidays</h1>

        <div class="container">
            <table id="infoTable" class="table table-fixed">
                <thead>
                    <tr>
                        <th class="col-xs-3">Choix</th>
                        <th class="col-xs-3">Nom</th>
                        <th class="col-xs-3">Type</th>
                    </tr>
                </thead>
                <tbody>
                <form action="Store" method="POST" id="myForm" >
                    <%
                        LinkedList<Complexes> list = db.getComplex();

                        int i = 0;
                        for (Complexes comp : list) {

                            out.println("<tr class=\"clickableRow\">");
                            out.println("        <td> <input type=\"submit\" id=\"html\" name=\"selectedComplexe\" value=\"" + comp.getIdComplexe() + "\" required> </td>");
                            out.println("        <td class=\"col-xs-3\">" + comp.getNomComplexe() + "</td>");
                            out.println("        <td class=\"col-xs-3\">" + comp.getTypeComplexe() + "</td>");
                            out.println("    </input>");
                            out.println("</tr>");
                            i++;
                        }
                    %>
                </form>
                </tbody>
            </table>
        </div>
    </body>
</html>
