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
public class TblDiscountDTO implements Serializable {

    private String discountCode;
    private float discountPercent;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(String discountCode, float discountPercent) {
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

}
