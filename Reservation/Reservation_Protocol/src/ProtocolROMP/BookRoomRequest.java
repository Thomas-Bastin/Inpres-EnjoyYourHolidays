/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class BookRoomRequest extends Request {
    private final String category;
    private final String type;
    private final Date dateBeg;
    private final int nights;
    private final int clientRef;
    
    
    BookRoomRequest(String Category, String Type, Date dateBeg, int Nights, int ClientRef){
        this.category = Category;
        this.type = Type;
        this.dateBeg = dateBeg;
        this.nights = Nights;
        this.clientRef = ClientRef;
    }
    
    @Override
    protected void Task(Socket sock, ServerConsole log) throws IOException {
        //Todo
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public Date getDateBeg() {
        return dateBeg;
    }

    public int getNights() {
        return nights;
    }

    public int getClientRef() {
        return clientRef;
    }
}
