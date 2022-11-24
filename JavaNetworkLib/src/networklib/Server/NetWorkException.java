/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

/**
 *
 * @author Arkios
 */
public class NetWorkException extends Exception {
    String Msg;
    public NetWorkException(String service_is_){
        Msg = service_is_;
    }
    
    @Override
    public String getMessage(){
        return Msg;
    }
}
