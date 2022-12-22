
package WebListener;

import DataBase.db;
import Entities.BasketRow;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author Arkios
 */
@WebListener()
public class MyWebListener implements HttpSessionListener{
    public static int connectedClient = 0;
    
    /**
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created: " + se.getSession().getId());
        connectedClient++;
    }

    /**
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        //When Session is Destroyed, you go here
        LinkedList<BasketRow> list = (LinkedList<BasketRow>) session.getAttribute("basket");
        if (list != null) {
            if (!list.isEmpty()) {
                for (BasketRow row : list) {
                    try {
                        db.CancelRoom(row.getRoom(), row.getDate());
                    } catch (SQLException ex) {
                        try {
                            db.CancelRoom(row.getRoom(), row.getDate());
                        } catch (SQLException ex1) {
                            Logger.getLogger(MyWebListener.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
                System.out.println("Basket Destroyed Successfuly: ");
            }
        }
        connectedClient--;
        System.out.println("Session Destroyed: " + se.getSession().getId());
    }
}
