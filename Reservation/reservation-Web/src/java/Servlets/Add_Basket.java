/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;


import DataBase.db;
import Entities.*;
import Entities.BasketRow;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arkios
 */
@WebServlet(name = "Add_Basket", urlPatterns = {"/Add_Basket"})
public class Add_Basket extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        Voyageurs user = (Voyageurs) session.getAttribute("user"); 
        Complexes complexe = (Complexes) session.getAttribute("selected_complex");
        Chambres room = (Chambres) session.getAttribute("selected_room");
        String paramNuit = request.getParameter("nbjours");
        String paramDate = request.getParameter("date");
        
        if(user == null || complexe == null || room == null){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        if(paramNuit == null){
            response.sendRedirect(request.getContextPath() + "/Store_Add_Basket_ERROR.jsp");
            return;
        }
        
        if(paramDate == null){
            response.sendRedirect(request.getContextPath() + "/Store_Add_Basket_ERROR.jsp");
            return;
        }
        
        int nbNuit = Integer.parseInt(paramNuit);
        Date date = Date.valueOf(paramDate);

        try {
            db.BookRoom(room, date, nbNuit, user);
        } 
        catch (SQLException ex){
            request.setAttribute("custom-error", ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/Store_Add_Basket_ERROR.jsp");
            rd.forward(request, response);
        }
        catch (Exception ex) {
            if(ex.getMessage().equals("DateInvalid")){
                request.setAttribute("custom-error", "La chambre est déjà occupée durant l'intervale de dates choisies");
                RequestDispatcher rd = request.getRequestDispatcher("/Store_Add_Basket_ERROR.jsp");
                rd.forward(request, response);
                return;
            }
            if(ex.getMessage().equals("DateToEarly")){
                request.setAttribute("custom-error", "Choisissez une date après la date du jour.");
                RequestDispatcher rd = request.getRequestDispatcher("/Store_Add_Basket_ERROR.jsp");
                rd.forward(request, response);
                return;
            }
            
            request.setAttribute("custom-error", ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/Store_Add_Basket_ERROR.jsp");
            rd.forward(request, response);
        }
        
        //Tout a fonctionné, ajout au pannier:
        LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
        if(list == null){ list = new LinkedList<>();}
        BasketRow row = new BasketRow(complexe, room, date, nbNuit);
        list.add(row);
        session.setAttribute("basket", list);
        
        //Redirection vers le panier:
        response.sendRedirect(request.getContextPath() + "/Basket.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
