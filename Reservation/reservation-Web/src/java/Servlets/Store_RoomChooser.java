/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.db;
import Entities.*;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "Store_RoomChooser", urlPatterns = {"/Store_RoomChooser"})
public class Store_RoomChooser extends HttpServlet {

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
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        
        Voyageurs user = (Voyageurs) session.getAttribute("user"); 
        Complexes complexe = (Complexes) session.getAttribute("selected_complex");
        
        if(user == null){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        if(complexe == null){
            response.sendError(10004, "ERROR: Le Complexe est null, la session a perdu de l'information");
            return;
        }
        
        String selectedRoom = request.getParameter("selectedRoom");
        if(selectedRoom == null){
            response.sendRedirect(request.getContextPath() + "/Store_RoomChooser.jsp");
            return;
        }
        
        Chambres room = null;
        try {
            room = db.getSpecificRoom(complexe, Integer.parseInt(selectedRoom));
        } catch (SQLException ex) {
            response.sendError(10009, "SQL ERROR: " + ex.getMessage());
            return;
        }
        if(room == null){
            response.sendRedirect(request.getContextPath() + "/Store_RoomChooser.jsp");
            return;
        }
        
        session.setAttribute("selected_room", room);
        response.sendRedirect(request.getContextPath() + "/Store_Add_Basket.jsp");
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
