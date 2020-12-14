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
public class TblDiscountAddError implements Serializable {
    private String duplicateDiscount;
    private String outOfRangeDiscount;
    private String outOfRangePercent;
    private String notValidPercent;

    public TblDiscountAddError() {
    }

    public TblDiscountAddError(String duplicateDiscount, String outOfRangeDiscount, String outOfRangePercent, String notValidPercent) {
        this.duplicateDiscount = duplicateDiscount;
        this.outOfRangeDiscount = outOfRangeDiscount;
        this.outOfRangePercent = outOfRangePercent;
        this.notValidPercent = notValidPercent;
    }

    public String getDuplicateDiscount() {
        return duplicateDiscount;
    }

    public void setDuplicateDiscount(String duplicateDiscount) {
        this.duplicateDiscount = duplicateDiscount;
    }

    public String getOutOfRangeDiscount() {
        return outOfRangeDiscount;
    }

    public void setOutOfRangeDiscount(String outOfRangeDiscount) {
        this.outOfRangeDiscount = outOfRangeDiscount;
    }

    public String getOutOfRangePercent() {
        return outOfRangePercent;
    }

    public void setOutOfRangePercent(String outOfRangePercent) {
        this.outOfRangePercent = outOfRangePercent;
    }

    public String getNotValidPercent() {
        return notValidPercent;
    }

    public void setNotValidPercent(String notValidPercent) {
        this.notValidPercent = notValidPercent;
    }

    
}
