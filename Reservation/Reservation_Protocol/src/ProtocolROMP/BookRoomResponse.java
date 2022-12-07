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
public class BookRoomResponse extends Response{
    public static final int KO = 201;
    
    
    private final int nChambre;
    private final float prix;
    
    public BookRoomResponse(int c, String m, int nc, float pr) {
        super(c, m);
        nChambre = nc;
        this.prix = pr;
    }
    
    public BookRoomResponse(int c, String m) {
        super(c, m);
        nChambre = -1;
        this.prix = -1;
    }
    
    public int getnChambre() {
        return nChambre;
    }

    public float getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "BookRoomResponse{" + "nChambre=" + nChambre + ", prix=" + prix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.nChambre;
        hash = 17 * hash + Float.floatToIntBits(this.prix);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookRoomResponse other = (BookRoomResponse) obj;
        if (this.nChambre != other.nChambre) {
            return false;
        }
        if (Float.floatToIntBits(this.prix) != Float.floatToIntBits(other.prix)) {
            return false;
        }
        return true;
    }
    
    
}
