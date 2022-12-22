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
                        <th class="col-xs-3">Complexe: </th>
                        <th class="col-xs-3">Chambre: </th>
                        <th class="col-xs-3">Lits: </th>
                        <th class="col-xs-3">Debut </th>
                        <th class="col-xs-3">Fin: </th>
                        <th class="col-xs-3">Prix: </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (session == null) {
                            response.sendRedirect(request.getContextPath() + "/Login.jsp");
                            return;
                        }
                        LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
                        if(list != null){ 
                            if(!list.isEmpty()){
                                int i = 0;
                                for (BasketRow row : list) {
                                    out.println("<form action=\"Basket\" method=\"POST\" id=\"delForm" + i + "\" ></form>");
                                    out.println("<input type=\"number\" id=\"html\" name=\"idrow\" value=\"" + i + "\" hidden form=\"delForm" + i + "\">");
                                    out.println("<tr class=\"clickableRow\">");
                                    out.println("        <td> <input type=\"submit\" id=\"html\" value=\"Delete\" required form=\"delForm" + i + "\"> </td>");
                                    out.println("        <td class=\"col-xs-3\">" + row.getComplexe().getNomComplexe() + " [" + row.getComplexe().getTypeComplexe() + "]" + "</td>");
                                    out.println("        <td class=\"col-xs-3\">" + row.getRoom().getType() + " n°" + row.getRoom().getIdComplexe() + "</td>");
                                    out.println("        <td class=\"col-xs-3\">" + row.getRoom().getNombreLits() + "</td>");
                                    out.println("        <td class=\"col-xs-3\">" + row.getDate() + "</td>"); 
                                    out.println("        <td class=\"col-xs-3\">" + row.getDate().toLocalDate().plusDays(row.getNbNuit()-1) + "</td>");
                                    out.println("        <td class=\"col-xs-3\">" + String.format("%.2f",db.howMuchToPay(row.getRoom(), row.getComplexe().getIdComplexe(), row.getRoom().getNumChambre(), row.getDate(), row.getNbNuit())) + "€" + "</td>");
                                    out.println("</tr>");
                                    i++;
                                }
                                //Display Input PayRoom
                            }
                        }
                    %>
                </tbody>
            </table>
                <%
                    if (session == null) {
                        response.sendRedirect(request.getContextPath() + "/Login.jsp");
                        return;
                    }
                    
                    if(list != null){
                        if(!list.isEmpty()){
                            out.println("<form action=\"Basket\" method=\"POST\" id=\"PayForm\">");
                            out.println("   <input type=\"text\" hidden value=\"true\" name=\"topay\" form=\"PayForm\">");
                            out.println("   <input type=\"submit\" class=\"btn btn-primary\" value=\"Payer les reservations\" form=\"PayForm\"> ");
                            out.println("</form>");    
                        }                    
                    }
                %>
                
        </div>
    </body>
</html>
