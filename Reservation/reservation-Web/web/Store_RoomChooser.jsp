<%-- 
    Document   : Store
    Created on : 20 déc. 2022, 21:42:56
    Author     : Arkios
--%>

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
            
            <div id="ButtonList">
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
        </header>


        <h1 class ="h1">Store - INPRES Enjoy Your Holidays</h1>
        <div class="container">
            <table id="infoTable" class="table table-fixed">
                <thead>
                    <tr>
                        <th class="col-xs-3">Choix</th>
                        <th class="col-xs-3">Chambre: </th>
                        <th class="col-xs-3">Equipements: </th>
                        <th class="col-xs-3">Lits: </th>
                        <th class="col-xs-3">Prix: </th>
                    </tr>
                </thead>
                <tbody>
                <form action="Store_RoomChooser" method="POST" id="myForm" >
                    <%
                        if (session == null) {
                            response.sendRedirect(request.getContextPath() + "/Login.jsp");
                            return;
                        }

                        Voyageurs user = (Voyageurs) session.getAttribute("user");
                        Complexes complex = (Complexes) session.getAttribute("selected_complex");

                        if (user == null) {
                            response.sendRedirect(request.getContextPath() + "/Login.jsp");
                            return;
                        }
                        if (complex == null) {
                            response.sendError(1010, "Erreur Complex NULL");
                            return;
                        }

                        LinkedList<Chambres> list = db.getRooms(complex);

                        int i = 0;
                        for (Chambres room : list) {

                            out.println("<tr class=\"clickableRow\">");
                            out.println("        <td> <input type=\"submit\" id=\"html\" name=\"selectedRoom\" value=\"" + room.getNumChambre() + "\" required> </td>");
                            out.println("        <td class=\"col-xs-3\">" + room.getType() + " n°" + room.getNumChambre() + "</td>");
                            out.println("        <td class=\"col-xs-3\">" + room.getEquipements() + "</td>");
                            out.println("        <td class=\"col-xs-3\">" + room.getNombreLits() + "</td>");
                            out.println("        <td class=\"col-xs-3\">" + String.format("%.2f", room.getPrixHTVA() * 1.21) + "€" + "</td>");
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
