/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrders;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tin
 */
public class TblOrdersDTO implements Serializable {

    private int orderId;
    private String userId;
    private long totalPrice;
    private Date orderDate;
    private String discountCode;

    public TblOrdersDTO() {
    }

    public TblOrdersDTO(int orderId, String userId, long totalPrice, Date orderDate, String discountCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.discountCode = discountCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

}
