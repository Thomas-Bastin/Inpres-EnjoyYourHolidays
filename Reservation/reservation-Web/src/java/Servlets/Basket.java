package Servlets;

import DataBase.db;
import Entities.Reservationchambre;
import Entities.BasketRow;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "Basket", urlPatterns = {"/Basket"})
public class Basket extends HttpServlet {

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
            //Back to Login
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }        
        System.out.println("Passage Basket");
        
        LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
        String idparam = (String) request.getParameter("idrow");        
        if(list == null){
            list = new LinkedList<>();
        }           
        
        if (idparam != null) {
            int id = Integer.parseInt(idparam);
            if (!list.isEmpty()) {
                BasketRow row = list.get(id);

                try {
                    db.CancelRoom(row.getRoom(), row.getDate());
                } catch (SQLException ex) {
                    response.sendRedirect(request.getContextPath() + "/Basket.jsp");
                    return;
                }
                list.remove(id);

            }
        }
        
        session.setAttribute("basket", list);

        if (!list.isEmpty()) {
            String topay = (String) request.getParameter("topay");
            if(topay != null){
                if(topay.equals("true")){
                    //Goto PayementPage:
                    response.sendRedirect(request.getContextPath() + "/Pay_Basket.jsp");
                    return;
                }
            }
        }
        
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
