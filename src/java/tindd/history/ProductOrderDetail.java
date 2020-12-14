/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.history;

import java.io.Serializable;
import tindd.tblOrderDetails.TblOrderDetailsDTO;

/**
 *
 * @author Tin
 */
public class ProductOrderDetail implements Serializable {
    private TblOrderDetailsDTO orderDetailsDTO;
    private String productName;

    public ProductOrderDetail() {
    }

    public ProductOrderDetail(TblOrderDetailsDTO orderDetailsDTO, String productName) {
        this.orderDetailsDTO = orderDetailsDTO;
        this.productName = productName;
    }

    public TblOrderDetailsDTO getOrderDetailsDTO() {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(TblOrderDetailsDTO orderDetailsDTO) {
        this.orderDetailsDTO = orderDetailsDTO;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
}
