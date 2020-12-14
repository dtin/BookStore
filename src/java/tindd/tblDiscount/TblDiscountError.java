/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblDiscount;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblDiscountError implements Serializable {

    private String emptyDiscount;
    private String discountNotValid;
    private String alreadyUse;

    public TblDiscountError() {
    }

    public TblDiscountError(String emptyDiscount, String discountNotValid, String alreadyUse) {
        this.emptyDiscount = emptyDiscount;
        this.discountNotValid = discountNotValid;
        this.alreadyUse = alreadyUse;
    }

    public String getEmptyDiscount() {
        return emptyDiscount;
    }

    public void setEmptyDiscount(String emptyDiscount) {
        this.emptyDiscount = emptyDiscount;
    }

    public String getDiscountNotValid() {
        return discountNotValid;
    }

    public void setDiscountNotValid(String discountNotValid) {
        this.discountNotValid = discountNotValid;
    }

    public String getAlreadyUse() {
        return alreadyUse;
    }

    public void setAlreadyUse(String alreadyUse) {
        this.alreadyUse = alreadyUse;
    }
}
