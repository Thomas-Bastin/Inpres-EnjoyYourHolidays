/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

/**
 *
 * @author Arkios
 */
public class CreditCard {
    private long NumberCard;
    private int CVV;
    private String HolderName;
    
    public CreditCard(){
        this.NumberCard = -1;
        this.CVV = -1;
        this.HolderName = null;
    }
    
    public CreditCard(String NumberCard, String CVV, String HolderName) throws NumberFormatException {
        this.NumberCard = Integer.parseInt(NumberCard);
        this.CVV = Integer.parseInt(CVV);
        this.HolderName = HolderName;
    }
    
    
    @Override
    public String toString() {
        return "CreditCard{" + "NumberCard=" + NumberCard + ", CVV=" + CVV + ", HolderName=" + HolderName + '}';
    }
    
    
    public long getNumberCard() {
        return NumberCard;
    }

    public void setNumberCard(String NumberCard) {
        this.NumberCard = Long.parseLong(NumberCard);
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = Integer.parseInt(CVV);
    }

    public String getHolderName() {
        return HolderName;
    }

    public void setHolderName(String HolderName) {
        this.HolderName = HolderName;
    }
}
