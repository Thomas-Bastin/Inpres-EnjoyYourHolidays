/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author Arkios
 */
public class CreditCard implements Serializable {
    private long NumberCard;
    private int CVV;
    private String HolderName;
    
    /**
     *
     */
    public CreditCard(){
        this.NumberCard = -1;
        this.CVV = -1;
        this.HolderName = null;
    }
    
    /**
     *
     * @param NumberCard
     * @param CVV
     * @param HolderName
     * @throws NumberFormatException
     */
    public CreditCard(String NumberCard, String CVV, String HolderName) throws NumberFormatException {
        this.NumberCard = Long.parseLong(NumberCard);
        this.CVV = Integer.parseInt(CVV);
        this.HolderName = HolderName;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "CreditCard{" + "NumberCard=" + NumberCard + ", CVV=" + CVV + ", HolderName=" + HolderName + '}';
    }
    
    /**
     *
     * @return
     */
    public long getNumberCard() {
        return NumberCard;
    }

    /**
     *
     * @param NumberCard
     */
    public void setNumberCard(String NumberCard) {
        this.NumberCard = Long.parseLong(NumberCard);
    }

    /**
     *
     * @return
     */
    public int getCVV() {
        return CVV;
    }

    /**
     *
     * @param CVV
     */
    public void setCVV(String CVV) {
        this.CVV = Integer.parseInt(CVV);
    }

    /**
     *
     * @return
     */
    public String getHolderName() {
        return HolderName;
    }

    /**
     *
     * @param HolderName
     */
    public void setHolderName(String HolderName) {
        this.HolderName = HolderName;
    }
}
