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
            <form action="Store_RoomChooser" method="POST" id="returnForm">
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
            <form action="Add_Basket" method="POST" id="Add_Basket" >
                <%
                    if (session == null) {
                        response.sendRedirect(request.getContextPath() + "/Login.jsp");
                        return;
                    }
                    
                    Voyageurs user = (Voyageurs) session.getAttribute("user");
                    Complexes complex = (Complexes) session.getAttribute("selected_complex");
                    Chambres room = (Chambres) session.getAttribute("selected_room");

                    if (user == null) {
                        response.sendRedirect(request.getContextPath() + "/Login.jsp");
                        return;
                    }
                    if (complex == null) {
                        response.sendError(1010, "Erreur Complex NULL");
                        return;
                    }
                    if (room == null) {
                        response.sendError(1011, "Erreur room NULL");
                        return;
                    }                    
                    
                    out.println("<label class=\"input-group mb-3\"> Information: " + user.getNomVoyageur() + ", " + user.getPrenomVoyageur().toUpperCase()
                            + ", " + user.getEmail() + "</label>");
                    out.println("<label class=\"input-group mb-3\"> Chambre: "+ room.getType()+" n°"+ room.getNumChambre() +" </label>");
                    out.println("<label class=\"input-group mb-3\"> Complexe: "+ complex.getNomComplexe()+"</label>");
                    
                %>
                
                <%
                    String error = (String) request.getAttribute("custom-error");
                    if(error != null && error instanceof String){
                        out.println("<label id=\"errorMessage\" class =\"errored\" >" + error + " </label>");
                    }
                %>

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon1">Nombre de Nuits</span>
                    </div>
                    <%
                        String paramNuit = request.getParameter("nbjours");
                        out.println("<input type=\"number\" class=\"form-control\" placeholder=\"0\" aria-label=\"Number\" aria-describedby=\"basic-addon1\" name=\"nbjours\" required min=\"1\" value=\"" + (paramNuit == null ? "" : paramNuit) + "\" max=\"365\" required>");
                    %>
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon2">Date d'arrivée</span>
                    </div>
                    <%
                        String paramDate = request.getParameter("date");
                        out.println(" <input type=\"date\" class=\"form-control\" placeholder=\"0\" aria-label=\"Date\" aria-describedby=\"basic-addon2\" name=\"date\" value=\"" + (paramDate == null ? "" : paramDate) + "\" required > ");
                    %>
                </div>
                
                <input type="submit" class="btn btn-primary" value="Ajouter au Panier">
            </form>
                
        </div>
    </body>
</html>
