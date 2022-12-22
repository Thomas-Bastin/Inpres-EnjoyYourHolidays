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
@WebServlet(name = "Store", urlPatterns = {"/Store"})
public class Store extends HttpServlet {
    
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
            //Back to Login
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        Voyageurs user = (Voyageurs) session.getAttribute("user");
        if (user == null) {
            //Back to Login
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        if (user.getNumeroClient() == -1) {
            //Back to Login
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        
        String SelectedComp = request.getParameter("selectedComplexe");
        if (SelectedComp == null) {
            //Back to Complexe Chooser
            response.sendRedirect(request.getContextPath() + "/Store_complexChooser.jsp");
            return;
        }
        int idComplexe = Integer.parseInt(SelectedComp);
        
        
        Complexes complexe;
        try {
            complexe = db.getSpecificComplex(idComplexe);
        } catch (SQLException ex) {
            response.sendError(1002, "SQL ERROR: " + ex.getMessage());
            return;
        }
        //Si toujours null error !!
        if (complexe == null) {
            response.sendError(1003, "ERROR: Id non trouvé en bdd");
            return;
        }
        
        //On a un user + un complexe selectionner, envoie vers un nouveau jsp
        session.setAttribute("selected_complex", complexe);
        response.sendRedirect(request.getContextPath() + "/Store_RoomChooser");
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
