/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

/**
 *
 * @author Thomas
 */
public class LoginResponse extends Response {
    public static final int BADMAIL = 401;
    public static final int BADPSWD = 402;
    
    public LoginResponse(int c, String m) {
        super(c, m);
    }
}
