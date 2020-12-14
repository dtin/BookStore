/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrderDetails;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblOrderDetailsDTO implements Serializable {
    private int detailId;
    private int orderId;
    private int productId;
    private long price;
    private int quantity;

    public TblOrderDetailsDTO() {
    }

    public TblOrderDetailsDTO(int detailId, int orderId, int productId, long price, int quantity) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public TblOrderDetailsDTO(int orderId, int productId, long price, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
    
    

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
