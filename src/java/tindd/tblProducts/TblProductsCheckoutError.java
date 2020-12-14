/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblProducts;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblProductsCheckoutError implements Serializable {

    private String productOutOfStock;

    public TblProductsCheckoutError() {
    }

    public TblProductsCheckoutError(String productOutOfStock) {
        this.productOutOfStock = productOutOfStock;
    }

    public String getProductOutOfStock() {
        return productOutOfStock;
    }

    public void setProductOutOfStock(String productOutOfStock) {
        this.productOutOfStock = productOutOfStock;
    }
}
