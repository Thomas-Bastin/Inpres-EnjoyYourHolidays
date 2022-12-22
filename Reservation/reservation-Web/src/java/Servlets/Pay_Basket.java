package Servlets;

import DataBase.db;
import Entities.Chambres;
import Entities.CreditCard;
import Entities.Voyageurs;
import Entities.BasketRow;
import ProtocolROMP.PayRoomRequest;
import ProtocolROMP.PayRoomResponse;
import ProtocolROMP.TimeOut;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author Arkios
 */
@WebServlet(name = "Pay_Basket", urlPatterns = {"/Pay_Basket"})
public class Pay_Basket extends HttpServlet {

    private Properties config = null;
    private InetSocketAddress Addr;

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
        if (session == null) {
            //Back to Login
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        //1 Get Information
        //1.1 Verfify Information
        String creditNumber = request.getParameter("credit-number");
        String creditExpires = request.getParameter("credit-expires");
        String creditCVC = request.getParameter("credit-cvc");
        if (creditNumber == null || creditExpires == null || creditCVC == null) {
            response.sendRedirect(request.getContextPath() + "/Store");
            return;
        }
        Pattern pCardNumber = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");
        Matcher mCardNumber = pCardNumber.matcher(creditNumber);
        Pattern pCCV = Pattern.compile("^[0-9]{3,4}$");
        Matcher mCCV = pCCV.matcher(creditCVC);
        if (!mCardNumber.find() && !creditNumber.equals("")) {
            request.setAttribute("custom-error", "Le Numéro de carte est invalide !!!");
            RequestDispatcher rd = request.getRequestDispatcher("/Pay_Basket_ERROR.jsp");
            rd.forward(request, response);
            return;
        }
        if (!mCCV.find() && !creditCVC.equals("")) {
            request.setAttribute("custom-error", "Le CCV est invalide !!!");
            RequestDispatcher rd = request.getRequestDispatcher("/Pay_Basket_ERROR.jsp");
            rd.forward(request, response);
            return;
        }
        
        
        Socket sock = null;
        try{
            InitAddr();
            sock = new Socket();
            sock.setSoTimeout(10*1000);
            sock.connect(Addr);
            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
            
            //Tout a fonctionné, ajout au pannier:
            LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
            Voyageurs user = (Voyageurs) session.getAttribute("user");
            if (user == null || list == null) {
                //Back to Login
                response.sendRedirect(request.getContextPath() + "/Login.jsp");
                return;
            }
            
            CreditCard cC = new CreditCard(creditNumber, creditCVC, creditExpires);
            
            for(BasketRow row : list){
                oos.writeObject(new PayRoomRequest(row.getRoom(), row.getDate(), user, cC));
                
                ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());
                Object r = ios.readObject();
                
                RequestDispatcher rd = request.getRequestDispatcher("/Pay_Basket_ERROR.jsp");
                
                if (r instanceof TimeOut) {
                    request.setAttribute("custom-error", "Le Serveur de payement est innacessible !");
                    rd.forward(request, response);
                    return;
                }
                
                if (r instanceof PayRoomResponse) {
                    PayRoomResponse myreq = (PayRoomResponse) r;
                    
                    switch (myreq.getCode()) {
                        case PayRoomResponse.SUCCESS:
                            break;
                            
                        case PayRoomResponse.BADDB:
                            request.setAttribute("custom-error", "Erreur SQL: " + myreq.getMessage());
                            rd.forward(request, response);
                            return;
                            
                        case PayRoomResponse.UNKOWN:
                        default:
                            request.setAttribute("custom-error", "Erreur: " + myreq.getMessage());
                            rd.forward(request, response);
                            return;
                    }
                }
            }
        }catch(IOException | ClassNotFoundException | NumberFormatException | ServletException ex){
            if(sock != null) sock.close();
            System.out.println("Close socket");
            Logger.getLogger(Pay_Basket.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher rd = request.getRequestDispatcher("/Pay_Basket_ERROR.jsp");
            request.setAttribute("custom-error", "Une erreur est survenue du côté serveur, veuillez réessayé plus tard...");
            rd.forward(request, response);
            return;
        }
        sock.close();
        session.setAttribute("basket", new LinkedList<>());
        //5 Supprimer Tout reset la liste
        response.sendRedirect (request.getContextPath() + "/Pay_Basket_SUCCESS.jsp");
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
    
    
    
    private void InitAddr() throws IOException, NumberFormatException, UnknownHostException{
        File f = new File("webServer.cfg");
        config = new Properties();
        
        if (f.createNewFile()) {
            OutputStream os = new FileOutputStream(f.getPath());
            config.setProperty("port", "50006");
            config.setProperty("ip", "127.0.0.1");

            config.store(os, "Configuration du Client:");
        } else {
            FileInputStream fis = new FileInputStream(f.getPath());
            config.load(fis);
        }
        
        //Get Port & IP
        int port = Integer.parseInt(config.getProperty("port"));
        InetAddress ip = InetAddress.getByName((String)config.getProperty("ip"));
        Addr = new InetSocketAddress(ip,port);
    }
}
    
