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
            <form action="Basket" method="POST" id="returnForm">
                <input class="btn btn-primary" type="submit" id="ReturnButton" name="return" value="<---" required>
            </form>
            
            <div id="ButtonList">
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
            
            <label id="montant" > Montant:   
                    <%
                        if (session == null) {
                            response.sendRedirect(request.getContextPath() + "/Login.jsp");
                            return;
                        }
                        LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
                        if(list != null){ 
                            if(!list.isEmpty()){
                                double count = 0;
                                for (BasketRow row : list) {
                                    count += db.howMuchToPay(row.getRoom(), row.getComplexe().getIdComplexe(), row.getRoom().getNumChambre(), row.getDate(), row.getNbNuit());
                                }
                                //Display Input PayRoom
                                out.println(String.format("%.2f",count));
                            }
                        }
                    %>
             €</label>
             
            <form action="Pay_Basket" method="POST" id="PayForm">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon1">Numero Carte</span>
                    </div>
                    <input maxlength="13" name="credit-number" pattern="\d*" placeholder="Card Number" type="tel"   class="form-control" aria-label="Number" aria-describedby="basic-addon1"/>
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon2">Date Expiration</span>
                    </div>
                    <input maxlength="5" name="credit-expires" placeholder="MM / YY" type="tel"       class="form-control" aria-label="Number" aria-describedby="basic-addon2"/>
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon2">CVC</span>
                    </div>
                    <input maxlength="4" name="credit-cvc" pattern="\d*" placeholder="CVC" type="tel"               class="form-control" aria-label="Number" aria-describedby="basic-addon3"/>
                </div>
                
                <input type="text" hidden value="true" name="topay">
                <input type="submit" class="btn btn-primary" value="Payer">                    
            </form>
        </div>
    </body>
</html>
